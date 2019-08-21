package com.hwagain.system.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WxUtil {
	public static String getPhoneNo(String token) throws InvalidAlgorithmParameterException, UnsupportedEncodingException {
		System.out.println("getWeChatPhone:" + token);
		if (token != null && token.length() > 0 && !token.equals("")) {
			String[] tokens = token.split("\\$\\$\\$");
			String session_key = tokens[0];
			String iv = tokens[1];
			String encryptedData = tokens[2]; 	

			byte[] resultByte = null;

//			resultByte = AES.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(session_key), Base64.decodeBase64(iv));
			if (null != resultByte && resultByte.length > 0) {
				String userInfo = new String(resultByte, "UTF-8");
				JsonObject jsonObject = (JsonObject) new JsonParser().parse(userInfo).getAsJsonObject();
				return jsonObject.get("phoneNumber").getAsString();
			}

		}
		return null;
	}
}