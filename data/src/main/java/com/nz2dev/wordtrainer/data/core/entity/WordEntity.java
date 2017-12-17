package com.nz2dev.wordtrainer.data.core.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by nz2Dev on 11.12.2017
 */
@Entity
public class WordEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ForeignKey(entity = AccountEntity.class, parentColumns = "id", childColumns = "accountId", onDelete = CASCADE)
    private int accountId;

    @ColumnInfo
    private String original;

    @ColumnInfo
    private String translate;

    public WordEntity(int accountId, String original, String translate) {
        this.accountId = accountId;
        this.original = original;
        this.translate = translate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
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
