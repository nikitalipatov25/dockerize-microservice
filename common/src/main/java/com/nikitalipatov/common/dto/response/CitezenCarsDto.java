package com.nikitalipatov.common.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CitezenCarsDto {

    private String gosNumber;
    private String model;
    private String name;
    private String color;
    private String status;
}
