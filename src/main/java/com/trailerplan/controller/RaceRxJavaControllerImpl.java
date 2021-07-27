package com.trailerplan.controller;

import java.net.URI;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.swagger.annotations.Api;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.trailerplan.model.dto.RaceDTO;
import com.trailerplan.service.RaceRxJavaService;

@Api(value = "race-rxjava")
@RestController
@RequestMapping(value = "/api/race-rxjava")
@Data
@Slf4j
public class RaceRxJavaControllerImpl implements RaceRxJavaController {

    private RaceRxJavaService service;

    @Autowired
    public RaceRxJavaControllerImpl(final RaceRxJavaService raceService) {
        this.service = raceService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = {"Content-type=application/json", "Accept=application/json"} )
    @ResponseBody
    public Single<ResponseEntity<RaceDTO>> create(@RequestBody final RaceDTO dto2create) {
        return service.create(dto2create.extractEntity())
                    .map( r -> {
                        URI location = URI.create("/api/race-rxjava");
                        return ResponseEntity.created(location).body(r.extractDTO());
                    });
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = {"Content-type=application/json", "Accept=application/json"} )
    @ResponseBody
    public Maybe<ResponseEntity<RaceDTO>> update(@RequestBody final RaceDTO dto2update) {
        return service.update(dto2update.extractEntity())
                    .map( r -> ResponseEntity.ok(r.extractDTO()));
    }

    @DeleteMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Single<ResponseEntity<HttpStatus>> delete(@PathVariable("id") final Long id2delete) {
        return service.delete(id2delete)
                .subscribeOn(Schedulers.io())
                .toSingle( () -> ResponseEntity.noContent().build());
    }

    @GetMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    public Maybe<ResponseEntity<RaceDTO>> findById(@PathVariable("id") final Long id2find) {
        return service.findById(id2find)
                    .map( r -> ResponseEntity.ok(r.extractDTO()));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    public Flowable<RaceDTO> findAll() {
        return service.findAll()
                .map( r -> r.extractDTO());
    }
}
