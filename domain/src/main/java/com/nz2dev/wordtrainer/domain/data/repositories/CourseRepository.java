package com.nz2dev.wordtrainer.domain.data.repositories;

import com.nz2dev.wordtrainer.domain.models.Course;
import com.nz2dev.wordtrainer.domain.models.CourseBase;

import java.util.Collection;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 30.12.2017
 */
public interface CourseRepository {

    Single<Long> addCourse(Course course);
    Single<Course> deleteCourse(long courseId);
    Single<CourseBase> getCourseBase(long courseId);
    Single<Collection<CourseBase>> getCoursesBase();
    Single<CourseBase> getCourseBaseByOriginalLanguageKey(String originalLanguageKey);

}
