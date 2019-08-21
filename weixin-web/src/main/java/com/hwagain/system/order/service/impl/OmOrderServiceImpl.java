package com.hwagain.system.order.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;
import com.hwagain.system.base.dto.OmDataDictDto;
import com.hwagain.system.base.entity.OmUser;
import com.hwagain.system.base.service.IOmDataDictService;
import com.hwagain.system.order.dto.OmOrderDetailDto;
import com.hwagain.system.order.dto.OmOrderDto;
import com.hwagain.system.order.dto.OmOrderListDto;
import com.hwagain.system.order.entity.OmOrder;
import com.hwagain.system.order.entity.OmOrderDetail;
import com.hwagain.system.order.entity.OmOrderList;
import com.hwagain.system.order.mapper.OmOrderMapper;
import com.hwagain.system.order.service.IOmOrderDetailService;
import com.hwagain.system.order.service.IOmOrderListService;
import com.hwagain.system.order.service.IOmOrderService;
import com.hwagain.system.util.DateUtil;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mon
 * @since 2019-08-16
 */
@Service("omOrderService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OmOrderServiceImpl extends ServiceImpl<OmOrderMapper, OmOrder> implements IOmOrderService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(OmOrder.class, OmOrderDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(OmOrderDto.class, OmOrder.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	@Autowired
	IOmOrderListService omOrderListService;
	@Autowired
	IOmDataDictService omDataDictService;
	@Autowired
	IOmOrderDetailService omOrderDetailService;
	
	@Override
	public boolean saveOneOrderList(String token, OmOrderListDto dto) {
		//TODO 1, 获取登录用户
//		OmUser user = getOmUser(token);
		OmUser user = new OmUser();
		user.setFdId(1162154379658145792l);
		user.setUserName("张三");
		// 下单人
		Assert.notNull(dto, "对象不能为空");
		dto.setUserId(user.getFdId()+"");
		dto.setUserName(user.getUserName());
		
		//2,参数信息校验
		checkMainInfo(dto);
		// 2.1 菜品详细信息校验
		List<OmOrderDetailDto> details = dto.getDetails();
		checkDetailInfo(details, dto);
		
		// 3, 校验是否已存在
		checkExist(dto);
		
		// 4, 设置必需参数 orderList
		dto.setDinnerStatus(0);// 未取餐
		dto.setPayStatus(0);//未支付
		dto.setRefundStatus(0);//未退款
		dto.setStatus(10);// 保存状态, 可以修改,50-删除  20-提交订单 30-完成, 60-取消
		
		if(dto.getFdId() == null){
			dto.setCreatorId(user.getUserName());
			dto.setCreateDate(new Date());
		}
		dto.setLastUpdateDate(new Date());
		dto.setLastUpdateId(user.getUserName());
		
		// 5, 保存更新 orderList
		OmOrderListDto omOrderListDto = omOrderListService.saveOrUpdate(dto);
		
		// 6, 设置详细菜单
		Wrapper<OmOrderDetail> detailWrapper = new CriterionWrapper<OmOrderDetail>(OmOrderDetail.class);
		detailWrapper.eq("order_list_id", omOrderListDto.getFdId());
		omOrderDetailService.delete(detailWrapper);
		
		for(OmOrderDetailDto detailDto : details){
			detailDto.setFdId(IdWorker.getId());
			detailDto.setOrderListId(omOrderListDto.getFdId());
			omOrderDetailService.insert(detailDto);
		}
		return true;
	}

	private void checkExist(OmOrderListDto dto) {
		Wrapper<OmOrderList> wrapper = new CriterionWrapper<OmOrderList>(OmOrderList.class);
		wrapper.eq("fd_date", DateUtil.formatString(dto.getFdDate()));
		wrapper.eq("meal_type", dto.getMealType());
		wrapper.eq("from_type", dto.getFromType());
		
		if(StringUtils.isEmpty(dto.getDinnerUserId())){
			wrapper.isNull("dinner_user_id");
		}else{
			wrapper.eq("dinner_user_id", dto.getDinnerUserId());
		}
		if(StringUtils.isEmpty(dto.getDinnerUserName())){
			wrapper.isNull("dinner_user_name");
		}else{
			wrapper.eq("dinner_user_name", dto.getDinnerUserName());
		}
		wrapper.eq("user_id", dto.getUserId()); // TODO
		
		wrapper.ne("status", 60); //取消
		wrapper.ne("status", 50); //删除
		wrapper.ne("status", 10); //未下单-早稿状态
		
		OmDataDictDto datadict = omDataDictService.findByItemNo(dto.getMealType());
		OmOrderList entity = omOrderListService.selectFirst(wrapper);
		if(entity != null){
			Assert.throwException("【" + DateUtil.formatString(dto.getFdDate()) + "," + datadict.getItemName() + "】已经存在记录,不能重复添加");
		}
		
	}

	private void checkDetailInfo(List<OmOrderDetailDto> details, OmOrderListDto dto) {
		if(details == null || details.size() == 0){
			Assert.throwException("没有选择菜品");
		}
		BigDecimal total = BigDecimal.ZERO;
		for(int i = 0; i < details.size(); i ++) {
			OmOrderDetailDto detail = details.get(i);
			Assert.notNull(detail.getAmount(), "菜品详情第" + (i+1) + "条记录金额不能为空");
			Assert.notNull(detail.getDisheId(), "菜品详情第" + (i+1) + "条记录菜品不能为空");
			Assert.notNull(detail.getNumber(), "菜品详情第" + (i+1) + "条记录数量不能为空");
			Assert.notNull(detail.getPrice(), "菜品详情第" + (i+1) + "条记录单价不能为空");
			Assert.notNull(detail.getType(), "菜品详情第" + (i+1) + "条记录菜品类型不能为空");
			total = total.add(detail.getAmount());
		}
		
		if(total.subtract(dto.getAmount()).abs().compareTo(new BigDecimal(1)) == 1){
			Assert.throwException("金额与菜品详情金额不对应,请确认");
		}
	}

	private void checkMainInfo(OmOrderListDto dto) {
		Assert.notNull(dto.getFdDate(), "就餐时间不能为空");
		Assert.notBlank(dto.getMealType(), "就餐类型不能为空");
		Assert.notBlank(dto.getFromType(), "订单类型(订餐/点餐)不能为空");
		if("ZHAOD".equals(dto.getFromType())){
			Assert.notBlank(dto.getDinnerUserId(), "招待点餐-人员不能为空");
		}
		if("WEIX".equals(dto.getFromType())){
			Assert.notBlank(dto.getDinnerUserName(), "维修人员点餐-人员不能为空");
		}
		Assert.notNull(dto.getAmount(), "金额不能为空");
		
		if(dto.getAmount().compareTo(BigDecimal.ZERO) <= 0){
			Assert.throwException("金额不能小于等于0");
		}
	}

	@Override
	public boolean saveBatchOrderList(String token, List<OmOrderListDto> dtos) {
		if(dtos == null || dtos.size() == 0){
			Assert.throwException("对象不能为空");
		}
		for (OmOrderListDto omOrderListDto : dtos) {
			saveOneOrderList(token, omOrderListDto);
		}
		return true;
	}

	@Override
	public boolean submitOrder(String token, OmOrderDto dto) {
		Assert.notNull(dto, "传递参数对象不能为空");
		Assert.notNull(dto.getAmount(), "订单金额不能为空");
		Assert.notNull(dto.getFromType(), "订单类型不能为空");
		
		//TODO 1, 获取登录用户
//		OmUser user = getOmUser(token);
		OmUser user = new OmUser();
		user.setFdId(1162154379658145792l);
		user.setUserName("张三");
		
		dto.setOrderDate(new Date());
		dto.setFdId(IdWorker.getId());
		dto.setPayStatus(0);//待支付
		dto.setStatus(10);//
		
		List<OmOrderListDto> details = dto.getDetails();
		if(details == null || details.size() == 0){
			Assert.throwException("没有选择要生成订单的记录");
		}
		
		BigDecimal total = BigDecimal.ZERO;
		for(OmOrderListDto detail : details){
			OmOrderList entity = omOrderListService.selectById(detail.getFdId());
			if(entity == null){
				Assert.throwException("记录不存在");
			}
			if(entity.getStatus() == null || entity.getStatus().intValue() != 10){
				Assert.throwException("记录不是可以生成订单状态");
			}
			entity.setOrderId(dto.getFdId());
			entity.setStatus(20);//已生成订单待支付
			entity.setLastUpdateId(user.getUserName());
			entity.setLastUpdateDate(new Date());
			
			omOrderListService.updateById(entity);
			
			total = total.add(entity.getAmount());
		}
		if(total.subtract(dto.getAmount()).abs().compareTo(new BigDecimal(1)) >= 0){
			Assert.throwException("订单总金额和订单列表的金额总和不一致。");
		}
		
		dto.setCreateDate(new Date());
		dto.setCreatorId(user.getUserName());
		dto.setLastUpdateDate(new Date());
		dto.setLastUpdateId(user.getUserName());
		dto.setUserId(user.getFdId()+"");
		super.insert(dtoToEntityMapper.map(dto, OmOrder.class));
		return true;
	}
	
	@Override
	public List<OmOrderListDto> findByFromTypeAndStatus(String token, String fromType, String status) {
		Assert.notBlank(token, "传递参数不能为空");
		Assert.notBlank(fromType, "传递参数不能为空");
		Assert.notBlank(status, "传递参数对象不能为空");
		
		//TODO 1, 获取登录用户
//		OmUser user = getOmUser(token);
		OmUser user = new OmUser();
		user.setFdId(1162154379658145792l);
		user.setUserName("张三");
		
		Map<String, String> params = new HashMap<>();
		params.put("fromType", fromType);
		params.put("status", status);
		
		List<OmOrderListDto> list = omOrderListService.findByFromTypeAndStatus(params);
		
		return list;
	}

	@Override
	public List<OmOrderListDto> findOrderByFromType(String token, String fromType) {
		Assert.notBlank(token, "传递参数不能为空");
		Assert.notBlank(fromType, "传递参数不能为空");
		
		//TODO 1, 获取登录用户
//		OmUser user = getOmUser(token);
		OmUser user = new OmUser();
		user.setFdId(1162154379658145792l);
		user.setUserName("张三");
		
		Map<String, String> params = new HashMap<>();
		params.put("fromType", fromType);
		params.put("dinnerStatus", "0");//未取餐
		params.put("payStatus", "1");//已付款
		params.put("refundStatus", "0");//没有退款
		params.put("satus", "30");//已付款-没有取消删除的订单
		
		List<OmOrderListDto> list = omOrderListService.findByFromTypeAndStatus(params);
		
		return list;
	}

	@Override
	public boolean deleteOneNotGenericOrder(String token, String fdId) {
		//TODO 1, 获取登录用户
//		OmUser user = getOmUser(token);
		OmUser user = new OmUser();
		user.setFdId(1162154379658145792l);
		user.setUserName("张三");
		
		Wrapper<OmOrderList> wrapper = new CriterionWrapper<OmOrderList>(OmOrderList.class);
		wrapper.eq("fd_id", fdId);
		wrapper.eq("status", 10);
		
		OmOrderList entity = omOrderListService.selectFirst(wrapper);
		if(entity != null){
			Long orderId = entity.getOrderId();
			if(orderId != null){
				OmOrder order = super.selectById(orderId);
				if(order != null){
					Assert.throwException("该订餐记录已生成付款订单, 不允许删除");
				}
			}
			entity.setStatus(50);//删除状态
			entity.setLastUpdateDate(new Date());
			entity.setLastUpdateId(user.getUserName());
			
			return omOrderListService.updateAllById(entity);
		}else{
			Assert.throwException("该订餐记录不存在");
		}
		
		return true;
	}
}
