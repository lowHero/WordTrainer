package com.nz2dev.wordtrainer.data.core.dao;

import android.arch.persistence.room.Room;

import com.nz2dev.wordtrainer.data.core.WordTrainerDatabase;
import com.nz2dev.wordtrainer.data.core.entity.TrainingEntity;
import com.nz2dev.wordtrainer.data.core.entity.WordEntity;
import com.nz2dev.wordtrainer.data.core.relation.TrainingAndWordJoin;
import com.nz2dev.wordtrainer.data.mapping.Mapper;
import com.nz2dev.wordtrainer.domain.models.Training;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by nz2Dev on 19.12.2017
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class TrainingDaoTest {

    private WordDao wordDao;
    private TrainingDao trainingDao;
    private WordTrainerDatabase database;
    private Mapper mapper = new Mapper();

    @Before
    public void createDB() {
        database = Room.inMemoryDatabaseBuilder(RuntimeEnvironment.application, WordTrainerDatabase.class)
                .allowMainThreadQueries()
                .build();

        wordDao = database.getWordDao();
        trainingDao = database.getTrainingDao();
    }

    @After
    public void closeDB() {
        if (database != null) {
            database.close();
        }
    }

    @Test
    public void addTraining_ByWordAddedId() {
        long wordId = wordDao.addWord(new WordEntity(1, "A", "B"));
        long trainingId = trainingDao.insertTraining(new TrainingEntity(wordId, null, 1));

        assertThat(wordId).isEqualTo(1);
        assertThat(trainingId).isEqualTo(1);

        TrainingAndWordJoin training = trainingDao.getTrainingById(trainingId);
        assertThat(training).isNotNull();
    }

    @Test
    public void updateTraining_WithDifferentEntity_ShouldSaveChanges() {
        int wordId = (int) wordDao.addWord(new WordEntity(1, "A", "B"));
        int trainingId = (int) trainingDao.insertTraining(new TrainingEntity(wordId, new Date(), 0));

        trainingDao.updateTraining(new TrainingEntity(trainingId, wordId, new Date(), 50));
        List<TrainingAndWordJoin> sortedTraining = trainingDao.getSortedTraining(1);
        assertThat(sortedTraining).isNotEmpty();

        TrainingAndWordJoin trainingAndWordJoin = sortedTraining.get(0);
        Training training = mapper.map(trainingAndWordJoin, Training.class);

        assertThat(trainingAndWordJoin.tId).isEqualTo(trainingId);
        assertThat(trainingAndWordJoin.word.getId()).isEqualTo(wordId);
        assertThat(trainingAndWordJoin.progress).isEqualTo(50);

        assertThat(training.getId()).isEqualTo(trainingAndWordJoin.tId);
        assertThat(training.getProgress()).isEqualTo(trainingAndWordJoin.progress);
    }

}
