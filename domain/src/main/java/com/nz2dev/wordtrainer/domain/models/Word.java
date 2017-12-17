package com.nz2dev.wordtrainer.domain.models;

/**
 * Created by nz2Dev on 30.11.2017
 */
public class Word {

    private int id;
    private int accountId;
    private String original;
    private String translate;

    public Word(int accountId, String original, String translate) {
        this.accountId = accountId;
        this.original = original;
        this.translate = translate;
    }

    // TODO remove this constructor
    public Word(int id, int accountId, String original, String translate) {
        this.id = id;
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
