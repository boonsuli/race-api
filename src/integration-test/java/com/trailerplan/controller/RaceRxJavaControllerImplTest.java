package com.trailerplan.controller;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.trailerplan.config.AppDataConfigMemoryTest;
import com.trailerplan.model.dto.RaceDTO;
import com.trailerplan.model.entity.RaceEntity;
import com.trailerplan.service.RaceRxJavaServiceImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = {AppDataConfigMemoryTest.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class RaceRxJavaControllerImplTest {

    @InjectMocks
    private RaceRxJavaControllerImpl controller;

    @Autowired
    private RaceRxJavaServiceImpl service;

    @BeforeAll
    public void initAll() {
        controller.setService(service);
    }

    private RaceDTO buildRaceDTO() {
        RaceDTO raceDTO = new RaceDTO();
        raceDTO.setRace_name("Mozart 100");
        raceDTO.setItra_point(5);
        raceDTO.setRace_date("2021-09-04");
        raceDTO.setCity_region("Salzburg");
        raceDTO.setCountry("Austria");
        raceDTO.setDeparture_city("Salzburg");
        raceDTO.setArrival_city("Salzburg");
        raceDTO.setDistance(108f);
        raceDTO.setElevation_up(5000);
        raceDTO.setElevation_down(5000);
        raceDTO.setMaximum_time(22f);
        raceDTO.setUrl("https://www.mozart100.com/");
        return raceDTO;
    }

    @Test
    @Order(1)
    public void shouldFindAll() {
        Integer expectedNbRaces = 5;
        WebTestClient
                .bindToController(controller)
                .build()
                .get()
                .uri("/api/race-rxjava")
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectStatus().is2xxSuccessful()
                .expectBody().jsonPath("$.length()").isEqualTo(expectedNbRaces)
        ;
    }

    @Test
    @Order(2)
    public void shouldFindById() {
        Long expectedId = 1l;
        WebTestClient
                .bindToController(controller)
                .build()
                .get()
                .uri("/api/race-rxjava/"+expectedId)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectStatus().is2xxSuccessful()
                .expectBody().jsonPath("$.id").isEqualTo(expectedId)
        ;
    }

    @Test
    @Order(3)
    public void shouldCreate() {
        Long expectedId  = 6l;
        WebTestClient
                .bindToController(controller)
                .build()
                .post()
                .uri("/api/race-rxjava")
                .bodyValue(buildRaceDTO())
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectStatus().is2xxSuccessful()
                .expectBody().jsonPath("$.id").isEqualTo(expectedId)
        ;

        Completable completable = service.delete(expectedId);
        completable.blockingSubscribe();
    }

    @Test
    @Order(4)
    public void shouldUpdate() {
        Long expectedId  = 6l;
        Single<RaceEntity> singleRace = service.create(buildRaceDTO().extractEntity());
        singleRace.blockingSubscribe();

        Maybe<RaceEntity> maybeRace = service.findById(expectedId);
        maybeRace.subscribe( r -> {
            String expectedRaceName = r.getRace_name() + "_update";
            r.setRace_name(expectedRaceName);
            r.setCity_region(r.getCity_region() + "_update");
            WebTestClient
                    .bindToController(controller)
                    .build()
                    .put()
                    .uri("/api/race-rxjava")
                    .bodyValue(r)
                    .exchange()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .expectStatus().is2xxSuccessful()
                    .expectBody().jsonPath("$.race_name").isEqualTo(expectedRaceName)
            ;
        });

        Completable completable = service.delete(expectedId);
        completable.blockingSubscribe();
    }

    @Test
    @Order(5)
    public void shouldDelete() {
        Long expectedId  = 6l;
        Single<RaceEntity> singleRace = service.create(buildRaceDTO().extractEntity());
        singleRace.blockingSubscribe();

        Maybe<RaceEntity> maybeRace = service.findById(expectedId);
        maybeRace.subscribe( r -> {
            WebTestClient
                    .bindToController(controller)
                    .build()
                    .delete()
                    .uri("/api/race-rxjava/"+expectedId)
                    .exchange()
                    .expectStatus().isNoContent()
           ;
        });
    }
}
