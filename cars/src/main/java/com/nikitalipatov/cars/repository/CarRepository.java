package com.nikitalipatov.cars.repository;

import com.nikitalipatov.cars.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {
    List<Car> findAllByOwnerId(int personId);
//    @Query(value = "SELECT c.id, c.ownerId, c.status, c.color, c.gosNumber, c.model, c.name FROM Car c WHERE c.status = 'ACTIVE'")
//    List<Car> findAllActive();
    List<Car> findAllByStatus(String status);
}
