package com.nikitalipatov.common.feign;

import com.nikitalipatov.common.dto.request.CarDtoRequest;
import com.nikitalipatov.common.dto.response.CarDtoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "Car", url = "http://cars:8082/api/car")
public interface CarClient {

    @DeleteMapping(value = "/delete/citizen/{citizenId}")
    void deleteCitizenCars(@PathVariable int citizenId);

    @PostMapping(value = "/create")
    CarDtoResponse createCars(@RequestBody CarDtoRequest carDtoRequest);

    @GetMapping(value = "/list/{personId}")
    List<CarDtoResponse> getCitizenCar(@PathVariable int personId);

    @GetMapping(value = "/list")
    List<CarDtoResponse> getAll();

    @PutMapping(value = "/edit/{id}")
    CarDtoResponse edit(@PathVariable int id, @RequestBody CarDtoRequest carDtoRequest);
}
