package com.nz2dev.wordtrainer.domain.models;

import com.nz2dev.wordtrainer.domain.models.bindings.EmbeddedModel;

/**
 * Created by nz2Dev on 04.01.2018
 */
public class Language extends EmbeddedModel {

    public static final String KEY_EN = "en";
    public static final String KEY_UK = "uk";
    public static final String KEY_RU = "ru";

    public static Language likeKey(String key) {
        return new Language(key, null, null);
    }

    private String key;
    private String drawableUri;
    private String localizedName;

    public Language(String key, String drawableUri, String localizedName) {
        this.key = key;
        this.drawableUri = drawableUri;
        this.localizedName = localizedName;
    }

    @Override
    public boolean isQuiteDefined() {
        return drawableUri != null && localizedName != null;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDrawableUri() {
        return drawableUri;
    }

    public void setDrawableUri(String drawableUri) {
        this.drawableUri = drawableUri;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }
}
