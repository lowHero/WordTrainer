package com.nz2dev.wordtrainer.device.files.serialization;

import com.google.gson.annotations.SerializedName;

import java.util.Collection;

/**
 * Created by nz2Dev on 11.01.2018
 */
public class JsonWordsPacket {

    @SerializedName("original_language_key")
    public final String originalLanguageKey;

    @SerializedName("translation_language_key")
    public final String translationLanguageKey;

    @SerializedName("wordsData")
    public final Collection<JsonWordData> wordsData;

    public JsonWordsPacket(String originalLanguageKey, String translationLanguageKey, Collection<JsonWordData> wordsData) {
        this.originalLanguageKey = originalLanguageKey;
        this.translationLanguageKey = translationLanguageKey;
        this.wordsData = wordsData;
    }

}
