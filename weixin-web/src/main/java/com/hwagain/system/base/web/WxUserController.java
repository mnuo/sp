package com.hwagain.system.base.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hwagain.framework.web.common.controller.BaseController;
import com.hwagain.system.base.service.IWxUserService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2019-08-21
 */
@RestController
@RequestMapping(value="/base/wxUser",method={RequestMethod.GET,RequestMethod.POST})
public class WxUserController extends BaseController{
	
	@Autowired
	IWxUserService wxUserService;
	
}
