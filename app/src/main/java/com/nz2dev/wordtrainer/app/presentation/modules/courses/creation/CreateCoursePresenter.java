package com.nz2dev.wordtrainer.app.presentation.modules.courses.creation;

import com.nz2dev.wordtrainer.app.dependencies.PerActivity;
import com.nz2dev.wordtrainer.app.preferences.AppPreferences;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.BasePresenter;
import com.nz2dev.wordtrainer.app.utils.helpers.ErrorHandler;
import com.nz2dev.wordtrainer.domain.interactors.CourseInteractor;
import com.nz2dev.wordtrainer.domain.interactors.SchedulingInteractor;
import com.nz2dev.wordtrainer.domain.models.Course;
import com.nz2dev.wordtrainer.domain.models.Scheduling;

import javax.inject.Inject;

/**
 * Created by nz2Dev on 30.12.2017
 */
@PerActivity
public class CreateCoursePresenter extends BasePresenter<CreateCourseView> {

    private final AppPreferences appPreferences;
    private final CourseInteractor courseInteractor;
    private final SchedulingInteractor schedulingInteractor;

    @Inject
    public CreateCoursePresenter(AppPreferences appPreferences, CourseInteractor courseInteractor, SchedulingInteractor schedulingInteractor) {
        this.appPreferences = appPreferences;
        this.courseInteractor = courseInteractor;
        this.schedulingInteractor = schedulingInteractor;
    }

    public void createCourseClick(String courseLanguage) {
        schedulingInteractor.uploadScheduling(Scheduling.newInstance(), (id, throwable) -> {
            if (throwable != null) {
                getView().showError(ErrorHandler.describe(throwable));
                return;
            }

            Course course = Course.unidentified(appPreferences.getSignedAccountId(), id, courseLanguage, null);

            courseInteractor.uploadCourse(course, (courseId, courseCreationThrowable) -> {
                if (courseCreationThrowable != null) {
                    getView().showError(ErrorHandler.describe(courseCreationThrowable));
                    return;
                }

                course.setId(courseId);
                appPreferences.selectCourse(course);
                getView().hideId();
            });
        });
    }

}
