package com.nz2dev.wordtrainer.app.utils.helpers;

/**
 * Created by nz2Dev on 17.01.2018
 */
public class Optional<T> {

    private T item;

    public Optional(T item) {
        this.item = item;
    }

    public boolean isPresent() {
        return item != null;
    }

    public T get() {
        return item;
    }

}
