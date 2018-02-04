package com.nz2dev.wordtrainer.data.source.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.nz2dev.wordtrainer.data.source.local.entity.CourseEntity;
import com.nz2dev.wordtrainer.data.source.local.relation.CourseBaseSet;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by nz2Dev on 30.12.2017
 */
@Dao
public interface CourseDao {

    @Insert(onConflict = REPLACE)
    long add(CourseEntity course);

    @Query("SELECT id, originalLanguage, translationLanguage FROM courses")
    List<CourseBaseSet> getCoursesBase();

    @Query("SELECT id, originalLanguage, translationLanguage FROM courses WHERE id = :courseId")
    CourseBaseSet getCourseBase(long courseId);

    @Query("SELECT * FROM courses WHERE id = :courseId")
    CourseEntity getCourse(long courseId);

    @Query("SELECT id, originalLanguage, translationLanguage FROM courses WHERE originalLanguage = :originalLanguage")
    CourseBaseSet getCourseBaseByOriginalLanguageKey(String originalLanguage);

    @Delete
    void deleteCourse(CourseEntity course);

}
