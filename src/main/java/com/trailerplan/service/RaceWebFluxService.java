package com.trailerplan.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.trailerplan.model.entity.RaceEntity;

public interface RaceWebFluxService {

    Mono<RaceEntity> create(final RaceEntity entity2create);
    Mono<RaceEntity> update(final RaceEntity entity2update);
    Mono<RaceEntity> delete(final Long id2delete);
    Mono<RaceEntity> findById(final Long id2find);
    Flux<RaceEntity> findAll();

}
