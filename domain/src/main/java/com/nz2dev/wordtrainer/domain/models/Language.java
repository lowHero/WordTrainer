package com.nz2dev.wordtrainer.domain.models;

/**
 * Created by nz2Dev on 04.01.2018
 */
public class Language {

    public static final String KEY_EN = "en";
    public static final String KEY_UK = "uk";
    public static final String KEY_RU = "ru";

    private String key;
    private int drawableResId;
    private int localizedNameStringId;

    public Language(String key, int drawableResId, int localizedNameStringId) {
        this.key = key;
        this.drawableResId = drawableResId;
        this.localizedNameStringId = localizedNameStringId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getDrawableResId() {
        return drawableResId;
    }

    public void setDrawableResId(int drawableResId) {
        this.drawableResId = drawableResId;
    }

    public int getLocalizedNameStringId() {
        return localizedNameStringId;
    }

    public void setLocalizedNameStringId(int localizedNameStringId) {
        this.localizedNameStringId = localizedNameStringId;
    }
}
