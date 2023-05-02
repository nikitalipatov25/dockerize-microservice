package com.nikitalipatov.citizens.listener;

import com.nikitalipatov.citizens.service.CitizenService;
import com.nikitalipatov.common.dto.kafka.KafkaMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "result", groupId = "city", containerFactory = "kafkaListenerContainerFactory")
@RequiredArgsConstructor
public class CitizenListener {

    private final CitizenService citizenService;

    @KafkaHandler
    public void CitizenStatusHandler(KafkaMessage kafkaMessage) {
        switch (kafkaMessage.getStatus()) {
            case FAIL -> citizenService.rollbackCitizenDeletion(kafkaMessage.getCitizenId());
            case SUCCESS -> citizenService.confirmCitizenDeletion(kafkaMessage.getCitizenId());
        }
    }
}
