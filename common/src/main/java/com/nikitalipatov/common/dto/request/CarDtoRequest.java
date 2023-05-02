package com.nikitalipatov.common.dto.request;

import com.nikitalipatov.common.enums.ModelStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CarDtoRequest {

    private int id;
    private int ownerId;
    @Builder.Default
    private String gosNumber = "LOTTERY";
    @Builder.Default
    private String model = "BMW";
    @Builder.Default
    private String name = "M5";
    @Builder.Default
    private String color = "Red";
    @Builder.Default
    private String status = ModelStatus.ACTIVE.name();
}
