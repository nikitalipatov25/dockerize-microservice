package com.nikitalipatov.common.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CitizenDtoResponse {
    private int id;
    private String name;
    private String age;
    private String sex;
    private String passportSerial;
    private String passportNumber;
    private String work;
    private List<CarDtoResponse> cars;
}
