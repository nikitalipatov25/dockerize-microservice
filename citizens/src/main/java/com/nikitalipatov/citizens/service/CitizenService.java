package com.nikitalipatov.citizens.service;

import com.nikitalipatov.common.dto.request.PersonDtoRequest;
import com.nikitalipatov.common.dto.response.CitizenDtoResponse;

import java.util.List;

public interface CitizenService {

    List<CitizenDtoResponse> getAllActiveCitizens();

    CitizenDtoResponse createCitizen(PersonDtoRequest personDtoRequest);

    void deleteCitizen(int id);

    void confirmCitizenDeletion(int personId);

    CitizenDtoResponse Citizen(int id, PersonDtoRequest personDtoRequest);

    void rollbackCitizenDeletion(int citizenId);

}
