package com.nz2dev.wordtrainer.data.repositories;

import com.nz2dev.wordtrainer.domain.models.Scheduling;
import com.nz2dev.wordtrainer.domain.repositories.SchedulingRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 30.12.2017
 */
@Singleton
public class RoomSchedulingRepository implements SchedulingRepository {

    @Inject
    public RoomSchedulingRepository() {
    }

    @Override
    public Single<Long> addScheduling(Scheduling scheduling) {
        return Single.just(1L);
    }

}
