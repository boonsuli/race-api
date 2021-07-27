package com.trailerplan.service;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.subscribers.TestSubscriber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.trailerplan.model.entity.RaceEntity;
import com.trailerplan.repository.RaceWebFluxRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RaceWebFluxServiceImplTest {

    @InjectMocks
    private RaceWebFluxServiceImpl service;

    @Mock
    private RaceWebFluxRepository repository;

    private RaceEntity buildRaceMock() {
        RaceEntity race = new RaceEntity(1l, "RaceName", "RaceChallenge", "2021-12-01", 100f,
                5000, 5000, 5, "France", "Chamonix", "Chamonix",
                "Chamonix", 26f, "http://www.trailerplan.com");
        return race;
    }

    @Test
    public void shouldCreate() {
        RaceEntity raceExpected = buildRaceMock();
        raceExpected.setId(1l);
        when(repository.save(any())).thenReturn(Mono.just(raceExpected));

        Mono<RaceEntity> monoRace = service.create(buildRaceMock());
        monoRace.subscribe( r -> {
            assertEquals(raceExpected.getId(), r.getId());
            assertEquals(raceExpected.getRace_name(), r.getRace_name());
            assertEquals(raceExpected.getChallenge_name(), r.getChallenge_name());
            assertEquals(raceExpected.getCountry(), r.getCountry());
            assertEquals(raceExpected.getDistance(), r.getDistance());
        });
        verify(repository).save(any());
    }

    @Test
    public void shouldUpdate() {
        RaceEntity raceFinded = buildRaceMock();
        raceFinded.setId(1l);

        RaceEntity raceExpected = buildRaceMock();
        raceExpected.setId(1l);
        raceExpected.setRace_name(raceExpected.getRace_name() + "Update");

        when(repository.findById(anyLong())).thenReturn(Mono.just(raceFinded));
        when(repository.save(any())).thenReturn(Mono.just(raceExpected));

        Mono<RaceEntity> raceUpdated = service.update(raceExpected);
        RaceEntity raceActual =  raceUpdated.block();

        assertEquals(raceExpected.getId(), raceActual.getId());
        assertEquals(raceExpected.getRace_name(), raceActual.getRace_name());
        assertEquals(raceExpected.getChallenge_name(), raceActual.getChallenge_name());

        verify(repository).findById(anyLong());
        verify(repository).save(any());
    }

    @Test
    public void shouldDelete() {
        RaceEntity raceFinded = buildRaceMock();
        raceFinded.setId(1l);

        when(repository.findById(anyLong())).thenReturn(Mono.just(raceFinded));
        when(repository.deleteById(anyLong())).thenReturn(Mono.empty());

        Mono<RaceEntity> raceDeleted = service.delete(1l);
        RaceEntity raceActual =  raceDeleted.block();
        assertEquals(raceFinded.getId(), raceActual.getId());

        verify(repository).findById(anyLong());
        verify(repository).deleteById(anyLong());
    }

    @Test
    public void shouldFindById() {
        when(repository.findById(anyLong())).thenReturn(Mono.just(buildRaceMock()));

        Mono<RaceEntity> raceFinded = service.findById(1l);
        RaceEntity raceActual =  raceFinded.block();
        assertNotNull(raceActual);
        assertEquals(1l, raceActual.getId());

        verify(repository).findById(anyLong());
    }

    @Test
    public void shouldEmitNothingWhenRaceNoteExist() {
        when(repository.findById(anyLong())).thenReturn(Mono.empty());

        Mono<RaceEntity> raceFinded = service.findById(999l);
        Maybe.fromPublisher(raceFinded.hasElement()).subscribe( b -> {
            Assertions.assertFalse(b);
        });

        verify(repository).findById(anyLong());
    }

    @Test
    public void shouldFindAll() {
        RaceEntity raceFinded1 = buildRaceMock();
        raceFinded1.setId(1l); raceFinded1.setRace_name(raceFinded1.getRace_name()+"1");
        RaceEntity raceFinded2 = buildRaceMock();
        raceFinded2.setId(2l); raceFinded2.setRace_name(raceFinded2.getRace_name()+"2");

        Flux<RaceEntity> flowableRaceExpected = Flux.just(raceFinded1, raceFinded2);
        when(repository.findAll()).thenReturn(flowableRaceExpected);

        Flux<RaceEntity> fluxRaceActual = service.findAll();
        TestSubscriber<RaceEntity> subscriber = new TestSubscriber<>();
        fluxRaceActual.subscribe(subscriber);

        subscriber.assertComplete();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(2);
        verify(repository).findAll();
    }

}
