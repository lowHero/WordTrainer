package com.nz2dev.wordtrainer.domain.models;

import java.util.Date;

/**
 * Created by nz2Dev on 16.12.2017
 */
public class Training {

    private int id;
    private Word word;
    private Date lastTrainingDate;
    private int progress;

    public Training(int id, Word word, Date lastTrainingDate, int progress) {
        this.id = id;
        this.word = word;
        this.lastTrainingDate = lastTrainingDate;
        this.progress = progress;
    }

    public void setData(Training training) {
        if (training.getId() != this.id) {
            throw new RuntimeException("training id's aren't the same");
        }
        setProgress(training.getProgress());
        setLastTrainingDate(training.getLastTrainingDate());
        setWord(training.getWord());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public Date getLastTrainingDate() {
        return lastTrainingDate;
    }

    public void setLastTrainingDate(Date lastTrainingDate) {
        this.lastTrainingDate = lastTrainingDate;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
