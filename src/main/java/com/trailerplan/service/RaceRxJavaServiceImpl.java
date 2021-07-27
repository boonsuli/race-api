package com.trailerplan.service;

import io.reactivex.rxjava3.core.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trailerplan.model.entity.RaceEntity;
import com.trailerplan.repository.RaceRxJavaRepository;

@Service("RaceService-RxJava")
@RequiredArgsConstructor
@Transactional
@Data
@Slf4j
public class RaceRxJavaServiceImpl implements RaceRxJavaService {

    private final RaceRxJavaRepository repository;

    public Single<RaceEntity> create(final RaceEntity entity2create) {
        log.debug("create race : " + entity2create.toString());
        return this.repository.save(entity2create);
    }

    public Maybe<RaceEntity> update(final RaceEntity entity2update) {
        log.debug("update race : " + entity2update.toString());
        return this.repository
                        .findById(entity2update.getId())
                        .flatMap( raceFinded -> {
                            log.debug("race finded : " + raceFinded.toString());
                            RaceEntity race2save = raceFinded.setPropertiesFromEntity(entity2update);
                            return this.repository.save(race2save).toMaybe();
                        });
    }

    public Completable delete(final Long id2delete) {
        log.debug("delete race with id : " + id2delete);
        return this.repository
                        .findById(id2delete)
                        .flatMapCompletable( raceFinded -> {
                            log.debug("race finded and to be deleted : " + raceFinded.toString());
                            return this.repository.deleteById(raceFinded.getId());
                        });
    }

    public Maybe<RaceEntity> findById(final Long id2find) {
        return this.repository
                .findById(id2find)
                .flatMap( existingRace -> {
                    log.debug("race finded : " + existingRace.toString());
                    return Maybe.create(maybeEmitter -> {
                        maybeEmitter.onSuccess(existingRace);
                        maybeEmitter.onComplete();
                    });
                });
    }

    public Flowable<RaceEntity> findAll() {
        return Flowable.create(flowableEmitter -> {
            try {
                this.repository
                        .findAll()
                        .doOnComplete( () -> flowableEmitter.onComplete() )
                        .forEach( race -> {
                            log.debug("race finded in list : " + race.toString());
                            flowableEmitter.onNext(race);
                        })
                ;
            } catch (final Exception e) {
                log.error("onError : " + e.getMessage());
                flowableEmitter.onError(e);
            }

        }, BackpressureStrategy.BUFFER );
    }
}
