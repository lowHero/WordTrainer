package com.nz2dev.wordtrainer.data.repositories;

import com.nz2dev.wordtrainer.domain.models.Course;
import com.nz2dev.wordtrainer.domain.repositories.CourseRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 30.12.2017
 */
@Singleton
public class RoomCourseRepository implements CourseRepository {

    @Inject
    public RoomCourseRepository() {
    }

    @Override
    public Single<Long> addCourse(Course course) {
        return Single.just(1L);
    }

    @Override
    public Single<Course> getCourse(long courseId) {
        return Single.just(new Course(courseId, -1, -1, "English", null));
    }

}
