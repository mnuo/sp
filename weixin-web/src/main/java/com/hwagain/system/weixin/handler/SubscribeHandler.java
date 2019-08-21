package com.hwagain.system.weixin.handler;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hwagain.framework.mybatisplus.toolkit.IdWorker;
import com.hwagain.system.base.entity.WxUser;
import com.hwagain.system.base.service.IWxUserService;
import com.hwagain.system.weixin.config.WechatConfig;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;


@Component
public class SubscribeHandler extends AbstractHandler {

    @Autowired
    private IWxUserService wxUserService ;

    @Autowired
    private WechatConfig wechatConfig;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {

        this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser());

        // 获取微信用户基本信息
        try {
            WxMpUser userWxInfo = weixinService.getUserService()
                .userInfo(wxMessage.getFromUser(), null);
            if (userWxInfo != null) {
                // 可以添加关注用户到本地数据库
                WxUser weixinUser = new WxUser();
                weixinUser.setFdId(IdWorker.getId());
                weixinUser.setOpenId(userWxInfo.getOpenId());
                weixinUser.setSex(userWxInfo.getSex());
                weixinUser.setProvince(userWxInfo.getProvince());
                weixinUser.setCity(userWxInfo.getCity());
                weixinUser.setCountry(userWxInfo.getCountry());
                weixinUser.setNickName(userWxInfo.getNickname());
                weixinUser.setHeadImgUrl(userWxInfo.getHeadImgUrl());
                weixinUser.setCreateTime(new Date());
                wxUserService.insert(weixinUser);
            }




//            File file = new File("G:\\lhx\\forestry-wechat\\images\\wood.jpg");
//            InputStream input = new FileInputStream(file);
//            WxMediaUploadResult uploadMediaRes = weixinService.getMaterialService().mediaUpload(WxConsts.MediaFileType.IMAGE, "jpg", input);
//
//            WxMpSubscribeMessage wxMpSubscribeMessage = new WxMpSubscribeMessage();
//            wxMpSubscribeMessage.setContentValue("华劲公司欢迎您！");
//            wxMpSubscribeMessage.setToUser(userWxInfo.getOpenId());
//            wxMpSubscribeMessage.setTemplateId(uploadMediaRes.getThumbMediaId());
//            wxMpSubscribeMessage.setScene(uploadMediaRes.get);
//            weixinService.getSubscribeMsgService().sendSubscribeMessage(wxMpSubscribeMessage);


//            File file = new File("G:\\lhx\\forestry-wechat\\images\\wood.jpg");
//            InputStream input = new FileInputStream(file);
//            WxMediaUploadResult uploadMediaRes = weixinService.getMaterialService().mediaUpload(WxConsts.MediaFileType.IMAGE, "jpg", input);

            WxMpXmlOutNewsMessage.Item article = new WxMpXmlOutNewsMessage.Item();
            article.setTitle("感谢关注华劲订餐系统微信公众号");

            article.setDescription("订餐系统微信公众号！大家来订餐吧！这里，将对你提供优质的服务！");
//            article.setPicUrl(wechatConfig.getDomain() + "images/wood.jpg");
            article.setUrl(wechatConfig.getForeEnd()+"apps");

            WxMpXmlOutNewsMessage newsMessage = new WxMpXmlOutNewsMessage();
            newsMessage.setToUserName(wxMessage.getFromUser());
            newsMessage.setFromUserName(wxMessage.getToUser());
            newsMessage.setCreateTime(new Date().getTime());
            newsMessage.setMsgType(WxConsts.XmlMsgType.NEWS);
            newsMessage.addArticle(article);

            return newsMessage;


        } catch (WxErrorException e) {
            if (e.getError().getErrorCode() == 48001) {
                this.logger.info("该公众号没有获取用户信息权限！");
            }
        } catch (Exception e) {

        }


        WxMpXmlOutMessage responseResult = null;
        try {
            responseResult = this.handleSpecial(wxMessage);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        if (responseResult != null) {
            return responseResult;
        }

//        try {
//
//            return new TextBuilder().build("感谢关注", wxMessage, weixinService);
//        } catch (Exception e) {
//            this.logger.error(e.getMessage(), e);
//        }

        return null;
    }

    /**
     * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
     */
    private WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage)
        throws Exception {
        //TODO
        return null;
    }

}
