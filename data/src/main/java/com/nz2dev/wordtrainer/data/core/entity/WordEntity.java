package com.nz2dev.wordtrainer.data.core.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by nz2Dev on 11.12.2017
 */
@Entity
public class WordEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String original;

    @ColumnInfo
    private String translate;

    public WordEntity(String original, String translate) {
        this.original = original;
        this.translate = translate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }
}
