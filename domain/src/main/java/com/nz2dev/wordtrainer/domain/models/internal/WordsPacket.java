package com.nz2dev.wordtrainer.domain.models.internal;

import java.util.Collection;

/**
 * Created by nz2Dev on 11.01.2018
 */
public class WordsPacket {

    public final String originalLanguageKey;
    public final String translationlanguageKey;
    public final Collection<WordData> wordsData;

    public WordsPacket(String originalLanguageKey, String translationlanguageKey, Collection<WordData> wordsData) {
        this.originalLanguageKey = originalLanguageKey;
        this.translationlanguageKey = translationlanguageKey;
        this.wordsData = wordsData;
    }

}
