package com.hwagain.system.recipe.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.web.common.controller.BaseController;
import com.hwagain.system.recipe.service.IOmRecipeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 菜单表-普通菜 前端控制器
 * </p>
 *
 * @author mon
 * @since 2019-08-08
 */
@RestController
@RequestMapping(value="/recipe/omRecipe",method={RequestMethod.GET,RequestMethod.POST})
@Api(value="菜单查询", description="菜单查询")
public class OmRecipeController extends BaseController{
	
	@Autowired
	IOmRecipeService omRecipeService;
	
	@RequestMapping(value = "/findDingcByDate", method = { RequestMethod.GET })
	@ApiOperation(value = "根据时间查询当天的菜单-订餐", notes = "根据时间查询当天的菜单-订餐", httpMethod = "GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "token", paramType = "query", required = true, dataType = "String") ,
		@ApiImplicitParam(name = "date", value = "yyyy-MM-dd", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "areaType", value = "GX-广西菜, JX-江西菜", paramType = "query", required = false, dataType = "String") 
	})
	public Response findDingcByDate(String token, String date, String areaType) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("token", token);
		params.put("fdDate", date);
		params.put("areaType", areaType);
		return SuccessResponseData.newInstance(omRecipeService.findDingcByDate(params));
	}
	
	@RequestMapping(value = "/findDiancByDate", method = { RequestMethod.GET })
	@ApiOperation(value = "根据时间查询当天的菜单-个人点餐", notes = "根据时间查询当天的菜单-个人点餐", httpMethod = "GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "token", paramType = "query", required = true, dataType = "String") ,
		@ApiImplicitParam(name = "date", value = "yyyy-MM-dd", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "areaType", value = "GX-广西菜, JX-江西菜", paramType = "query", required = false, dataType = "String") 
	})
	public Response findDiancByDate(String token, String date, String areaType) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("token", token);
		params.put("fdDate", date);
		params.put("areaType", areaType);
		return SuccessResponseData.newInstance(omRecipeService.findDiancByDate(params));
	}
	
	@RequestMapping(value = "/findZhaodByDate", method = { RequestMethod.GET })
	@ApiOperation(value = "根据时间查询当天的菜单-招待点餐", notes = "根据时间查询当天的菜单-招待点餐", httpMethod = "GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "token", paramType = "query", required = true, dataType = "String") ,
		@ApiImplicitParam(name = "date", value = "yyyy-MM-dd", paramType = "query", required = true, dataType = "String"),
	})
	public Response findZhaodByDate(String token, String date) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("token", token);
		params.put("fdDate", date);
//		params.put("areaType", areaType);
		return SuccessResponseData.newInstance(omRecipeService.findZhaodByDate(params));
	}
	
	@RequestMapping(value = "/findWeixByDate", method = { RequestMethod.GET })
	@ApiOperation(value = "根据时间查询当天的菜单-维修人员点餐", notes = "根据时间查询当天的菜单-维修人员点餐", httpMethod = "GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "token", paramType = "query", required = true, dataType = "String") ,
		@ApiImplicitParam(name = "date", value = "yyyy-MM-dd", paramType = "query", required = true, dataType = "String"),
	})
	public Response findWeixByDate(String token, String date) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("token", token);
		params.put("fdDate", date);
//		params.put("areaType", areaType);
		return SuccessResponseData.newInstance(omRecipeService.findWeixByDate(params));
	}
}
