package com.nikitalipatov.citizens.repository;

import com.nikitalipatov.citizens.model.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CitizenRepository extends JpaRepository<Citizen, Integer> {

//    @Query(value = "SELECT c.name, c.age, c.sex, c.id, c.status, c.passportNumber, c.passportSerial, c.work FROM Citizen c WHERE c.status = 'ACTIVE'")
    List<Citizen> findAllByStatus(String status);

    @Query("SELECT count(*) FROM Citizen c WHERE c.status = 'ACTIVE'")
    int countActiveCitizens();

}
