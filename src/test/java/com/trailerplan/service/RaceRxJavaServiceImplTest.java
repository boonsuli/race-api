package com.trailerplan.service;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.observers.TestObserver;
import io.reactivex.rxjava3.subscribers.TestSubscriber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.trailerplan.model.entity.RaceEntity;
import com.trailerplan.repository.RaceRxJavaRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RaceRxJavaServiceImplTest {

    @InjectMocks
    private RaceRxJavaServiceImpl service;

    @Mock
    private RaceRxJavaRepository repository;

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
        when(repository.save(any())).thenReturn(Single.just(raceExpected));

        Single<RaceEntity> raceCreated = service.create(buildRaceMock());

        raceCreated.subscribe( r -> {
            Assertions.assertEquals(raceExpected.getId(), r.getId());
            Assertions.assertEquals(raceExpected.getRace_name(), r.getRace_name());
            Assertions.assertEquals(raceExpected.getChallenge_name(), r.getChallenge_name());
            Assertions.assertEquals(raceExpected.getCountry(), r.getCountry());
            Assertions.assertEquals(raceExpected.getDistance(), r.getDistance());
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

        when(repository.findById(anyLong())).thenReturn(Maybe.just(raceFinded));
        when(repository.save(any())).thenReturn(Single.just(raceExpected));

        Maybe<RaceEntity> raceUpdated = service.update(raceExpected);
        raceUpdated.subscribe( r -> {
            Assertions.assertEquals(r.getRace_name(), raceExpected.getRace_name());
        });

        verify(repository).findById(anyLong());
        verify(repository).save(any());
    }

    @Test
    public void shouldDelete() {
        RaceEntity raceFinded = buildRaceMock();
        raceFinded.setId(1l);

        when(repository.findById(anyLong())).thenReturn(Maybe.just(raceFinded));
        when(repository.deleteById(anyLong())).thenReturn(Completable.complete());

        TestObserver<Void> observer = new TestObserver<Void>();
        service.delete(1l)
                .subscribe(observer);

        observer.assertComplete();
        verify(repository).findById(anyLong());
        verify(repository).deleteById(anyLong());
    }

    @Test
    public void shouldFindRaceById() {
        when(repository.findById(anyLong())).thenReturn(Maybe.just(buildRaceMock()));

        Maybe<RaceEntity> race = service.findById(1l);
        race.subscribe(r-> {
            Assertions.assertNotNull(r);
            Assertions.assertEquals(1l, r.getId());
        });

        verify(repository).findById(anyLong());
    }

    @Test
    public void shouldEmitNothingWhenRaceNoteExist() {
        when(repository.findById(anyLong())).thenReturn(Maybe.empty());

        Maybe<RaceEntity> raceFinded = service.findById(999l);
        raceFinded.isEmpty().toMaybe().subscribe( b-> {
            Assertions.assertTrue(b);
        });

        verify(repository).findById(anyLong());
    }

    @Test
    public void shouldFindAll() {
        RaceEntity raceFinded1 = buildRaceMock();
        raceFinded1.setId(1l); raceFinded1.setRace_name(raceFinded1.getRace_name()+"1");
        RaceEntity raceFinded2 = buildRaceMock();
        raceFinded2.setId(2l); raceFinded2.setRace_name(raceFinded2.getRace_name()+"2");

        Flowable<RaceEntity> flowableRaceExpected = Flowable.just(raceFinded1, raceFinded2);
        when(repository.findAll()).thenReturn(flowableRaceExpected);

        Flowable<RaceEntity> flowableRaceActual = service.findAll();
        TestSubscriber<RaceEntity> subscriber = new TestSubscriber<>();
        flowableRaceActual.subscribe(subscriber);

        subscriber.assertComplete();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(2);
        verify(repository).findAll();
    }

}
