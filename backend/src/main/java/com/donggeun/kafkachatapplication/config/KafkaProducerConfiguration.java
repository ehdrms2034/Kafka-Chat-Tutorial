package com.donggeun.kafkachatapplication.config;

import com.donggeun.kafkachatapplication.model.ChatMessage;
import com.donggeun.kafkachatapplication.model.serializer.ChatMessageSerializer;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfiguration {

    public static final String CHAT_TOPIC_NAME = "dev.chat.message";
    private final KafkaHostProperties kafkaHostProperties;


    @Bean
    public ProducerFactory<String, ChatMessage> producerFactory() {
        return new DefaultKafkaProducerFactory<String, ChatMessage>(kafkaProducerConfigParams());
    }

    @Bean
    public Map<String, Object> kafkaProducerConfigParams() {
        Map<String, Object> props = Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaHostProperties.getBootstrapServers(),
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ChatMessageSerializer.class
        );
        return props;
    }

    @Bean
    public KafkaTemplate<String, ChatMessage> chatMessageProducerTemplate() {
        return new KafkaTemplate<String, ChatMessage>(producerFactory());
    }

    @Bean
    public NewTopic configureChatTopic() {

        return TopicBuilder.name(KafkaProducerConfiguration.CHAT_TOPIC_NAME)
                .partitions(6)
                .replicas(2)
                .build();
    }

}