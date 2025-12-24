package com.shopping.controller;

import com.shopping.vo.ChatRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ToolEnhancedChatExample {

    private final ChatClient toolEnabledClient;

    public ToolEnhancedChatExample(ChatClient.Builder builder,
                                   ToolCallbackProvider toolProvider) {
        this.toolEnabledClient = builder
                .defaultSystem("作为MCP小助手，请根据具体需求智能调用最合适的MCP工具组合来优化回答效果。" +
                        "要求能够自动识别任务类型，精准匹配工具链，并在响应中保持专业性与实用性的平衡。" +
                        "请确保输出结果既符合技术规范又具备良好的用户体验，同时支持多轮交互中的上下文连贯处理。")
                .defaultToolCallbacks(toolProvider)
                .build();
    }

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
