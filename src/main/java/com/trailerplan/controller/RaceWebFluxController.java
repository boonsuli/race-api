package com.trailerplan.controller;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.trailerplan.model.dto.RaceDTO;

public interface RaceWebFluxController {

    Mono<ResponseEntity<RaceDTO>>   create(final RaceDTO dto2create);
    Mono<ResponseEntity<RaceDTO>>   update(final RaceDTO dto2update);
    Mono<ResponseEntity<Void>>      delete(final Long id2delete);
    Mono<ResponseEntity<RaceDTO>>   findById(final Long id2find);
    Flux<RaceDTO>                   findAll();

}
