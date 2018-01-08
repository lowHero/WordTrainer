package com.nz2dev.wordtrainer.app.presentation.infrastructure;

/**
 * Created by nz2Dev on 29.11.2017
 */
public interface HasDependencies<T> {
    T getDependencies();
}
