package com.shopping.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class StreamingChatExample {
    @Autowired
    private ChatClient chatClient;

    @GetMapping(value = "/api/stream-chat", produces = "text/html;charset=UTF-8")
    public Flux<String> streamChat(@RequestParam String message) {
        return chatClient.prompt()
                .user(message)
                .stream()
                .content()
                .map(chunk -> "data: " + chunk + "\n\n");
    }

    @GetMapping(value = "/api/stream-chat-2", produces = "text/html;charset=UTF-8")
    public Flux<String> streamChat2(@RequestParam String message) {
        return chatClient
                .prompt()
                .user(message)
                .stream()
                .content();
    }
}
