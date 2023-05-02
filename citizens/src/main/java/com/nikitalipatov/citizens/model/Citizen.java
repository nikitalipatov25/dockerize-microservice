package com.nikitalipatov.citizens.model;

import com.nikitalipatov.common.enums.WorkPlace;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name ="citizen")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Citizen {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String name;
    private int age;
    private String sex;
    private String passportSerial;
    private String passportNumber;
    private String status;
    @Builder.Default
    private String work = WorkPlace.FACTORY.name();
}
