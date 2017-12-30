package com.nz2dev.wordtrainer.domain.repositories;

import com.nz2dev.wordtrainer.domain.models.Course;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 30.12.2017
 */
public interface CourseRepository {

    Single<Long> addCourse(Course course);
    Single<Course> getCourse(long courseId);

}
