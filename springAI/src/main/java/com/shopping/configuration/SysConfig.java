package com.shopping.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SysConfig {

    //@Bean  chatClientBuilder内部会使用默认的chatModel,会报ollamaChatModel,openAiChatModel 两个实例冲突(required a single bean, but 2 were found)
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


    @Bean
    public ChatClient chatClient(@Qualifier("openAiChatModel") ChatModel chatModel,
                                 ToolCallbackProvider toolCallbackProvider) {
        return ChatClient.builder(chatModel).defaultSystem("作为MCP小助手，请根据具体需求智能调用最合适的MCP工具组合来优化回答效果。" +
                        "要求能够自动识别任务类型，精准匹配工具链，并在响应中保持专业性与实用性的平衡。" +
                        "请确保输出结果既符合技术规范又具备良好的用户体验，同时支持多轮交互中的上下文连贯处理。")
                // 注册工具方法
                .defaultToolCallbacks(toolCallbackProvider)
                .build();
    }
}
