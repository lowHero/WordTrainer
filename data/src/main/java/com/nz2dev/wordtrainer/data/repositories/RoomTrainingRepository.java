package com.nz2dev.wordtrainer.data.repositories;

import com.nz2dev.wordtrainer.data.core.dao.TrainingDao;
import com.nz2dev.wordtrainer.data.core.entity.TrainingEntity;
import com.nz2dev.wordtrainer.data.core.entity.joined.TrainingAndWordJoin;
import com.nz2dev.wordtrainer.data.mapping.Mapper;
import com.nz2dev.wordtrainer.domain.models.Training;
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
public class RoomTrainingRepository extends RxObservableRepository<Training> implements TrainingsRepository {

    private TrainingDao trainingDao;
    private Mapper mapper;

    @Inject
    public RoomTrainingRepository(TrainingDao trainingDao, Mapper mapper) {
        this.trainingDao = trainingDao;
        this.mapper = mapper;
    }

    @Override
    public Single<Boolean> addTraining(int wordId) {
        return Single.create(emitter -> {
            TrainingEntity trainingEntity = new TrainingEntity(wordId, null, 0);
            int resultId = (int) trainingDao.insertTraining(trainingEntity);

            if (resultId != -1) {
                TrainingAndWordJoin trainingAndWordEntity = trainingDao.getTrainingById(resultId);
                requestChanges(Collections.singleton(mapper.map(trainingAndWordEntity, Training.class)));
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
            emitter.onSuccess(true);
        });
    }

    @Override
    public Single<Training> getTraining(int id) {
        return Single.create(emitter -> {
            TrainingAndWordJoin trainingEntity = trainingDao.getTrainingById(id);
            Training training = mapper.map(trainingEntity, Training.class);
            emitter.onSuccess(training);
        });
    }

    @Override
    public Single<Training> getFirstSortedTraining(int accountId) {
        return Single.create(emitter -> {
            TrainingAndWordJoin trainingEntity = trainingDao.getFirstSortedTraining(accountId);
            Training training = mapper.map(trainingEntity, Training.class);
            emitter.onSuccess(training);
        });
    }

    @Override
    public Single<Collection<Training>> getSortedTrainings(int accountId) {
        return Single.create(emitter -> {
            List<TrainingAndWordJoin> sortedTrainingEntityList = trainingDao.getSortedTraining(accountId);
            List<Training> sortedTrainings = mapper.mapList(sortedTrainingEntityList, new ArrayList<>(), Training.class);
            emitter.onSuccess(sortedTrainings);
        });
    }
}
