package com.shopping.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SysConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder,
                                 ToolCallbackProvider toolCallbackProvider) {
        return chatClientBuilder
                .defaultSystem("作为MCP小助手，请根据具体需求智能调用最合适的MCP工具组合来优化回答效果。" +
                        "要求能够自动识别任务类型，精准匹配工具链，并在响应中保持专业性与实用性的平衡。" +
                        "请确保输出结果既符合技术规范又具备良好的用户体验，同时支持多轮交互中的上下文连贯处理。")
                // 注册工具方法
                .defaultToolCallbacks(toolCallbackProvider)
                .build();
    }
}
