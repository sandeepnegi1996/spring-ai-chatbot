package com.example.ai.chatbot;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {


    @Bean("openAiChatClient")
  public ChatClient chatClient(ChatClient.Builder builder) {

        return builder.defaultSystem(" You are a helpful and formal assistant Keep answers concise unless asked for detail.").build();
    }

}
