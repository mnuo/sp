package com.hwagain.system.order.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hwagain.framework.web.common.controller.BaseController;
import com.hwagain.system.order.service.IOmRefundService;

/**
 * <p>
 * 退款表 前端控制器
 * </p>
 *
 * @author mon
 * @since 2019-08-16
 */
@RestController
@RequestMapping(value="/order/omRefund",method={RequestMethod.GET,RequestMethod.POST})
public class OmRefundController extends BaseController{
	
	@Autowired
	IOmRefundService omRefundService;
	
}
