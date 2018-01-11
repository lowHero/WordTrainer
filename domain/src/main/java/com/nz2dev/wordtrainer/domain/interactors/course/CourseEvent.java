package com.nz2dev.wordtrainer.domain.interactors.course;

import com.nz2dev.wordtrainer.domain.models.CourseBase;

/**
 * Created by nz2Dev on 09.01.2018
 */
public final class CourseEvent {

    public enum Type {
        Select,
        Changed
    }

    static CourseEvent newSelect(CourseBase course) {
        return new CourseEvent(Type.Select, course);
    }

    static CourseEvent newChanged(CourseBase course) {
        return new CourseEvent(Type.Changed, course);
    }

    private final Type type;
    private final CourseBase course;

    private CourseEvent(Type type, CourseBase course) {
        this.type = type;
        this.course = course;
    }

    public boolean isSelected() {
        return type.equals(Type.Select);
    }

    public boolean isChanged() {
        return type.equals(Type.Changed);
    }

    public CourseBase getCourse() {
        return course;
    }
}
