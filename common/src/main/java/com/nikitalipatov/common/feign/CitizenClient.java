package com.nikitalipatov.common.feign;

import com.nikitalipatov.common.dto.request.PersonDtoRequest;
import com.nikitalipatov.common.dto.response.CitizenDtoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "Citizen", url = "http://localhost:8081/api/citizen")
public interface CitizenClient {

    @GetMapping(value = "/rollback/{personId}")
    void rollbackCitizenCreation(@PathVariable int personId);

    @GetMapping(value = "/active")
    List<CitizenDtoResponse> getActiveCitizens();

    @PostMapping(value = "/create")
    CitizenDtoResponse create(@RequestBody PersonDtoRequest personDtoRequest);

    @PutMapping(value = "/edit/{id}")
    CitizenDtoResponse edit(@PathVariable int id, @RequestBody PersonDtoRequest personDtoRequest);

    @DeleteMapping(value = "/delete/{id}")
    void delete(@PathVariable int id);

}
