package com.shopping.api.modules.user.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * dubbo rest 协议访问路径如下:http://localhost:8089/helloService/sayHello
 */
@RestController
@RequestMapping("/helloService")
public interface HelloService {
    @GetMapping("/sayHello")
    String sayHello(String name);
}
