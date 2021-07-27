package com.trailerplan.service;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.trailerplan.model.entity.RaceEntity;
import com.trailerplan.repository.RaceWebFluxRepository;

@Service("RaceService-WebFlux")
@RequiredArgsConstructor
@Transactional
@Data
@Slf4j
public class RaceWebFluxServiceImpl implements RaceWebFluxService {

    private final RaceWebFluxRepository repository;

    public Mono<RaceEntity> create(final RaceEntity entity2create) {
        log.debug("create race : " + entity2create.toString());
        return repository.save(entity2create);
    }

    public Mono<RaceEntity> update(final RaceEntity entity) {
        log.debug("update race : " + entity.toString());
        return repository.findById(entity.getId())
                    .map(entityFinded -> entityFinded.setPropertiesFromEntity(entity))
                    .flatMap(entityUpdatedBeforeSave -> repository.save(entityUpdatedBeforeSave))
                    .then(Mono.just(entity));
    }

    public Mono<RaceEntity> delete(final Long id2delete) {
        log.debug("delete race with id : " + id2delete);
        return repository.findById(id2delete)
                    .flatMap(entityFinded -> repository.deleteById(entityFinded.getId())
                    .then(Mono.just(entityFinded)));
    }

    public Mono<RaceEntity> findById(final Long id2find) {
        return repository.findById(id2find);
    }

    public Flux<RaceEntity> findAll() {
        return repository.findAll();
    }
}

