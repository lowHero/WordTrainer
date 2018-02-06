package com.nz2dev.wordtrainer.data.repositories;

import com.nz2dev.wordtrainer.data.source.local.WordTrainerDatabase;
import com.nz2dev.wordtrainer.data.source.local.dao.TrainingDao;
import com.nz2dev.wordtrainer.data.source.local.entity.TrainingEntity;
import com.nz2dev.wordtrainer.data.source.local.mapping.DatabaseMapper;
import com.nz2dev.wordtrainer.data.source.local.relation.TrainingAndWordJoin;
import com.nz2dev.wordtrainer.domain.models.Training;
import com.nz2dev.wordtrainer.domain.data.repositories.TrainingsRepository;

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
    private DatabaseMapper databaseMapper;

    @Inject
    public RoomTrainingRepository(WordTrainerDatabase database, DatabaseMapper databaseMapper) {
        this.trainingDao = database.getTrainingDao();
        this.databaseMapper = databaseMapper;
    }

    @Override
    public Single<Boolean> addTraining(Training primitive) {
        return Single.create(emitter -> {
            long resultId = trainingDao.insertTraining(databaseMapper.map(primitive, TrainingEntity.class));

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
            trainingDao.updateTraining(databaseMapper.map(training, TrainingEntity.class));
            emitter.onSuccess(true);
        });
    }

    @Override
    public Single<Training> getTraining(long id) {
        return Single.create(emitter -> {
            TrainingAndWordJoin trainingEntity = trainingDao.getTrainingById(id);
            Training training = databaseMapper.map(trainingEntity, Training.class);
            emitter.onSuccess(training);
        });
    }

    @Override
    public Single<Training> getTrainingByWordId(long wordId) {
        return Single.create(emitter -> {
            TrainingAndWordJoin trainingEntity = trainingDao.getTrainingByWordId(wordId);
            Training training = databaseMapper.map(trainingEntity, Training.class);
            emitter.onSuccess(training);
        });
    }

    @Override
    public Single<Training> getFirstSortedTraining(long courseId) {
        return Single.create(emitter -> {
            TrainingAndWordJoin trainingEntity = trainingDao.getFirstSortedTraining(courseId);
            Training training = databaseMapper.map(trainingEntity, Training.class);
            emitter.onSuccess(training);
        });
    }

    @Override
    public Single<Collection<Training>> getSortedTrainings(long courseId) {
        return Single.create(emitter -> {
            List<TrainingAndWordJoin> sortedTrainingEntityList = trainingDao.getSortedTraining(courseId);
            Collection<Training> sortedTrainings = databaseMapper.mapList(sortedTrainingEntityList, new ArrayList<>(), Training.class);
            emitter.onSuccess(sortedTrainings);
        });
    }
}
