package com.donggeun.kafkachatapplication;

import com.donggeun.kafkachatapplication.config.KafkaConfiguration;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class KafkaChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaChatApplication.class, args);
    }

//    @KafkaListener(id = "" ,topics = "testTopics")
//    public void listen(String in){
//        System.out.println(in);
//    }

    @Bean
    public ApplicationRunner runner(KafkaTemplate<String,String> template){
        return args -> {
            template.send(KafkaConfiguration.CHAT_TOPIC_NAME, "Hello World");
        };
    }

}
