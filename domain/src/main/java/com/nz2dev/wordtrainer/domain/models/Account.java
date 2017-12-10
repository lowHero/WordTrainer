package com.nz2dev.wordtrainer.domain.models;

/**
 * Created by nz2Dev on 30.11.2017
 */
public class Account {

    private int id;
    private String name;
    private boolean hasPassword;

    public Account(String name) {
        this(-1, name, false);
    }

    public Account(int id, String name, boolean hasPassword) {
        this.id = id;
        this.name = name;
        this.hasPassword = hasPassword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHasPassword() {
        return hasPassword;
    }

    public void setHasPassword(boolean hasPassword) {
        this.hasPassword = hasPassword;
    }
}
