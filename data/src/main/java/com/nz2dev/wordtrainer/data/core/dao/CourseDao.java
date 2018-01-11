package com.nz2dev.wordtrainer.data.core.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.nz2dev.wordtrainer.data.core.entity.CourseEntity;
import com.nz2dev.wordtrainer.data.core.relation.CourseBaseSet;

import java.util.Collection;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by nz2Dev on 30.12.2017
 */
@Dao
public interface CourseDao {

    @Insert(onConflict = REPLACE)
    long add(CourseEntity course);

    @Query("SELECT courses.id, courses.originalLanguage FROM courses ")
    List<CourseBaseSet> getCoursesBase();

}
