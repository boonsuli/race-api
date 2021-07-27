package com.trailerplan.repository;

import org.springframework.data.repository.reactive.RxJava3CrudRepository;

import com.trailerplan.model.entity.RaceEntity;

public interface RaceRxJavaRepository extends RxJava3CrudRepository<RaceEntity, Long> {
}
