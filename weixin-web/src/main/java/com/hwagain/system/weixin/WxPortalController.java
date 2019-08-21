package com.hwagain.system.weixin;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.hwagain.system.weixin.config.WechatConfig;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;


@RestController
@RequestMapping("/wx/portal")
public class WxPortalController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WechatConfig wechatConfig;

    @GetMapping(produces = "text/plain;charset=utf-8")
    public String authGet(@RequestParam(name = "signature", required = false) String signature,
                          @RequestParam(name = "timestamp", required = false) String timestamp,
                          @RequestParam(name = "nonce", required = false) String nonce,
                          @RequestParam(name = "echostr", required = false) String echostr) {

        log.info("\n接收到来自微信服务器的认证消息：[{"+signature+"}, {"+timestamp+"}, {"+nonce+"}, {"+echostr+"}]");
        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        final WxMpService wxService = WxMpConfiguration.getMpService();
        if (wxService == null) {
            log.error(String.format("未找到对应appid=[%s]的配置，请核实！", signature));
        }

        if (wxService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }

        return "非法请求";
    }

    @PostMapping(produces = "application/xml; charset=UTF-8")
    public String post(@RequestBody String requestBody,
                       @RequestParam("signature") String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce,
                       @RequestParam("openid") String openid,
                       @RequestParam(name = "encrypt_type", required = false) String encType,
                       @RequestParam(name = "msg_signature", required = false) String msgSignature) {
        final WxMpService wxService = WxMpConfiguration.getMpService();
        log.info("\n接收微信请求：[openid=[{"+openid+"}], [signature=[{"+signature+"}], encType=[{"+encType+"}], msgSignature=[{"+msgSignature+"}],"
                + " timestamp=[{"+timestamp+"}], nonce=[{"+nonce+"}], requestBody=[\n{"+requestBody+"}\n] ");

        if (!wxService.checkSignature(timestamp, nonce, signature)) {
            log.error("非法请求，可能属于伪造的请求！");
        }

        String out = null;
        if (encType == null) {
            // 明文传输的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
            WxMpXmlOutMessage outMessage = this.route(inMessage);
            if (outMessage == null) {
                return "";
            }

            out = outMessage.toXml();
        } else if ("aes".equalsIgnoreCase(encType)) {
            // aes加密的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody, wxService.getWxMpConfigStorage(),
                timestamp, nonce, msgSignature);
            log.debug("\n消息解密后内容为：\n{"+inMessage.toString()+"} ");
            WxMpXmlOutMessage outMessage = this.route(inMessage);
            if (outMessage == null) {
                return "";
            }

            out = outMessage.toEncryptedXml(wxService.getWxMpConfigStorage());
        }

        log.debug("\n组装回复信息：{"+out+"}");
        return out;
    }

    private WxMpXmlOutMessage route(WxMpXmlMessage message) {
        try {
            return WxMpConfiguration.getRouter().route(message);
        } catch (Exception e) {
            log.error("路由消息时出现异常！", e);
        }

        return null;
    }



    //获得授权地址
    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl){
        String url = "http://888xin.nat300.top/wx/portal/userInfo";
        final WxMpService wxService = WxMpConfiguration.getMpService();
        String redirectURL = wxService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl));
        log.info("【微信网页授权】获取code,redirectURL={"+redirectURL+"}");
        return "redirect:" + redirectURL;
    }

    @GetMapping("/getcode")
    public String getcode(@RequestParam("code") String code){
        return "code:" + code;
    }

    //从上面的授权地址获得code，重定向这个接口获得AccessToken
    //获得AccessToken
    @GetMapping("/userInfo")
    public void userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String state,
                           HttpServletResponse response) throws Exception {
        log.info("【微信网页授权】code={"+code+"}");
        log.info("【微信网页授权】state={"+state+"}");
        final WxMpService wxService = WxMpConfiguration.getMpService();
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken;
        try {
            wxMpOAuth2AccessToken = wxService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.info("【微信网页授权】{}", e);
            throw new Exception(e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        log.info("【微信网页授权】openId={"+openId+"}");
        //return "AccessToken:" + wxMpOAuth2AccessToken.getAccessToken() + " Openid=" + openId;
        //response.sendRedirect(wechatConfig.getDomain()+"jump/register?openId=" + openId);
        //System.out.println(wechatConfig.getForeEnd()+"signup?openId=" + openId);
        response.sendRedirect(wechatConfig.getForeEnd()+"signup?openId=" + openId);
    }



    @GetMapping("/main")
    public void main(@RequestParam("code") String code, HttpServletResponse response) throws Exception {
        log.info("【微信网页授权】code={"+code+"}");
        final WxMpService wxService = WxMpConfiguration.getMpService();
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken;
        try {
            wxMpOAuth2AccessToken = wxService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.info("【微信网页授权】{}", e);
            throw new Exception(e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        log.info("【微信网页授权】openId={"+openId+"}");
        response.sendRedirect(wechatConfig.getForeEnd()+"apps?openId=" + openId);
    }

    @GetMapping("/login")
    public void login(@RequestParam("code") String code, HttpServletResponse response) throws Exception {
        log.info("【微信网页授权】code={"+code+"}");
        final WxMpService wxService = WxMpConfiguration.getMpService();
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken;
        try {
            wxMpOAuth2AccessToken = wxService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.info("【微信网页授权】{}", e);
            throw new Exception(e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        log.info("【微信网页授权】openId={"+openId+"}");
        response.sendRedirect(wechatConfig.getForeEnd()+"login?openId=" + openId);
    }

    @GetMapping("/update")
    public void update(@RequestParam("code") String code, HttpServletResponse response) throws Exception {
        log.info("【微信网页授权】code={"+code+"}");
        final WxMpService wxService = WxMpConfiguration.getMpService();
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken;
        try {
            wxMpOAuth2AccessToken = wxService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.info("【微信网页授权】{}", e);
            throw new Exception(e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        log.info("【微信网页授权】openId={"+openId+"}");
        response.sendRedirect(wechatConfig.getForeEnd()+"user/update?openId=" + openId);
    }
    @RequestMapping("/menu")
    private String setMenu() {
        // 1.根据appid和appsecret和回调地址配置微信授权
        WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpConfigStorage.setAppId(wechatConfig.getAppId());
        wxMpConfigStorage.setSecret(wechatConfig.getSecret());
        WxMpServiceImpl wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage);
 
        /**
         *  2. 设置按钮
         *  menu对象是总的按钮,button是具体的按钮
         */
        WxMenu menu = new WxMenu();
        WxMenuButton button1 = new WxMenuButton();
        button1.setType(WxConsts.MenuButtonType.VIEW);
        button1.setName("登录网厅");
        button1.setUrl(wechatConfig.getForeEnd()+"apps");
 
//        WxMenuButton button2 = new WxMenuButton();
//        button2.setType(WxConsts.MenuButtonType.CLICK);
//        button2.setName("帮助说明");
// 
        // 3. 添加到menu
        menu.getButtons().add(button1);
//        menu.getButtons().add(button2);
        String result = menu.toJson().toString();
        System.out.println(result);
 
        // 根据运行结果返回相应的字符串
        try {
            wxMpService.getMenuService().menuCreate(result);
            return "SUCCESS"+" "+result;
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return "FAILURE"+result;
    }

}
