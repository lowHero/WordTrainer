package com.nz2dev.wordtrainer.data.repositories;

import com.nz2dev.wordtrainer.data.R;
import com.nz2dev.wordtrainer.domain.models.Course;
import com.nz2dev.wordtrainer.domain.models.Language;
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
    public Single<Long> addCourse(Course.Primitive course) {
        return Single.just(1L);
    }

    @Override
    public Single<Course> getCourse(long courseId) {
        Language original = new Language("en", R.drawable.ic_flag_english, R.string.english_language);
        Language translation = new Language("uk", R.drawable.ic_flag_ukraine, R.string.ukrainian_language);
        return Single.just(new Course(1, -1, 1, original, translation));
    }

}
