package com.shopping.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class ChatExample {
    @Autowired
    private ChatClient chatClient;


    @GetMapping("/simple")
    public ResponseEntity<String> simpleChat(@RequestParam String message) {
        try {
            String response = chatClient.prompt()
                    .user(message)
                    .call()
                    .content();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("处理请求时发生错误: " + e.getMessage());
        }
    }

    @GetMapping(value = "/stream", produces = "text/html;charset=UTF-8")
    public Flux<String> stream(@RequestParam String message) {
        return chatClient
                .prompt()
                .user(message)
                .stream()
                .content();
    }
}
