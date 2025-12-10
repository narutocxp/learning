package com.shopping.modules.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    @Autowired
    private RestTemplate restTemplate;

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
}