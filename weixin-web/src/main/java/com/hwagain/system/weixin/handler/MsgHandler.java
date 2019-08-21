package com.hwagain.system.weixin.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hwagain.system.util.JsonUtils;
import com.hwagain.system.weixin.builder.TextBuilder;
import com.hwagain.system.weixin.config.WechatConfig;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts.XmlMsgType;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;


@Component
@Slf4j
public class MsgHandler extends AbstractHandler {

    @Autowired
    private WechatConfig wechatConfig;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {

        if (!wxMessage.getMsgType().equals(XmlMsgType.EVENT)) {
            //TODO 可以选择将消息保存到本地
        }

        //当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
//        try {
//            if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
//                && weixinService.getKefuService().kfOnlineList()
//                .getKfOnlineList().size() > 0) {
//                return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE()
//                    .fromUser(wxMessage.getToUser())
//                    .toUser(wxMessage.getFromUser()).build();
//            } else if ("图片".equals(wxMessage.getContent())){
//
//                log.debug("图片开始加载");
//                try {
//                    File file = new File("F:\\www1\\888xin2-4028800b67b39f320167ca87d03c0513\\webapp\\images\\mm.jpeg");
//                    InputStream input = new FileInputStream(file);
//                    //WxMediaUploadResult wxMediaUploadResult = weixinService.getMaterialService().mediaUpload(WxConsts.MediaFileType.IMAGE, Constants.FILE_JPG, ClassLoader.getSystemResourceAsStream("static/images/mm.jpeg"));
//                    WxMediaUploadResult wxMediaUploadResult = weixinService.getMaterialService().mediaUpload(WxConsts.MediaFileType.IMAGE, Constants.FILE_JPG, input);
//                    WxMpXmlOutImageMessage m
//                            = WxMpXmlOutMessage
//                            .IMAGE()
//                            .mediaId(wxMediaUploadResult.getMediaId())
//                            .fromUser(wxMessage.getToUser())
//                            .toUser(wxMessage.getFromUser())
//                            .build();
//                    log.debug("图片开始加载完" + m.getMediaId());
//                    return m;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                return new TextBuilder().build("获取图片出错！", wxMessage, weixinService);
//            } else if ("登录".equals(wxMessage.getContent())){
//                return new TextBuilder().build("http://888xin2.jsp.jspee.org", wxMessage, weixinService);
//            } else if ("授权".equals(wxMessage.getContent())){
//                return new TextBuilder().build("OAuth2.0网页授权演示 \n" +
//                        "<a href=\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd735cdab1c124e9d&redirect_uri=http%3A%2F%2F888xin.nat300.top%2Fwx%2Fportal%2FuserInfo&response_type=code&scope=snsapi_userinfo&state=%27%2Ftest%2Fflow%27&connect_redirect=1#wechat_redirect\">点击这里体验</a>\n" +
//                        "技术支持 华劲工作室\n" +
//                        "\n", wxMessage, weixinService);
//            } else if ("图文".equals(wxMessage.getContent())){
//
//
//
//                WxMpXmlOutNewsMessage.Item article = new WxMpXmlOutNewsMessage.Item();
//                article.setTitle("测试图文消息");
//                article.setDescription("测试图文消息，招投标项目测试，看看是怎么样的！大家来招投标吧！");
//                article.setPicUrl(wechatConfig.getDomain() + "images/abb186f2-1d7c-4982-b9b9-dc09b0fc8d63.jpg");
//                article.setUrl("https://www.baidu.com");
//
//                WxMpXmlOutNewsMessage newsMessage = new WxMpXmlOutNewsMessage();
//                newsMessage.setToUserName(wxMessage.getFromUser());
//                newsMessage.setFromUserName(wxMessage.getToUser());
//                newsMessage.setCreateTime(new Date().getTime());
//                newsMessage.setMsgType(XmlMsgType.NEWS);
//                newsMessage.addArticle(article);
//
//                return newsMessage;
//
//            } else {
//                StringBuilder builder = new StringBuilder();
//                builder.append("您好，我是小q，请回复数字选择服务：").append("\n\n");
//                builder.append("1  投标流程").append("\n");
//                builder.append("2  招标信息").append("\n");
//                builder.append("3  投标报名").append("\n");
//                builder.append("4  合同条款确认").append("\n");
//                builder.append("5  保证金到账确认").append("\n");
//                builder.append("6  开始出价").append("\n");
//                builder.append("7  中标公告").append("\n");
//                builder.append("8  我的投标").append("\n\n");
//                builder.append("回复“?”显示此帮助菜单");
//                return new TextBuilder().build(builder.toString(), wxMessage, weixinService);
//            }
//        } catch (WxErrorException e) {
//            e.printStackTrace();
//        }

        //TODO 组装回复消息
        String content = "收到信息内容：" + JsonUtils.toJson(wxMessage);

        return new TextBuilder().build(content, wxMessage, weixinService);

    }

//    public static void main(String[] args) throws IOException {
//        InputStream inputStream = ClassLoader.getSystemResourceAsStream("static/images/mm.jpeg");
//        BufferedImage bufferedImage = ImageIO.read(inputStream);
//        System.out.println(bufferedImage.getHeight());
//    }


}
