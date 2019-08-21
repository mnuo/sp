package com.hwagain.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = { "com.hwagain" })
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
@MapperScan({ "com.hwagain.system.**.mapper*" })
@ImportResource({"classpath:dubboConsumer.xml"})
public class OrderMealApplicationMain {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(OrderMealApplicationMain.class, args);
		System.err.println("启动成功");
	}

}


