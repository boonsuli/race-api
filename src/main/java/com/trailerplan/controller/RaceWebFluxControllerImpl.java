package com.trailerplan.controller;

import java.net.URI;

import io.swagger.annotations.Api;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.trailerplan.model.dto.RaceDTO;
import com.trailerplan.service.RaceWebFluxService;

@Api(value = "race-webflux")
@RestController
@RequestMapping(value = "/api/race-webflux")
@Data
@Slf4j
public class RaceWebFluxControllerImpl implements RaceWebFluxController {

    private RaceWebFluxService service;

    @Autowired
    public RaceWebFluxControllerImpl(final RaceWebFluxService raceService) {
        this.service = raceService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE,
        headers = {"Content-type=application/json", "Accept=application/json"})
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<RaceDTO>> create(@RequestBody final RaceDTO dto2create) {
        return service.create(dto2create.extractEntity())
                    .map( r -> ResponseEntity.created(URI.create("/api/race-webflux")).body(r.extractDTO()));
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE,
        headers = {"Content-type=application/json", "Accept=application/json"})
    @ResponseBody
    public Mono<ResponseEntity<RaceDTO>> update(@RequestBody final RaceDTO dto2update) {
        return service.update(dto2update.extractEntity())
                    .map( r -> ResponseEntity.ok(r.extractDTO()));
    }

    @DeleteMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id") final Long id2delete) {
        return service.delete(id2delete)
                    .map( r -> ResponseEntity.noContent().build());
    }

    @GetMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    public Mono<ResponseEntity<RaceDTO>> findById(@PathVariable("id") final Long id2find) {
        return service.findById(id2find)
                    .map( r ->  ResponseEntity.ok(r.extractDTO()) )
                    .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    public Flux<RaceDTO> findAll() {
        return service.findAll()
                .map( r -> r.extractDTO());
    }
}
