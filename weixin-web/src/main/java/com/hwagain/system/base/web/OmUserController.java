package com.hwagain.system.base.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponse;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.web.common.controller.BaseController;
import com.hwagain.system.base.dto.OmUserDto;
import com.hwagain.system.base.service.IOmUserService;
import com.hwagain.system.util.ConstantUtil;

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
 * @since 2019-08-09
 */
@RestController
@RequestMapping(value="/base/omUser",method={RequestMethod.GET,RequestMethod.POST})
@Api(value="用户信息", description="用户信息")
public class OmUserController extends BaseController{
	
	private final static Logger logger = LoggerFactory.getLogger(OmUserController.class);
	
	@Autowired
	IOmUserService omUserService;
	
	@RequestMapping("/register")
	@ApiOperation(value="注册",notes="注册",httpMethod="POST")
	public Response register(@RequestBody OmUserDto dto){
		return SuccessResponse.newInstance(omUserService.saveOne(dto) ? "保存成功":"保存失败");
	}
	@RequestMapping("/sendCheckCode")
	@ApiOperation(value="获取验证码",notes="获取验证码",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "phone", value = "手机号", paramType = "query", required = true, dataType = "String")
	})
	public Response sendCheckCode(String phone){
		return SuccessResponse.newInstance(omUserService.sendCheckCode(phone));
	}
	
    @PostMapping("/login")
    @ApiOperation(value = "手机登录", notes = "手机登录",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号码", paramType = "query", required = true, dataType = "string"),
            @ApiImplicitParam(name = "vcode", value = "验证码", paramType = "query", required = true, dataType = "string")
    })
    public Response dealerLogin(String phone, String vcode) {
    	return SuccessResponseData.newInstance(omUserService.login(phone, vcode));
    }
    @PostMapping("/checklogin")
    @ApiOperation(value = "手机登录", notes = "手机登录",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "token", paramType = "header", required = true, dataType = "string")
    })
    public Response checklogin() {
    	return SuccessResponseData.newInstance(omUserService.checklogin(null));
    }
	
	@RequestMapping("/wxLogin")
	@ApiOperation(value="小程序登录",notes="小程序登录",httpMethod="POST")
	public Response wxLogin(String code) {
		logger.info("wxLogin code: " + code);
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + ConstantUtil.APP_ID + "&secret=" + ConstantUtil.APP_SECRET + "&js_code=" + code + "&grant_type=authorization_code";
		logger.info("wxLogin url: " + url);
		RestTemplate rest = new RestTemplate();
		String json = rest.getForEntity(url, String.class).getBody();
		logger.info("wxLogin url response: " + json);
		return SuccessResponseData.newInstance(json);
	}
}
