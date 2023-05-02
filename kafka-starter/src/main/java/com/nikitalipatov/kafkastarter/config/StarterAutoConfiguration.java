package com.nikitalipatov.kafkastarter.config;

import com.nikitalipatov.kafkastarter.kafka.KafkaConsumerConfig;
import com.nikitalipatov.kafkastarter.kafka.KafkaProducerConfig;
import com.nikitalipatov.kafkastarter.kafka.KafkaTopicConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(StarterProperties.class)
@ConditionalOnProperty(prefix = "kafka-starter", name = {"port", "group", "command", "result"})
@RequiredArgsConstructor
public class StarterAutoConfiguration {

    @Bean
    public KafkaTopicConfig kafkaTopicConfig() {
        return new KafkaTopicConfig();
    }
//
//    @Bean
//        public ProducerFactory<String, KafkaMessage> producerFactory() {
//        Map<String, Object> configProps = new HashMap<>();
//        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
//        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//        return new DefaultKafkaProducerFactory<>(configProps);
//    }

//    @Bean
//    public KafkaService kafkaService() {
//        return new KafkaService(new KafkaTemplate<String, KafkaMessage>(producerFactory()));
//    }

    @Bean
    public KafkaProducerConfig kafkaProducerConfig() {
        return new KafkaProducerConfig();
    }

    @Bean
    public KafkaConsumerConfig kafkaConsumerConfig() {
        return new KafkaConsumerConfig();
    }
}
