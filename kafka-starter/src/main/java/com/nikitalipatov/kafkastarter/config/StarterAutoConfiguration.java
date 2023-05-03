package com.nikitalipatov.kafkastarter.config;

import com.nikitalipatov.common.dto.kafka.KafkaMessage;
import com.nikitalipatov.kafkastarter.service.KafkaService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(StarterProperties.class)
@ConditionalOnProperty(prefix = "kafka-starter")
@RequiredArgsConstructor
public class StarterAutoConfiguration {

    private final KafkaProperties kafkaProperties;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic command() {
        return TopicBuilder.name("command")
                .partitions(1)
                .build();
    }

    @Bean
    public NewTopic result() {
        return TopicBuilder.name("result")
                .partitions(1)
                .build();
    }

    @Bean
    public ProducerFactory<String, KafkaMessage> producerFactory() {
        var producerProperties = kafkaProperties.buildProducerProperties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(producerProperties);
    }

    @Bean
    public KafkaTemplate<String, KafkaMessage> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public KafkaService kafkaService(KafkaTemplate<String, KafkaMessage> kafkaTemplate) {
        return new KafkaService(kafkaTemplate);
    }

//    @Bean
//    public KafkaProducerConfig kafkaProducerConfig() {
//        return new KafkaProducerConfig();
//    }
//
//    @Bean
//    public KafkaConsumerConfig kafkaConsumerConfig() {
//        return new KafkaConsumerConfig();
//    }
}
