package com.trailerplan.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.trailerplan.model.entity.RaceEntity;

public interface RaceWebFluxRepository extends ReactiveCrudRepository<RaceEntity, Long> {
}
