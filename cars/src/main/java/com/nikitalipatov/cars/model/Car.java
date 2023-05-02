package com.nikitalipatov.cars.model;

import com.nikitalipatov.common.enums.ModelStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "car")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Builder.Default
    private String gosNumber = "LOTTERY";
    @Builder.Default
    private String model = "BMW";
    @Builder.Default
    private String name = "M5";
    @Builder.Default
    private String color = "Red";
    private int ownerId;
    @Builder.Default
    private String status = ModelStatus.ACTIVE.name();

}
