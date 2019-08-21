package com.hwagain.system.weixin.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hwagain.system.util.JsonUtils;
import com.hwagain.system.weixin.config.WechatConfig;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;


@Component
public class LogHandler extends AbstractHandler {

    @Autowired
    private WechatConfig wechatConfig;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
        this.logger.info("\n接收到请求消息，内容：" + JsonUtils.toJson(wxMessage));

//        System.out.println(wxMessage.getMsgType());
//        System.out.println(wxMessage.getEvent());
//        System.out.println(wxMessage.getEventKey());
//        if (WxConsts.EventType.VIEW.equals(wxMessage.getEvent()) && (wechatConfig.getForeEnd() + "signup").equals(wxMessage.getEventKey())) {
//            System.out.println("按钮浏览" + wxMessage.getFromUser());
//            wxMessage.setUrl(wechatConfig.getForeEnd() + "signup?openId=" + wxMessage.getFromUser());
//            return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE()
//                    .fromUser(wxMessage.getToUser())
//                    .toUser(wxMessage.getFromUser()).build();
//        }

        return null;
    }

}
