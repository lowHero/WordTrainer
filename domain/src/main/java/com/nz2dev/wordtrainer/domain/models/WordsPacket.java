package com.nz2dev.wordtrainer.domain.models;

import java.util.Collection;

/**
 * Created by nz2Dev on 11.01.2018
 */
public class WordsPacket {

    public final String originalLangCode;
    public final String translationLangCode;
    public final Collection<WordData> data;

    public WordsPacket(String originalLangCode, String translationLangCode, Collection<WordData> data) {
        this.originalLangCode = originalLangCode;
        this.translationLangCode = translationLangCode;
        this.data = data;
    }

    public WordsPacket separate(Collection<WordData> dataSelection) {
        if (!data.containsAll(dataSelection)) {
            throw new RuntimeException("not all dataSelection exist in this packet");
        }
        return new WordsPacket(originalLangCode, translationLangCode, dataSelection);
    }

}
