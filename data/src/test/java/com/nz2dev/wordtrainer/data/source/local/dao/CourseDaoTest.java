package com.nz2dev.wordtrainer.data.source.local.dao;

import android.arch.persistence.room.Room;

import com.nz2dev.wordtrainer.data.source.local.WordTrainerDatabase;
import com.nz2dev.wordtrainer.data.source.local.entity.CourseEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by nz2Dev on 30.12.2017
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class CourseDaoTest {

    private CourseDao courseDao;
    private WordTrainerDatabase database;

    @Before
    public void createDB() {
        database = Room.inMemoryDatabaseBuilder(RuntimeEnvironment.application, WordTrainerDatabase.class)
                .allowMainThreadQueries()
                .build();
        courseDao = database.getCourseDao();
    }

    @After
    public void closeDB() {
        if (database != null) {
            database.close();
        }
    }

    @Test
    public void insert_WithZeroId_ShouldAutoGenerateNewIdWithoutExceptions() {
        long firstId = courseDao.add(CourseEntity.exceptId(1, 1, "EN", "UA"));
        long secondId = courseDao.add(CourseEntity.exceptId(1, 1, "EN", "FR"));

        assertThat(firstId).isEqualTo(1);
        assertThat(secondId).isEqualTo(2);
    }

}
