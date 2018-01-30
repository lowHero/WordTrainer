package com.nz2dev.wordtrainer.domain.data.repositories;

import com.nz2dev.wordtrainer.domain.models.Scheduling;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 30.12.2017
 */
public interface SchedulingRepository {

    Single<Long> addScheduling(Scheduling scheduling);
    Single<Boolean> updateScheduling(Scheduling scheduling);
    Single<Scheduling> getSchedulingByCourseId(long courseId);

}
