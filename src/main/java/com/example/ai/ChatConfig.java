package com.example.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {

    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {

        return builder.defaultSystem(" You are a helpful assistant Keep answers concise unless asked for detail.").build();
    }
}
