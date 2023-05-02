package com.nikitalipatov.cars.service.impl;

import com.nikitalipatov.cars.mapper.CarMapper;
import com.nikitalipatov.cars.model.Car;
import com.nikitalipatov.cars.repository.CarRepository;
import com.nikitalipatov.cars.service.CarService;
import com.nikitalipatov.common.annotation.ChooseWinner;
import com.nikitalipatov.common.dto.kafka.KafkaMessage;
import com.nikitalipatov.common.dto.request.CarDtoRequest;
import com.nikitalipatov.common.dto.response.CitizenDtoResponse;
import com.nikitalipatov.common.dto.response.CarDtoResponse;
import com.nikitalipatov.common.enums.EventType;
import com.nikitalipatov.common.enums.LotteryStatus;
import com.nikitalipatov.common.enums.ModelStatus;
import com.nikitalipatov.common.enums.Status;
import com.nikitalipatov.common.error.LotteryException;
import com.nikitalipatov.common.error.ResourceNotFoundException;
import com.nikitalipatov.common.feign.CitizenClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private static final AtomicBoolean isLottery = new AtomicBoolean(false);

    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;
    private final CitizenClient citizenClient;

    @Lazy @Autowired
    private CarServiceImpl carService;

    @SneakyThrows
    @Scheduled(fixedDelay = 60000)
    public void startLottery() {
        isLottery.set(true);
        CitizenDtoResponse randomCitizen = carService.chooseWinner();
        Car lotteryCar = Car.builder().ownerId(randomCitizen.getId()).build();
        try {
            carRepository.save(lotteryCar);
            isLottery.set(false);
        } catch (Exception e) {
            deleteLotteryCar(lotteryCar.getId());
            throw new LotteryException(LotteryStatus.CANT_RUN_LOTTERY.name());
        } finally {
            isLottery.set(false);
        }
    }

    @ChooseWinner
    public CitizenDtoResponse chooseWinner() {
        try {
            List<CitizenDtoResponse> citizenList = citizenClient.getActiveCitizens();
            return citizenList.get(new Random().nextInt(citizenList.size()));
        } catch (Exception e) {
            isLottery.set(false);
            throw new LotteryException(LotteryStatus.CANT_CHOOSE_WINNER.name());
        }
    }

    @Override
    public List<CarDtoResponse> getAllCitizenCars() {
        return carRepository.findAllActive().stream().map(carMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    @Transactional
    public CarDtoResponse createCar(CarDtoRequest carDtoRequest) {
        if (isLottery.get()) {
            throw new LotteryException(LotteryStatus.LOTTERY_IS_GOING.name());
        }
        Car car = carRepository.save(carMapper.toEntity(carDtoRequest));
        return carMapper.toDto(car);
    }

    public List<CarDtoResponse> getCitizenCar(int personId) {
        return carRepository.findAllByOwnerId(personId).stream().map(carMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public void deleteCitizenCars(int personId) {
        try {
            var personCars = carRepository.findAllByOwnerId(personId);
            personCars.forEach(car -> car.setStatus(ModelStatus.DELETED.name()));
            carRepository.saveAll(personCars);
            var message = new KafkaMessage(
                    UUID.randomUUID(),
                    Status.SUCCESS,
                    EventType.CAR_DELETED,
                    personId
            );
            kafkaTemplate.send("result", message);
        } catch (Exception e) {
            var message = new KafkaMessage(
                    UUID.randomUUID(),
                    Status.FAIL,
                    EventType.CAR_DELETED,
                    personId
            );
            kafkaTemplate.send("result", message);
        }
    }

    @Override
    public CarDtoResponse editCar(int carId, CarDtoRequest carDtoRequest) {
        Car car = getCar(carId);
        return carMapper.toDto(carRepository.save(carMapper.updateModel(carDtoRequest, car)));
    }

    private Car getCar(int carId) {
        return carRepository.findById(carId).orElseThrow(
                () -> new ResourceNotFoundException("No such car with id " + carId)
        );
    }

    private void deleteLotteryCar(int carId) {
        var car = getCar(carId);
        carRepository.delete(car);
    }
}
