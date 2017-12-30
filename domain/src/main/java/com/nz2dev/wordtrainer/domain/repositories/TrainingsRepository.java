package com.nz2dev.wordtrainer.domain.repositories;

import com.nz2dev.wordtrainer.domain.models.Training;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.repositories.infrastructure.ObservableRepository;

import java.util.Collection;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 16.12.2017
 */
public interface TrainingsRepository extends ObservableRepository {

    Single<Boolean> addTraining(Word word);
    Single<Boolean> updateTraining(Training training);
    Single<Training> getTraining(long id);
    Single<Training> getFirstSortedTraining(long courseId);
    Single<Collection<Training>> getSortedTrainings(long courseId);

}
