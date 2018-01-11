package com.nz2dev.wordtrainer.domain.models.internal;

/**
 * Created by nz2Dev on 11.01.2018
 */
public class WordData {

    public final String original;
    public final String translation;

    public WordData(String original, String translation) {
        this.original = original;
        this.translation = translation;
    }

}
