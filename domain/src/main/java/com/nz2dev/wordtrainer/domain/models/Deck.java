package com.nz2dev.wordtrainer.domain.models;

/**
 * Created by nz2Dev on 06.02.2018
 */
public class Deck {

    public static final String DEFAULT_NAME = "Default";

    public static Deck unidentified(long courseId, String name) {
        return new Deck(0L, courseId, name);
    }

    private long id;
    private long courseId;
    private String name;

    public Deck(long id, long courseId, String name) {
        this.id = id;
        this.courseId = courseId;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
