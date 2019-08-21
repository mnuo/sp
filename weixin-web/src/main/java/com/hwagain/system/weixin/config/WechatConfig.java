package com.hwagain.system.weixin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.hwagain.system.util.JsonUtils;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "wx.mp")
public class WechatConfig {

    /**
     * 设置微信公众号的appid
     */
    private String appId;

    /**
     * 设置微信公众号的app secret
     */
    private String secret;

    /**
     * 设置微信公众号的token
     */
    private String token;

    /**
     * 设置微信公众号的EncodingAESKey
     */
    private String aesKey;

    /**
     * 微信公众号调用的域名
     */
    private String domain;
//    /**
//     * 前端登录界面
//     */
//    private String foreEndLogin;
//    /**
//     * 前端主界面
//     */
//    private String foreEndMain;
    /**
     * 前端界面域名
     */
    private String foreEnd;

    private String localDomain;

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAesKey() {
		return aesKey;
	}

	public void setAesKey(String aesKey) {
		this.aesKey = aesKey;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getForeEnd() {
		return foreEnd;
	}

	public void setForeEnd(String foreEnd) {
		this.foreEnd = foreEnd;
	}

	public String getLocalDomain() {
		return localDomain;
	}

	public void setLocalDomain(String localDomain) {
		this.localDomain = localDomain;
	}
    
}
