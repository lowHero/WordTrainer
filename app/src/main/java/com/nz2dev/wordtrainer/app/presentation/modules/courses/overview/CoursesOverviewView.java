package com.nz2dev.wordtrainer.app.presentation.modules.courses.overview;

import com.nz2dev.wordtrainer.domain.models.internal.CourseInfo;

import java.util.Collection;

/**
 * Created by nz2Dev on 09.01.2018
 */
public interface CoursesOverviewView {

    void showCourses(Collection<CourseInfo> courses);

    void showSelectedCourse(long currentCourseId);

    void navigateCourseAddition();

    void navigateWordsExporting(long courseId);

}
