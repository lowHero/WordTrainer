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

    private Scheduling one = Scheduling.newInstance();

    @Inject
    public RoomSchedulingRepository() {
    }

    @Override
    public Single<Long> addScheduling(Scheduling scheduling) {
        one = scheduling;
        return Single.just(1L);
    }

    @Override
    public Single<Boolean> updateScheduling(Scheduling scheduling) {
        one = scheduling;
        return Single.just(true);
    }

    @Override
    public Single<Scheduling> getSchedulingByCourseId(long courseId) {
        return Single.just(one);
    }

}
