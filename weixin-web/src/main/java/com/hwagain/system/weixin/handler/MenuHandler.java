package com.hwagain.system.weixin.handler;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hwagain.system.weixin.config.WechatConfig;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;


@Component
public class MenuHandler extends AbstractHandler {

    @Autowired
    private WechatConfig wechatConfig;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {

        String msg = String.format("type:%s, event:%s, key:%s",
            wxMessage.getMsgType(), wxMessage.getEvent(),
            wxMessage.getEventKey());
//        if (WxConsts.EventType.VIEW.equals(wxMessage.getEvent())) {
//
//            if ((wechatConfig.getForeEnd() + "signup").equals(wxMessage.getEventKey())) {
//                System.out.println("按钮浏览" + wxMessage.getFromUser());
//                String newUrl = wechatConfig.getForeEnd() + "signup?openId=" + wxMessage.getFromUser();
//                wxMessage.setUrl(newUrl);
//                wxMessage.setEventKey(newUrl);
//                return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE()
//                        .fromUser(wxMessage.getToUser())
//                        .toUser(wxMessage.getFromUser()).build();
//            } else if ((wechatConfig.getDomain() + "jump/test").equals(wxMessage.getKeyStr())) {
////                System.out.println("按钮浏览" + wxMessage.getFromUser());
//                String newUrl = wechatConfig.getDomain() + "jump/test?openId=" + wxMessage.getFromUser();
////                wxMessage.setUrl(newUrl);
////                wxMessage.setEventKey(newUrl);
////                wxMessage.getAllFieldsMap().put("EventKey",newUrl);
////                return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE()
////                        .fromUser(wxMessage.getToUser())
////                        .toUser(wxMessage.getFromUser()).build();
//
//
//
//                WxMpXmlOutNewsMessage.Item article = new WxMpXmlOutNewsMessage.Item();
//                article.setTitle("感谢关注华劲招投标微信公众号");
//
//                //article.setDescription("招投标项目微信公众号！大家来招投标吧！这里，将对你提供优质的服务！");
//                article.setPicUrl(wechatConfig.getDomain() + "images/wood.jpg");
//                article.setUrl(newUrl);
//
//                WxMpXmlOutNewsMessage newsMessage = new WxMpXmlOutNewsMessage();
//                newsMessage.setToUserName(wxMessage.getFromUser());
//                newsMessage.setFromUserName(wxMessage.getToUser());
//                newsMessage.setCreateTime(new Date().getTime());
//                newsMessage.setMsgType(WxConsts.XmlMsgType.NEWS);
//                newsMessage.addArticle(article);
//
//                return newsMessage;
//
//
//
//            }
//            System.out.println("按钮浏览" + wxMessage.getFromUser());
//            return null;
//        } else if (WxConsts.EventType.CLICK.equals(wxMessage.getEvent())) {
//            System.out.println("按钮点击" + wxMessage.getFromUser());
//            String newUrl = wechatConfig.getDomain() + "jump/test?openId=" + wxMessage.getFromUser();
//            WxMpXmlOutNewsMessage.Item article = new WxMpXmlOutNewsMessage.Item();
//            article.setTitle("欢迎注册华劲招投标系统");
//
//            article.setDescription("点击进入注册页面");
//            article.setPicUrl(wechatConfig.getDomain() + "images/wood.jpg");
//            article.setUrl(newUrl);
//
//            WxMpXmlOutNewsMessage newsMessage = new WxMpXmlOutNewsMessage();
//            newsMessage.setToUserName(wxMessage.getFromUser());
//            newsMessage.setFromUserName(wxMessage.getToUser());
//            newsMessage.setCreateTime(new Date().getTime());
//            newsMessage.setMsgType(WxConsts.XmlMsgType.NEWS);
//            newsMessage.addArticle(article);
//
//            return newsMessage;
//        }

        return WxMpXmlOutMessage.TEXT().content(msg)
            .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
            .build();
    }

}
