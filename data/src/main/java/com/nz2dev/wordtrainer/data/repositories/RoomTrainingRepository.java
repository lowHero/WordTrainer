package com.nz2dev.wordtrainer.data.repositories;

import com.nz2dev.wordtrainer.data.core.WordTrainerDatabase;
import com.nz2dev.wordtrainer.data.core.dao.TrainingDao;
import com.nz2dev.wordtrainer.data.core.entity.TrainingEntity;
import com.nz2dev.wordtrainer.data.core.relation.TrainingAndWordJoin;
import com.nz2dev.wordtrainer.data.mapping.Mapper;
import com.nz2dev.wordtrainer.domain.models.Training;
import com.nz2dev.wordtrainer.domain.repositories.TrainingsRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 16.12.2017
 */
@Singleton
public class RoomTrainingRepository implements TrainingsRepository {

    private TrainingDao trainingDao;
    private Mapper mapper;

    @Inject
    public RoomTrainingRepository(WordTrainerDatabase database, Mapper mapper) {
        this.trainingDao = database.getTrainingDao();
        this.mapper = mapper;
    }

    @Override
    public Single<Boolean> addTraining(Training primitive) {
        return Single.create(emitter -> {
            long resultId = trainingDao.insertTraining(mapper.map(primitive, TrainingEntity.class));

            if (resultId != -1L) {
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
    public Single<Training> getTraining(long id) {
        return Single.create(emitter -> {
            TrainingAndWordJoin trainingEntity = trainingDao.getTrainingById(id);
            Training training = mapper.map(trainingEntity, Training.class);
            emitter.onSuccess(training);
        });
    }

    @Override
    public Single<Training> getTrainingByWordId(long wordId) {
        return Single.create(emitter -> {
            TrainingAndWordJoin trainingEntity = trainingDao.getTrainingByWordId(wordId);
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
            Collection<Training> sortedTrainings = mapper.mapList(sortedTrainingEntityList, new ArrayList<>(), Training.class);
            emitter.onSuccess(sortedTrainings);
        });
    }
}
