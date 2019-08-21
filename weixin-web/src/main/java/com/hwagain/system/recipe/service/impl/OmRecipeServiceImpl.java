package com.hwagain.system.recipe.service.impl;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.system.recipe.dto.OmRecipeDto;
import com.hwagain.system.recipe.dto.RecipeRpsDto;
import com.hwagain.system.recipe.entity.OmRecipe;
import com.hwagain.system.recipe.mapper.OmRecipeMapper;
import com.hwagain.system.recipe.service.IOmRecipeService;
import com.hwagain.system.util.ConstantUtil;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * <p>
 * 菜单表-普通菜 服务实现类
 * </p>
 *
 * @author mon
 * @since 2019-08-08
 */
@Service("omRecipeService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OmRecipeServiceImpl extends ServiceImpl<OmRecipeMapper, OmRecipe> implements IOmRecipeService {
	private final static Logger logger = LoggerFactory.getLogger(OmRecipeServiceImpl.class);
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(OmRecipe.class, OmRecipeDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(OmRecipeDto.class, OmRecipe.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
	
	@Autowired
	OmRecipeMapper omRecipeMapper;

	@Override
	public Map<String, Object> findDingcByDate(Map<String, String> params) {
		Map<String, Object> result = new HashMap<>();//返回结果集
		result.put("date", params.get("fdDate"));
		
		String token = params.get("token");
		//TODO 获取登录用户设置用户类型查询
		params.put("userType", "3001");//设置当前用户类型: 内部员工,外部普通员工3002,优良3003,优质3004
		
		List<OmRecipeDto> list = omRecipeMapper.findByParams(params);
		this.genericCommonResult(list, result, 0);
		return result;
	}
	/**
	 * 
	 * @param list 处理列表
	 * @param result 处理结果
	 * @param i : 0-订餐, 1-点餐
	 */
	public void genericCommonResult(List<OmRecipeDto> list, Map<String, Object> result, int i){
		if(list != null && list.size() > 0){
			for (OmRecipeDto omRecipe : list) {
				Map<String, List<RecipeRpsDto>> mealMap = (Map<String, List<RecipeRpsDto>>)result.get(i == 0 ? omRecipe.getMealType() : "putong");
				if(mealMap == null){//第一次初始化
					mealMap = new HashMap<String, List<RecipeRpsDto>>();
				}
				result.put(i == 0 ? omRecipe.getMealType() : "putong", mealMap);
				
				List<RecipeRpsDto> rps = mealMap.get(omRecipe.getAreaType());
				if(rps == null || rps.size() == 0){//第一次初始化
					rps = new ArrayList<RecipeRpsDto>();
				}
				mealMap.put(omRecipe.getAreaType(), rps);
				
				if(rps != null && rps.size() > 0){//主要针对非中晚餐,分荤素
					setDtoFromEntity(rps, omRecipe);
				} else {//第一次分四个品种
					int flag = omRecipe.getType() == 2003 ? 1 : 0;
					//品种1
					rps.add(new RecipeRpsDto(flag, omRecipe.getDishe1Id(), omRecipe.getDishe1Name(), omRecipe.getAreaType(), omRecipe.getMealType(), omRecipe.getMealTypeName(), omRecipe.getType(), omRecipe.getTypeName(), omRecipe.getPrice()));
					//品种2
					rps.add(new RecipeRpsDto(flag, omRecipe.getDishe2Id(), omRecipe.getDishe2Name(), omRecipe.getAreaType(), omRecipe.getMealType(), omRecipe.getMealTypeName(), omRecipe.getType(), omRecipe.getTypeName(), omRecipe.getPrice()));
					//品种3
					rps.add(new RecipeRpsDto(flag, omRecipe.getDishe3Id(), omRecipe.getDishe3Name(), omRecipe.getAreaType(), omRecipe.getMealType(), omRecipe.getMealTypeName(), omRecipe.getType(), omRecipe.getTypeName(), omRecipe.getPrice()));
					//品种4
					rps.add(new RecipeRpsDto(flag, omRecipe.getDishe4Id(), omRecipe.getDishe4Name(), omRecipe.getAreaType(), omRecipe.getMealType(), omRecipe.getMealTypeName(), omRecipe.getType(), omRecipe.getTypeName(), omRecipe.getPrice()));
				}
			}
		}
	}
	
	
	public void setDtoFromEntity(List<RecipeRpsDto> rps, OmRecipeDto omRecipe){
		for(int i = 1; i < 4; i ++){//主要针对非中晚餐,分荤素
			RecipeRpsDto dto = rps.get(i-1);
			if(dto != null){
				try {
					PropertyDescriptor pd1 = new PropertyDescriptor("dishe" + i + "Id", OmRecipeDto.class);
					Method getMethod1 = pd1.getReadMethod();
					
					PropertyDescriptor pd2 = new PropertyDescriptor("dishe" + i + "Name", OmRecipeDto.class);
					Method getMethod2 = pd2.getReadMethod();
					if(omRecipe.getType() == 2003){//荤菜
						dto.setMeatDisheId((Long)ReflectionUtils.invokeMethod(getMethod1, omRecipe));
						dto.setMeatDisheName((String)ReflectionUtils.invokeMethod(getMethod2, omRecipe));
						dto.setMeatType(omRecipe.getType());
						dto.setMeatTypeName(omRecipe.getTypeName());
						dto.setMeatPrice(omRecipe.getPrice());
					}else {
						dto.setDisheId((Long)ReflectionUtils.invokeMethod(getMethod1, omRecipe));
						dto.setDisheName((String)ReflectionUtils.invokeMethod(getMethod2, omRecipe));
						dto.setType(omRecipe.getType());
						dto.setTypeName(omRecipe.getTypeName());
						dto.setPrice(omRecipe.getPrice());
					}
				} catch (IntrospectionException e) {
					e.printStackTrace();
					logger.error(omRecipe.getFdId() + "菜单设置失败: " + e);
				}
			}
		}
	}
	@Override
	public Map<String, Object> findDiancByDate(Map<String, String> params) {
		Map<String, Object> result = new HashMap<>();//返回结果集
		result.put("date", params.get("fdDate"));
		
		String token = params.get("token");
		//TODO 获取登录用户设置用户类型查询
		params.put("userType", "3001");//设置当前用户类型: 内部员工,外部普通员工3002,优良3003,优质3004
		
		//TODO
//		Date fdDate = new Date();
//		String today = DateUtil.formatString(fdDate);
//		params.put("fdDate", today);
//		String curentDate = DateUtil.formatString(fdDate, "yyyy-MM-dd HH:mm")
		
		String today = "2019-08-19";
		String mealType = ConstantUtil.getMealType("2019-08-19 11:10", today);
		
		Assert.notBlank(mealType, "改时间不在点餐时间段");
		params.put("mealType", mealType);
		
		//普通餐
		List<OmRecipeDto> list = omRecipeMapper.findByParams(params);
		this.genericCommonResult(list, result, 1);
		
		//小炒
		Map<String, String> params1 = new HashMap<String, String>();
		params1.put("type", "2004");
		params1.put("mealType", mealType);
		params1.put("userType", "3001"); //TODO
		
		List<RecipeRpsDto> xiaochao = omRecipeMapper.findBaseDisheByType(params1);
		result.put("xiaochao", xiaochao);
		//炖蛊
		params1.put("type", "2005");
		List<RecipeRpsDto> dungu = omRecipeMapper.findBaseDisheByType(params1);
		result.put("dungu", dungu);
		
		return result;
	}
	@Override
	public Map<String, Object> findZhaodByDate(Map<String, String> params) {
		Map<String, Object> result = new HashMap<>();//返回结果集
		result.put("date", params.get("fdDate"));
		
		String token = params.get("token");
		//TODO 获取登录用户设置用户类型查询
		params.put("userType", "3001");//设置当前用户类型: 内部员工,外部普通员工3002,优良3003,优质3004
		
		//TODO
//		Date fdDate = new Date();
//		String today = DateUtil.formatString(fdDate);
//		params.put("fdDate", today);
//		String curentDate = DateUtil.formatString(fdDate, "yyyy-MM-dd HH:mm")
		
		String today = "2019-08-19";
		String mealType = ConstantUtil.getMealType("2019-08-19 11:10", today);
		
		Assert.notBlank(mealType, "改时间不在点餐时间段");
		params.put("mealType", mealType);
		
		
		//小炒
		Map<String, String> params1 = new HashMap<String, String>();
		params1.put("type", "2004");
		params1.put("mealType", mealType);
		params1.put("userType", "3001"); //TODO
		
		List<RecipeRpsDto> xiaochao = omRecipeMapper.findBaseDisheByType(params1);
		result.put("xiaochao", xiaochao);
		//炖蛊
		params1.put("type", "2005");
		List<RecipeRpsDto> dungu = omRecipeMapper.findBaseDisheByType(params1);
		result.put("dungu", dungu);
		
		return result;
	}
	@Override
	public Map<String, Object> findWeixByDate(Map<String, String> params) {
		Map<String, Object> result = new HashMap<>();//返回结果集
		result.put("date", params.get("fdDate"));
		
		String token = params.get("token");
		//TODO 获取登录用户设置用户类型查询
		params.put("userType", "3001");//设置当前用户类型: 内部员工,外部普通员工3002,优良3003,优质3004
		
		//TODO
//		Date fdDate = new Date();
//		String today = DateUtil.formatString(fdDate);
//		params.put("fdDate", today);
//		String curentDate = DateUtil.formatString(fdDate, "yyyy-MM-dd HH:mm")
		
		String today = "2019-08-19";
		String mealType = ConstantUtil.getMealType("2019-08-19 11:10", today);
		
		Assert.notBlank(mealType, "当下时间不在点餐时间段");
		params.put("mealType", mealType);
		
		//普通餐
		List<OmRecipeDto> list = omRecipeMapper.findByParams(params);
		this.genericCommonResult(list, result, 1);
		return result;
	}
	
}
