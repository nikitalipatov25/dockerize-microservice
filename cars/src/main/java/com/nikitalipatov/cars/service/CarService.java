package com.nikitalipatov.cars.service;

import com.nikitalipatov.common.dto.request.CarDtoRequest;
import com.nikitalipatov.common.dto.response.CarDtoResponse;

import java.util.List;

public interface CarService {

    List<CarDtoResponse> getAllCitizenCars();

    CarDtoResponse createCar(CarDtoRequest carDtoRequest);

    CarDtoResponse editCar(int carId, CarDtoRequest carDtoRequest);

    List<CarDtoResponse> getCitizenCar(int personId);

    void deleteCitizenCars(int personId);
}