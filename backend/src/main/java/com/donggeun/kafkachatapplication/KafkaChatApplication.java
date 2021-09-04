package com.donggeun.kafkachatapplication;

import com.donggeun.kafkachatapplication.config.KafkaProducerConfiguration;
import com.donggeun.kafkachatapplication.model.ChatMessage;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
@EnableConfigurationProperties
@EnableKafka
public class KafkaChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaChatApplication.class, args);
    }

//    @KafkaListener(id = "" ,topics = "testTopics")
//    public void listen(String in){
//        System.out.println(in);
//    }

//    @Bean
//    public ApplicationRunner runner(KafkaTemplate<String,ChatMessage> template){
//        ChatMessage test = ChatMessage.builder()
//                .messageId(0L)
//                .userId(0L)
//                .roomId(0L)
//                .username("test")
//                .content("하이")
//                .build();
//
//        return args -> {
//            template.send(KafkaProducerConfiguration.CHAT_TOPIC_NAME,"0번방", test);
//        };
//    }

}
