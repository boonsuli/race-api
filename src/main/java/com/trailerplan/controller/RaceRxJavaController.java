package com.trailerplan.controller;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.trailerplan.model.dto.RaceDTO;

public interface RaceRxJavaController {

    Single<ResponseEntity<RaceDTO>>     create(final RaceDTO dto2create);
    Maybe<ResponseEntity<RaceDTO>>      update(final RaceDTO dto2update);
    Single<ResponseEntity<HttpStatus>>  delete(final Long id2delete);
    Maybe<ResponseEntity<RaceDTO>>      findById(final Long id2find);
    Flowable<RaceDTO>   findAll();

}
