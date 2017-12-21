package com.nz2dev.wordtrainer.data.core.dao;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;

import com.nz2dev.wordtrainer.data.core.WordTrainerDatabase;
import com.nz2dev.wordtrainer.data.core.dao.TestDao;
import com.nz2dev.wordtrainer.data.core.entity.TestEntity;
import com.nz2dev.wordtrainer.data.utils.ImmediateSchedulersRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subscribers.TestSubscriber;

/**
 * Created by nz2Dev on 17.12.2017
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class TestDaoTest {

    private TestDao testDao;
    private WordTrainerDatabase database;

    @Rule public ImmediateSchedulersRule immediateSchedulersRule = new ImmediateSchedulersRule();

    @Before
    public void createDB() {
        database = Room.inMemoryDatabaseBuilder(RuntimeEnvironment.application, WordTrainerDatabase.class)
                .allowMainThreadQueries()
                .build();

        testDao = database.testDao();
    }

    @After
    public void closeDB() {
        if (database != null) {
            database.close();
        }
    }

    @Test
    public void getAll_FlowableList_ShouldRequeryIfTableRowChanged() {
        TestSubscriber<List<TestEntity>> testSubscriber = new TestSubscriber<>();

        testDao.getAll().subscribe(testSubscriber);
        testDao.add(new TestEntity(0));

        testSubscriber.assertValue(testEntities -> testEntities.size() > 1);
    }

}
