package com.nikitalipatov.cars.controller;

import com.nikitalipatov.cars.service.CarService;
import com.nikitalipatov.common.dto.request.CarDtoRequest;
import com.nikitalipatov.common.dto.response.CarDtoResponse;
import com.nikitalipatov.common.feign.CarClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/car")
public class CarController implements CarClient {

    private final CarService carService;

    @Override
    public CarDtoResponse createCars(CarDtoRequest carDtoRequest) {
        return carService.createCar(carDtoRequest);
    }

    @Override
    public List<CarDtoResponse> getCitizenCar(int citizenId) {
        return carService.getCitizenCar(citizenId);
    }

    @Override
    public List<CarDtoResponse> getAll() {
        return carService.getAllCitizenCars();
    }

    @Override
    public void deleteCitizenCars(int citizenId) {
        carService.deleteCitizenCars(citizenId);
    }

    @Override
    public CarDtoResponse edit(int carId, CarDtoRequest carDtoRequest) {
        return carService.editCar(carId, carDtoRequest);
    }
}