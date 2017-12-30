package com.nz2dev.wordtrainer.data.core.dao;

import android.arch.persistence.room.Room;

import com.nz2dev.wordtrainer.data.core.WordTrainerDatabase;
import com.nz2dev.wordtrainer.data.core.dao.AccountDao;
import com.nz2dev.wordtrainer.data.core.dao.AccountHistoryDao;
import com.nz2dev.wordtrainer.data.core.dao.WordDao;
import com.nz2dev.wordtrainer.data.core.entity.WordEntity;

import org.assertj.core.api.Condition;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;

/**
 * Created by nz2Dev on 12.12.2017
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class WordDaoTest {

    private WordDao wordDao;
    private WordTrainerDatabase database;

    @Before
    public void createDB() {
        database = Room.inMemoryDatabaseBuilder(RuntimeEnvironment.application, WordTrainerDatabase.class)
                .allowMainThreadQueries()
                .build();

        wordDao = database.getWordDao();
    }

    @After
    public void closeDB() {
        if (database != null) {
            database.close();
        }
    }

    @Test
    public void addWord_WithAccountId_ShouldReturnByThatId() {
        wordDao.addWord(new WordEntity(1, "na", "la"));

        List<WordEntity> allWords = wordDao.getAllWords(1);

        assertThat(allWords).isNotNull();
        assertThat(allWords).hasSize(1);
        assertThat(allWords).has(new Condition<WordEntity>() {
            @Override
            public boolean matches(WordEntity value) {
                return value.getCourseId() == 1 && value.getOriginal().equals("na");
            }
        }, atIndex(0));
    }

    @Test
    public void getPartOfWord_withMiddleId_shouldReturnInLimitRange() {
        for (int i = 0; i < 10; i++) {
            wordDao.addWord(new WordEntity(1, "na", "sd"));
        }

        List<WordEntity> words = wordDao.getPartOfWords(1, 1, 50);
        assertThat(words).hasSize(10);
        assertThat(words).areExactly(1, new Condition<WordEntity>() {
            @Override
            public boolean matches(WordEntity value) {
                return value.getId() == 1;
            }
        });
    }
}
