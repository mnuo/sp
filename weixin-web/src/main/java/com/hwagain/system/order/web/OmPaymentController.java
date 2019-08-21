package com.hwagain.system.order.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hwagain.framework.web.common.controller.BaseController;
import com.hwagain.system.order.service.IOmPaymentService;

/**
 * <p>
 * 支付表 前端控制器
 * </p>
 *
 * @author mon
 * @since 2019-08-16
 */
@RestController
@RequestMapping(value="/order/omPayment",method={RequestMethod.GET,RequestMethod.POST})
public class OmPaymentController extends BaseController{
	
	@Autowired
	IOmPaymentService omPaymentService;
	
}
