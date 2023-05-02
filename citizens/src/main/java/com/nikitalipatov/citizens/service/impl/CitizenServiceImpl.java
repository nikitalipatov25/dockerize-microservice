package com.nikitalipatov.citizens.service.impl;

import com.nikitalipatov.citizens.mapper.CitizenMapper;
import com.nikitalipatov.citizens.model.Citizen;
import com.nikitalipatov.citizens.repository.CitizenRepository;
import com.nikitalipatov.citizens.service.CitizenService;
import com.nikitalipatov.common.dto.kafka.KafkaMessage;
import com.nikitalipatov.common.dto.request.PersonDtoRequest;
import com.nikitalipatov.common.dto.response.CitizenDtoResponse;
import com.nikitalipatov.common.enums.EventType;
import com.nikitalipatov.common.enums.ModelStatus;
import com.nikitalipatov.common.enums.Status;
import com.nikitalipatov.common.error.ResourceNotFoundException;
//import com.nikitalipatov.kafkastarter.service.KafkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@EnableScheduling
@EnableAsync
public class CitizenServiceImpl implements CitizenService {

    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;
//    private final KafkaService kafkaService;
    private final CitizenRepository citizenRepository;
    private final CitizenMapper citizenMapper;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Scheduled(fixedDelay = 60000)
    public void cloneFactory() {
        executorService.execute(() -> {
            for (int i = 0; i < 5; i++) {
                createCitizen(PersonDtoRequest.builder().build());
            }
        });
    }

    @Override
    public void rollbackCitizenDeletion(int citizenId) {
        Citizen citizen = getCitizen(citizenId);
        if (citizen.getStatus().equals(ModelStatus.PREPARING_TO_DELETE.name())) {
            citizen.setStatus(ModelStatus.ACTIVE.name());
            citizenRepository.save(citizen);
        }
    }

    @Override
    public void confirmCitizenDeletion(int personId) {
        Citizen citizen = getCitizen(personId);
        if (citizen.getStatus().equals(ModelStatus.PREPARING_TO_DELETE.name())) {
            citizen.setStatus(ModelStatus.DELETED.name());
            citizenRepository.save(citizen);
        }
    }

    @Override
    public List<CitizenDtoResponse> getAllActiveCitizens() {
        List<Citizen> activeCitizensList = citizenRepository.findAllByStatus("ACTIVE");
        return activeCitizensList.stream().map(citizenMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CitizenDtoResponse createCitizen(PersonDtoRequest personDtoRequest) {
        Citizen citizen = citizenRepository.save(citizenMapper.toEntity(personDtoRequest));
        return citizenMapper.toDto(citizen);
    }

    @Override
    public void deleteCitizen(int personId) {
        Citizen citizen = getCitizen(personId);
        citizen.setStatus(ModelStatus.PREPARING_TO_DELETE.name());
        citizenRepository.save(citizen);
        var message = new KafkaMessage(
                UUID.randomUUID(),
                Status.SUCCESS,
                EventType.CITIZEN_DELETED,
                personId
        );
        //System.out.println(kafkaProperties);
        System.out.println("a");
//        kafkaService.send(message);
        kafkaTemplate.send("command", message);
    }

    @Override
    public CitizenDtoResponse Citizen(int personId, PersonDtoRequest personDtoRequest) {
        Citizen citizen = getCitizen(personId);
        return citizenMapper.toDto(citizenRepository.save(citizenMapper.updateModel(personDtoRequest, citizen)));
    }

    private Citizen getCitizen(int citizenId) {
        return citizenRepository.findById(citizenId).orElseThrow(
                () -> new ResourceNotFoundException("No such citizen with id " + citizenId)
        );
    }
}
