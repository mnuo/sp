package com.hwagain.system.base.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;
import com.hwagain.orgsys.api.OrderMealApi;
import com.hwagain.orgsys.dto.UserOrderMealDto;
import com.hwagain.system.base.dto.OmUserDto;
import com.hwagain.system.base.entity.OmUser;
import com.hwagain.system.base.mapper.OmUserMapper;
import com.hwagain.system.base.service.IOmUserService;
import com.hwagain.system.util.ConstantUtil;
import com.hwagain.system.util.MD5Utils;
import com.hwagain.system.util.SmsUtils;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mon
 * @since 2019-08-09
 */
@Service("omUserService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OmUserServiceImpl extends ServiceImpl<OmUserMapper, OmUser> implements IOmUserService {
	private final static Logger logger = LoggerFactory.getLogger(OmUserServiceImpl.class);
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(OmUser.class, OmUserDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(OmUserDto.class, OmUser.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
	@Autowired
	OrderMealApi orderMearService;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpServletResponse response;
	
	@Autowired
    private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
    private RedisTemplate redisTemplate;

	
	@Override
	public boolean saveOne(OmUserDto dto) {
		Assert.notBlank(dto.getPhone(), "手机号不能为空");
		Assert.notBlank(dto.getUserName(), "姓名不能为空");
		Assert.notBlank(dto.getType(), "用户类型(内部人员,外部人员)不能为空");
		Assert.notBlank(dto.getVcode(), "验证码不能为空");
		
		checkVerifyCode(dto.getPhone(), dto.getVcode());// 验证码
		
		Wrapper<OmUser> wrapper=new CriterionWrapper<OmUser>(OmUser.class);
		wrapper.eq("status", 30);//有效状态
		wrapper.eq("phone", dto.getPhone());//有效状态
		OmUser entity = super.selectFirst(wrapper);
		if(entity != null){
			Assert.throwException("该手机号已经注册过, 请确认.");
		}
		
		// 调用PS系统验证内部人员
		if("0".equals(dto.getType())){
			UserOrderMealDto psuser = null;
			try {
				psuser = orderMearService.findUserOneByPhone(dto.getPhone());
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("PS根据手机号获取人员失败:　" + e.getMessage());
				logger.error(e);
			}
			Assert.notNull(psuser, "该手机号不存在内部系统中, 请确认。");
			
			dto.setDeptId(psuser.getDeptCode());
			dto.setDeptName(psuser.getDeptName());
			dto.setUserCode(psuser.getUserCode());
			dto.setWorkship(psuser.getWorkShopName());
			dto.setWorkshipId(psuser.getWorkShopCode());
			dto.setPosition(psuser.getPositionName());
			dto.setPositionId(psuser.getPositionCode());
		}
		
		if("1".equals(dto.getType())){//外部人员, 注册默认为公司外部普通人员
			dto.setExoType(3002);
			dto.setExoTypeName("公司外部普通人员");
		}
		
		dto.setFdId(IdWorker.getId());
		dto.setStatus(30);
		dto.setRegisterDate(new Date());
		dto.setLastUpdateId(dto.getUserName());
		dto.setLastUpdateDate(new Date());
		
		return super.insert(dtoToEntityMapper.map(dto, OmUser.class));
	}
	private void checkVerifyCode(String phone, String vcode) {
		 //验证码
        String code = stringRedisTemplate.opsForValue().get(ConstantUtil.REDIS_IDENTIFYING_CODE + phone);
        if (StringUtils.isEmpty(code)){
            Assert.throwException("验证码未输入或已过期");
        } else if (!code.equals(vcode)){
            Assert.throwException("验证码不匹配");
        }
        //匹配成功清空
        stringRedisTemplate.delete(ConstantUtil.REDIS_IDENTIFYING_CODE + phone);
	}
	@Override
	public String sendCheckCode(String phone) {
        String code6 = SmsUtils.getRandom6Code();
        SmsUtils.sendSms("验证码:"+code6, "订餐系统", Collections.singletonList(phone));
        logger.info("向手机号: " + phone + ", 发送验证码:" + code6);
        //5分钟过期
        stringRedisTemplate.opsForValue().set(ConstantUtil.REDIS_IDENTIFYING_CODE+phone, code6, ConstantUtil.REDIS_EXPIRE, TimeUnit.MINUTES);
		return code6;
	}
	@Override
	public OmUserDto login(String phone, String vcode) {
		Assert.notBlank(phone, "手机号不能为空");
		Assert.notBlank(vcode, "验证码不能为空");
		
		checkVerifyCode(phone, vcode);// 验证码
		
		Wrapper<OmUser> wrapper=new CriterionWrapper<OmUser>(OmUser.class);
		wrapper.eq("status", 30);//有效状态
		wrapper.eq("phone", phone);//有效状态
		OmUser entity = super.selectFirst(wrapper);
		if(entity != null){
			OmUserDto dto = entityToDtoMapper.map(entity, OmUserDto.class);
			
            //把用户存储到Redis里面，过期时间为5个小时
            String userKey = dto.getPhone() + dto.getFdId() + new Date().getTime();
            String accessToken = MD5Utils.getSHA256(userKey);
            redisTemplate.opsForValue().set(ConstantUtil.REDIS_TOKEN + accessToken, dto, ConstantUtil.REDIS_EXPIRE_DAYS, TimeUnit.DAYS);
            dto.setAccessToken(accessToken);
            response.setHeader("Authorization", accessToken);
            return dto;
		} else {
            Assert.throwException("账号或密码错误");
        }
		return null;
	}
	
	@Override
	public OmUserDto checklogin(String authorization) {
		authorization = request.getHeader("Authorization");
		return validateSession(authorization);
	}
	private OmUserDto validateSession(String authorization) {
		OmUserDto dto = (OmUserDto) redisTemplate.opsForValue().get(ConstantUtil.REDIS_TOKEN + authorization);
        if (dto != null){
            //刷新session时间
            redisTemplate.expire(ConstantUtil.REDIS_SESSION + authorization, ConstantUtil.REDIS_EXPIRE_DAYS, TimeUnit.DAYS);
        }
        return dto;
    }

}
