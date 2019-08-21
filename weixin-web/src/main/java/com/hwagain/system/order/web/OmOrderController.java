package com.hwagain.system.order.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponse;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.web.common.controller.BaseController;
import com.hwagain.system.order.dto.OmOrderDto;
import com.hwagain.system.order.dto.OmOrderListDto;
import com.hwagain.system.order.service.IOmOrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mon
 * @since 2019-08-16
 */
@RestController
@RequestMapping(value="/order/omOrder",method={RequestMethod.GET,RequestMethod.POST})
@Api(value="订单", description="订单")
public class OmOrderController extends BaseController{
	
	@Autowired
	IOmOrderService omOrderService;
	
	@RequestMapping("/saveOneOrderList")
	@ApiOperation(value="提交",notes="提交",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "token", paramType = "query", required = false, dataType = "String")
	})
//	@ApiImplicitParams({
//		@ApiImplicitParam(name = "token", value = "token", paramType = "query", required = false, dataType = "String"),
//		@ApiImplicitParam(name = "fdDate", value = "fdDate-就餐时间", paramType = "query", required = false, dataType = "String"),
//		@ApiImplicitParam(name = "mealType", value = "用餐类型-早中晚夜", paramType = "query", required = false, dataType = "String"),
//		@ApiImplicitParam(name = "fromType", value = "订餐类型: DINGC-订餐,DIANC-点餐,ZHAOD-招待点餐,WEIX-维修人员点餐", paramType = "query", required = false, dataType = "String"),
//		@ApiImplicitParam(name = "dinnerUserType", value = "就餐用户(订餐/点餐可为空)", paramType = "query", required = false, dataType = "String"),
//		@ApiImplicitParam(name = "dinnerUserId", value = "就餐用户ID(待定-招待点餐是多个人一起, 存招待点餐人员表id)", paramType = "query", required = false, dataType = "String"),
//		@ApiImplicitParam(name = "amount", value = "金额", paramType = "query", required = false, dataType = "String")
//	})
//	public Response saveOrderList(String token, String fdDate, String mealType, String fromType, String dinnerUserName, String dinnerUserId, BigDecimal amount, @RequestBody List<RecipeRpsDto> list) {
//		return SuccessResponseData.newInstance(omOrderService.saveOrderList(token, fdDate, mealType, fromType, dinnerUserName, dinnerUserId, amount, list));
//	}
	public Response saveOneOrderList(String token, @RequestBody OmOrderListDto dto) {
		return SuccessResponse.newInstance(omOrderService.saveOneOrderList(token, dto) ? "保存成功" : "保存失败");
	}
	
	@RequestMapping("/saveBatchOrderList")
	@ApiOperation(value="批量提交",notes="批量提交",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "token", paramType = "query", required = false, dataType = "String")
	})
	public Response saveBatchOrderList(String token, @RequestBody List<OmOrderListDto> dto) {
		return SuccessResponse.newInstance(omOrderService.saveBatchOrderList(token, dto) ? "保存成功" : "保存失败");
	}
	
	@RequestMapping("/submitOrder")
	@ApiOperation(value="生成订单",notes="生成订单",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "token", paramType = "query", required = false, dataType = "String")
	})
	public Response submitOrder(String token, @RequestBody OmOrderDto dto) {
		return SuccessResponse.newInstance(omOrderService.submitOrder(token, dto) ? "保存成功" : "保存失败");
	}
	
	@RequestMapping("/findByFromTypeAndStatus")
	@ApiOperation(value="查找已选择提交的菜单信息",notes="查找已选择提交的菜单信息",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "token", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "fromType", value = "fromType-订餐类型: DINGC-订餐,DIANC-点餐,ZHAOD-招待点餐,WEIX-维修人员点餐", paramType = "query", required = true, dataType = "String")
	})
	public Response findByFromTypeAndStatus(String token, String fromType) {
		return SuccessResponseData.newInstance(omOrderService.findByFromTypeAndStatus(token, fromType, "10"));
	}
	
	@RequestMapping("/findOrderByFromType")
	@ApiOperation(value="查找已付款未到的订单",notes="查找已付款未到的订单",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "token", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "fromType", value = "fromType-订餐类型: DINGC-订餐,DIANC-点餐,ZHAOD-招待点餐,WEIX-维修人员点餐", paramType = "query", required = true, dataType = "String")
	})
	public Response findOrderByFromType(String token, String fromType) {
		return SuccessResponseData.newInstance(omOrderService.findOrderByFromType(token, fromType));
	}
	
	@RequestMapping("/deleteOneNotGenericOrder")
	@ApiOperation(value="删除已选择未生成订单记录",notes="删除已选择未生成订单记录",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "token", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "fdId", value = "fdId", paramType = "query", required = true, dataType = "String")
	})
	public Response deleteOneNotGenericOrder(String token, String fdId) {
		return SuccessResponse.newInstance(omOrderService.deleteOneNotGenericOrder(token, fdId) ? "删除成功" : "删除失败");
	}
}
