package com.nz2dev.wordtrainer.data.core.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.nz2dev.wordtrainer.data.core.entity.TestEntity;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by nz2Dev on 17.12.2017
 */
@Dao
public interface TestDao {

    @Query("SELECT * FROM TestEntity")
    Flowable<List<TestEntity>> getAll();

    @Insert
    void add(TestEntity testEntity);

}
