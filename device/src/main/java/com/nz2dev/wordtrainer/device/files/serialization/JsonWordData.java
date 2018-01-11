package com.nz2dev.wordtrainer.device.files.serialization;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nz2Dev on 11.01.2018
 */
public class JsonWordData {

    @SerializedName("original")
    public final String original;

    @SerializedName("translation")
    public final String translation;

    public JsonWordData(String original, String translation) {
        this.original = original;
        this.translation = translation;
    }
}
