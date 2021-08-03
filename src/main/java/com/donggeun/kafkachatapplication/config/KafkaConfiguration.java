package com.donggeun.kafkachatapplication.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {

    public static String CHAT_TOPIC_NAME = "chat-message";

    @Bean
    public NewTopic configureChatTopic(){
        return TopicBuilder.name(CHAT_TOPIC_NAME)
                .partitions(6)
                .replicas(2)
                .build();
    }

}