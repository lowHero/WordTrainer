package com.nz2dev.wordtrainer.domain.models;

import java.util.Date;

/**
 * Created by nz2Dev on 16.12.2017
 */
public class Training {

    public static Training unidentified(Word word) {
        return new Training(0L, word, null, 0);
    }

    private long id;
    private Word word;
    private Date lastTrainingDate;
    private long progress;

    public Training(long id, Word word, Date lastTrainingDate, long progress) {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }
}
