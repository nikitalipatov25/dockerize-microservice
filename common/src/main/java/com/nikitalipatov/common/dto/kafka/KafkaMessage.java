package com.nikitalipatov.common.dto.kafka;

import com.nikitalipatov.common.enums.EventType;
import com.nikitalipatov.common.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KafkaMessage {
    private UUID messageId;
    private Status status;
    private EventType eventType;
    private int citizenId;
}
