package com.shopping.controller;

import com.shopping.vo.ChatRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ToolEnhancedChatExample {
    @Autowired
    private ChatClient toolEnabledClient;

    @PostMapping("/api/tool-chat")
    public Map<String, Object> toolChat(@RequestBody ChatRequest request) {
        long startTime = System.currentTimeMillis();

        String response = toolEnabledClient.prompt()
                .user(request.getMessage())
                .call()
                .content();

        long duration = System.currentTimeMillis() - startTime;

        return Map.of(
                "response", response,
                "duration", duration,
                "timestamp", System.currentTimeMillis()
        );
    }
}
