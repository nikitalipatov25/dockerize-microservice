package com.nikitalipatov.citizens.mapper;

import com.nikitalipatov.citizens.model.Citizen;
import com.nikitalipatov.common.dto.request.PersonDtoRequest;
import com.nikitalipatov.common.dto.response.CitizenDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CitizenMapper {
    CitizenDtoResponse toDto(Citizen citizen);
    Citizen toEntity(PersonDtoRequest personDtoRequest);
    Citizen updateModel(PersonDtoRequest personDtoRequest, @MappingTarget Citizen citizen);
}
