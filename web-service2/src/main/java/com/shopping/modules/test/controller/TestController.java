package com.shopping.modules.test.controller;

import com.shopping.api.modules.user.service.UserService;
import com.shopping.api.modules.user.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    @Autowired
    private RestTemplate restTemplate;

    @DubboReference
    private UserService userService;

    @GetMapping("/test")
    public String getTest() {
        try {
            String resultObject = restTemplate.getForObject("http://web-service1/learning/user/list", String.class);
            log.info(resultObject);
            return resultObject;
        } catch (RestClientException e) {
            log.error("Failed to connect to web-service1", e);
            return "Error: Unable to connect to web-service1. Check if the service is running and registered with Nacos.";
        }
    }

    @GetMapping("/test1")
    public String getTest1() {
        List<UserVo> userVoList = userService.findList("陈帅");
        log.info("{}", userVoList);
        return "123456789";
    }
}