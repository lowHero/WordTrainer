package com.nz2dev.wordtrainer.domain.repositories;

import com.nz2dev.wordtrainer.domain.models.Training;
import com.nz2dev.wordtrainer.domain.repositories.infrastructure.ObservableRepository;

import java.util.Collection;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 16.12.2017
 */
public interface TrainingsRepository extends ObservableRepository<Training> {

    Single<Boolean> addTraining(int wordId);
    Single<Boolean> updateTraining(Training training);
    Single<Training> getTraining(int id);
    Single<Training> getFirstSortedTraining(int accountId);
    Single<Collection<Training>> getSortedTrainings(int accountId);

}
