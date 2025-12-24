package com.shopping;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.shopping.modules")
//@EnableDubbo
@DubboComponentScan("com.shopping.modules")
@Slf4j
public class WebService1 {
    public static void main(String[] args) {
        SpringApplication.run(WebService1.class, args);
    }
}