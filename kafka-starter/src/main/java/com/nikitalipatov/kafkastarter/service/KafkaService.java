//package com.nikitalipatov.kafkastarter.service;
//
//import com.nikitalipatov.common.dto.kafka.KafkaMessage;
//import lombok.RequiredArgsConstructor;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class KafkaService {
//
//    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;
//
//    public void send(KafkaMessage kafkaMessage) {
//        kafkaTemplate.send("command", kafkaMessage);
//    }
//}
