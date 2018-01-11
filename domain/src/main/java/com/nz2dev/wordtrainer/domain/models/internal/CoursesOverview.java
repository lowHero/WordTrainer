package com.nz2dev.wordtrainer.domain.models.internal;

import java.util.Collection;

/**
 * Created by nz2Dev on 09.01.2018
 */
public class CoursesOverview {

    private Collection<CourseInfo> courses;
    private long selectedCourseId;

    public CoursesOverview(Collection<CourseInfo> courses, long selectedCourseId) {
        this.courses = courses;
        this.selectedCourseId = selectedCourseId;
    }

    public Collection<CourseInfo> getCourses() {
        return courses;
    }

    public void setCourses(Collection<CourseInfo> courses) {
        this.courses = courses;
    }

    public long getSelectedCourseId() {
        return selectedCourseId;
    }

    public void setSelectedCourseId(long selectedCourseId) {
        this.selectedCourseId = selectedCourseId;
    }
}
