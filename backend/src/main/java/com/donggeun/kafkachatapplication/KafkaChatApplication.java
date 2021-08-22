package com.donggeun.kafkachatapplication;

import com.donggeun.kafkachatapplication.config.KafkaProducerConfiguration;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
@EnableConfigurationProperties
public class KafkaChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaChatApplication.class, args);
    }

//    @KafkaListener(id = "" ,topics = "testTopics")
//    public void listen(String in){
//        System.out.println(in);
//    }

//    @Bean
//    public ApplicationRunner runner(KafkaTemplate<String,String> template){
//        return args -> {
//            template.send(KafkaProducerConfiguration.CHAT_TOPIC_NAME, "Hello World");
//        };
//    }

}
