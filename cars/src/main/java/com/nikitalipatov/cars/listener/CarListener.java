package com.nikitalipatov.cars.listener;

import com.nikitalipatov.cars.service.CarService;
import com.nikitalipatov.common.dto.kafka.KafkaMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(topics = "${kafka-starter.command}", groupId = "${kafka-starter.group}", containerFactory = "${kafka-starter.containerFactory}")
public class CarListener {

    private final CarService carService;

    @KafkaHandler
    public void carHandler(KafkaMessage kafkaMessage) {
        carService.deleteCitizenCars(kafkaMessage.getCitizenId());
    }
}
