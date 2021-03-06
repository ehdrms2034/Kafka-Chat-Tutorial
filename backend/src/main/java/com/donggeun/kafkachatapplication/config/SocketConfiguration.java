package com.donggeun.kafkachatapplication.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class SocketConfiguration implements WebSocketMessageBrokerConfigurer {

    static public String TOPIC_PREFIX= "/topic";

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        config.enableSimpleBroker(TOPIC_PREFIX);
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socket-chat")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

}
