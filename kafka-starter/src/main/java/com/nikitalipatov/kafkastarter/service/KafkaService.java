package com.nikitalipatov.kafkastarter.service;

import com.nikitalipatov.common.dto.kafka.KafkaMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public class KafkaService {

    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    public void send(String topic, KafkaMessage kafkaMessage) {
        kafkaTemplate.send(topic, kafkaMessage);
    }
}
