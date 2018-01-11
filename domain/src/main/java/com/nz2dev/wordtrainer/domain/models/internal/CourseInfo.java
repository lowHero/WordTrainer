package com.nz2dev.wordtrainer.domain.models.internal;

import com.nz2dev.wordtrainer.domain.models.CourseBase;

/**
 * Created by nz2Dev on 10.01.2018
 */
public class CourseInfo {

    private CourseBase course;
    private int wordsCount;

    public CourseInfo(CourseBase course, int wordsCount) {
        this.course = course;
        this.wordsCount = wordsCount;
    }

    public CourseBase getCourse() {
        return course;
    }

    public void setCourse(CourseBase course) {
        this.course = course;
    }

    public int getWordsCount() {
        return wordsCount;
    }

    public void setWordsCount(int wordsCount) {
        this.wordsCount = wordsCount;
    }
}
