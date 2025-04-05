package com.ai.aidemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {
    @Value("${ai.system.message}")
    private String systemMessage;

    @Bean
    public String systemMessage() {
        return systemMessage;
    }
}


//    @Bean
//    public OpenAiChatModel openAiChatModel(OpenAiChatModel chatModel,
//                                           @Value("${ai.system.message:Robin is a helpful AI assistant for farm management}") String systemMessage) {
//        return chatModel;
//    }

