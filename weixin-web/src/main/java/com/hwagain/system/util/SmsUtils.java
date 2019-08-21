package com.hwagain.system.util;

import com.hwagain.info.sms.dto.SendSmsDto;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

public class SmsUtils {

    private final static String SMS_URL = "http://sms.hwagain.com:60015/info-center/sms/infoSmsSend/saveSms";

    public static void sendSms(String content, String fromEmpNo, List<String> persons){
        SendSmsDto sendSmsDto = new SendSmsDto();
        //发送内容
        sendSmsDto.setContent(content);
        //发送员工编号
        sendSmsDto.setCreateUserCode(fromEmpNo);
        //来源ID
        //sendSmsDto.setSourceId("");
        //系统编号
        sendSmsDto.setSystemCode("job-progress");
        //List<String> list = Arrays.asList("15607896907");
        sendSmsDto.setPhones(persons);
        doPost(SMS_URL, sendSmsDto);
    }

    private static String doPost(String url, Object object) {
        RestTemplate restTemplate = new RestTemplate();

        //创建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //params是url中出现的路径变量
        //Map<String, String> params = new HashMap<String, String>();
        //params.put("id", "123");
        //User student = new User("sansan",10);

        //entity包含请求的对象和消息头；
        HttpEntity<Object> entity = new HttpEntity<>(object, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        String s = responseEntity.getBody();//{"msg":"调用成功！","code":1}
        System.out.println(s);
        return s;
    }

    public static String getRandom6Code(){
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        return verifyCode;
    }


}
