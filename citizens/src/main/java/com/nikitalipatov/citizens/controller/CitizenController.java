package com.nikitalipatov.citizens.controller;

import com.nikitalipatov.citizens.service.CitizenService;
import com.nikitalipatov.common.dto.request.PersonDtoRequest;
import com.nikitalipatov.common.dto.response.CitizenDtoResponse;
import com.nikitalipatov.common.feign.CitizenClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/citizen")
public class CitizenController implements CitizenClient {

    private final CitizenService citizenService;

    @Override
    public void rollbackCitizenCreation(int personId) {
        citizenService.confirmCitizenDeletion(personId);
    }

    @Override
    public List<CitizenDtoResponse> getActiveCitizens() {
        return citizenService.getAllActiveCitizens();
    }

    @Override
    public CitizenDtoResponse create(PersonDtoRequest personDtoRequest) {
        return citizenService.createCitizen(personDtoRequest);
    }

    @Override
    public CitizenDtoResponse edit(int id, PersonDtoRequest personDtoRequest) {
        return citizenService.Citizen(id, personDtoRequest);
    }

    @Override
    public void delete(int id) {
        citizenService.deleteCitizen(id);
    }

}
