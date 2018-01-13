package com.nz2dev.wordtrainer.domain.interactors.course;

import com.nz2dev.wordtrainer.domain.models.CourseBase;

/**
 * Created by nz2Dev on 09.01.2018
 */
public final class CourseEvent {

    public enum Type {
        Select,
        Added,
        Deleted,
        NotSpecified
    }

    static CourseEvent newSelect(CourseBase course) {
        return new CourseEvent(Type.Select, course);
    }

    static CourseEvent newAdded(CourseBase course) {
        return new CourseEvent(Type.Added, course);
    }

    static CourseEvent newDeleted(CourseBase course) {
        return new CourseEvent(Type.Deleted, course);
    }

    static CourseEvent newNotSpecified() {
        return new CourseEvent(Type.NotSpecified, null);
    }

    private final Type type;
    private final CourseBase course;

    private CourseEvent(Type type, CourseBase course) {
        this.type = type;
        this.course = course;
    }

    public boolean isSelected() {
        return Type.Select.equals(type);
    }

    public boolean isAdded() {
        return Type.Added.equals(type);
    }

    public boolean isDeleted() {
        return Type.Deleted.equals(type);
    }

    public boolean isChanged() {
        return isAdded() || isDeleted();
    }

    public boolean isNotSpecified() {
        return Type.NotSpecified.equals(type);
    }

    public CourseBase getCourse() {
        return course;
    }
}
