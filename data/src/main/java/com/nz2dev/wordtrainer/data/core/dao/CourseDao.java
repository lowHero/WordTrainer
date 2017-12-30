package com.nz2dev.wordtrainer.data.core.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.nz2dev.wordtrainer.data.core.entity.CourseEntity;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by nz2Dev on 30.12.2017
 */
@Dao
public interface CourseDao {

    @Insert(onConflict = REPLACE)
    long add(CourseEntity course);

}
