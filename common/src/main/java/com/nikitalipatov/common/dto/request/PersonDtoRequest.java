package com.nikitalipatov.common.dto.request;

import com.nikitalipatov.common.enums.ModelStatus;
import com.nikitalipatov.common.enums.WorkPlace;
import lombok.*;

import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDtoRequest {

    @Builder.Default
    private String name = "Citizen Clone" + ThreadLocalRandom.current().nextInt(1, 9999);
    @Builder.Default
    private int age = ThreadLocalRandom.current().nextInt(18, 99);
    @Builder.Default
    private String passportSerial = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 9999));
    @Builder.Default
    private String passportNumber = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 9999));
    @Builder.Default
    private String sex = "Clone";
    @Builder.Default
    private String status = ModelStatus.ACTIVE.name();
    @Builder.Default
    private String work = WorkPlace.FACTORY.name();
}