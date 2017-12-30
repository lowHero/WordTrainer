package com.nz2dev.wordtrainer.data.repositories;

import com.nz2dev.wordtrainer.data.core.dao.TrainingDao;
import com.nz2dev.wordtrainer.data.core.entity.TrainingEntity;
import com.nz2dev.wordtrainer.data.core.relation.TrainingAndWordJoin;
import com.nz2dev.wordtrainer.data.mapping.Mapper;
import com.nz2dev.wordtrainer.domain.models.Training;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.repositories.TrainingsRepository;
import com.nz2dev.wordtrainer.domain.repositories.infrastructure.RxObservableRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 16.12.2017
 */
@Singleton
public class RoomTrainingRepository extends RxObservableRepository implements TrainingsRepository {

    private TrainingDao trainingDao;
    private Mapper mapper;

    @Inject
    public RoomTrainingRepository(TrainingDao trainingDao, Mapper mapper) {
        this.trainingDao = trainingDao;
        this.mapper = mapper;
    }

    @Override
    public Single<Boolean> addTraining(Word word) {
        return Single.create(emitter -> {
            TrainingEntity trainingEntity = new TrainingEntity(word.getId(), null, 0);
            long resultId = trainingDao.insertTraining(trainingEntity);

            if (resultId != -1L) {
                requestChanges(State.Changed);
                emitter.onSuccess(true);
            } else {
                emitter.onSuccess(false);
            }
        });
    }

    @Override
    public Single<Boolean> updateTraining(Training training) {
        return Single.create(emitter -> {
            trainingDao.updateTraining(mapper.map(training, TrainingEntity.class));

            requestChanges(State.Updated);
            emitter.onSuccess(true);
        });
    }

    @Override
    public Single<Training> getTraining(long id) {
        return Single.create(emitter -> {
            TrainingAndWordJoin trainingEntity = trainingDao.getTrainingById(id);
            Training training = mapper.map(trainingEntity, Training.class);
            emitter.onSuccess(training);
        });
    }

    @Override
    public Single<Training> getFirstSortedTraining(long courseId) {
        return Single.create(emitter -> {
            TrainingAndWordJoin trainingEntity = trainingDao.getFirstSortedTraining(courseId);
            Training training = mapper.map(trainingEntity, Training.class);
            emitter.onSuccess(training);
        });
    }

    @Override
    public Single<Collection<Training>> getSortedTrainings(long courseId) {
        return Single.create(emitter -> {
            List<TrainingAndWordJoin> sortedTrainingEntityList = trainingDao.getSortedTraining(courseId);
            List<Training> sortedTrainings = mapper.mapList(sortedTrainingEntityList, new ArrayList<>(), Training.class);
            emitter.onSuccess(sortedTrainings);
        });
    }
}
