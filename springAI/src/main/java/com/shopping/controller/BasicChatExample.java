package com.shopping.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicChatExample {
    @Autowired
    private ChatClient chatClient;


    @GetMapping("/api/simple-chat")
    public ResponseEntity<String> simpleChat(@RequestParam String question) {
        try {
            String response = chatClient.prompt()
                    .user(question)
                    .call()
                    .content();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("处理请求时发生错误: " + e.getMessage());
        }
    }
}
