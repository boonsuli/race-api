package com.trailerplan.service;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

import com.trailerplan.model.entity.RaceEntity;

public interface RaceRxJavaService {

    Single<RaceEntity>      create(final RaceEntity entity2create);
    Maybe<RaceEntity>       update(final RaceEntity entity2update);
    Completable             delete(final Long id2delete);
    Maybe<RaceEntity>       findById(final Long id2find);
    Flowable<RaceEntity>    findAll();

}
