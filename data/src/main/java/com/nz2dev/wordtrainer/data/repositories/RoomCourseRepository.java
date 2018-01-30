package com.nz2dev.wordtrainer.data.repositories;

import com.nz2dev.wordtrainer.data.core.WordTrainerDatabase;
import com.nz2dev.wordtrainer.data.core.dao.CourseDao;
import com.nz2dev.wordtrainer.data.core.entity.CourseEntity;
import com.nz2dev.wordtrainer.data.core.relation.CourseBaseSet;
import com.nz2dev.wordtrainer.data.mapping.Mapper;
import com.nz2dev.wordtrainer.domain.models.Course;
import com.nz2dev.wordtrainer.domain.models.CourseBase;
import com.nz2dev.wordtrainer.domain.data.repositories.CourseRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 30.12.2017
 */
@Singleton
public class RoomCourseRepository implements CourseRepository {

    private CourseDao courseDao;
    private Mapper mapper;

    @Inject
    public RoomCourseRepository(WordTrainerDatabase database, Mapper mapper) {
        this.courseDao = database.getCourseDao();
        this.mapper = mapper;
    }

    @Override
    public Single<Long> addCourse(Course course) {
        return Single.create(emitter -> {
            long id = courseDao.add(mapper.map(course, CourseEntity.class));
            emitter.onSuccess(id);
        });
    }

    @Override
    public Single<CourseBase> getCourseBase(long courseId) {
        return Single.create(emitter -> {
            CourseBaseSet entity = courseDao.getCourseBase(courseId);
            emitter.onSuccess(mapper.map(entity, CourseBase.class));
        });
    }

    @Override
    public Single<Collection<CourseBase>> getCoursesBase() {
        return Single.create(emitter -> {
            List<CourseBaseSet> entityList = courseDao.getCoursesBase();
            Collection<CourseBase> courses = mapper.mapList(entityList, new ArrayList<>(entityList.size()), CourseBase.class);
            emitter.onSuccess(courses);
        });
    }

    @Override
    public Single<CourseBase> getCourseBaseByOriginalLanguageKey(String originalLanguageKey) {
        return Single.create(emitter -> {
            CourseBaseSet entity = courseDao.getCourseBaseByOriginalLanguageKey(originalLanguageKey);
            CourseBase course = mapper.map(entity, CourseBase.class);
            emitter.onSuccess(course);
        });
    }

    @Override
    public Single<Course> deleteCourse(long courseId) {
        return Single.create(emitter -> {
            CourseEntity entity = courseDao.getCourse(courseId);
            courseDao.deleteCourse(entity);
            emitter.onSuccess(mapper.map(entity, Course.class));
        });
    }

}
