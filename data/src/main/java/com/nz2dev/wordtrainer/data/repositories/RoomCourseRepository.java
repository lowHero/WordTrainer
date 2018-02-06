package com.nz2dev.wordtrainer.data.repositories;

import com.nz2dev.wordtrainer.data.source.local.WordTrainerDatabase;
import com.nz2dev.wordtrainer.data.source.local.dao.CourseDao;
import com.nz2dev.wordtrainer.data.source.local.entity.CourseEntity;
import com.nz2dev.wordtrainer.data.source.local.mapping.DatabaseMapper;
import com.nz2dev.wordtrainer.data.source.local.relation.CourseBaseSet;
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
    private DatabaseMapper databaseMapper;

    @Inject
    public RoomCourseRepository(WordTrainerDatabase database, DatabaseMapper databaseMapper) {
        this.courseDao = database.getCourseDao();
        this.databaseMapper = databaseMapper;
    }

    @Override
    public Single<Long> addCourse(Course course) {
        return Single.create(emitter -> {
            long id = courseDao.add(databaseMapper.map(course, CourseEntity.class));
            emitter.onSuccess(id);
        });
    }

    @Override
    public Single<CourseBase> getCourseBase(long courseId) {
        return Single.create(emitter -> {
            CourseBaseSet entity = courseDao.getCourseBase(courseId);
            emitter.onSuccess(databaseMapper.map(entity, CourseBase.class));
        });
    }

    @Override
    public Single<Collection<CourseBase>> getCoursesBase() {
        return Single.create(emitter -> {
            List<CourseBaseSet> entityList = courseDao.getCoursesBase();
            Collection<CourseBase> courses = databaseMapper.mapList(entityList, new ArrayList<>(entityList.size()), CourseBase.class);
            emitter.onSuccess(courses);
        });
    }

    @Override
    public Single<CourseBase> getCourseBaseByOriginalLanguageKey(String originalLanguageKey) {
        return Single.create(emitter -> {
            CourseBaseSet entity = courseDao.getCourseBaseByOriginalLanguageKey(originalLanguageKey);
            CourseBase course = databaseMapper.map(entity, CourseBase.class);
            emitter.onSuccess(course);
        });
    }

    @Override
    public Single<Course> deleteCourse(long courseId) {
        return Single.create(emitter -> {
            CourseEntity entity = courseDao.getCourse(courseId);
            courseDao.deleteCourse(entity);
            emitter.onSuccess(databaseMapper.map(entity, Course.class));
        });
    }

}
