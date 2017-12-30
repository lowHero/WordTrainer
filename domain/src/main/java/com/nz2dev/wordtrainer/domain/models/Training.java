package com.nz2dev.wordtrainer.domain.models;

import java.util.Date;

/**
 * Created by nz2Dev on 16.12.2017
 */
public class Training {

    // Primitive in case when has relationship that replace modelId onto modelObject
    // but the main model has same structure in data layer
    public static class Primitive {

        public static Primitive of(long id, long wordId, long time, long progress) {
            return new Primitive(id, wordId, time, progress);
        }

        public final long id;
        public final long wordId;
        public final long time;
        public final long progress;

        private Primitive(long id, long wordId, long time, long progress) {
            this.id = id;
            this.wordId = wordId;
            this.time = time;
            this.progress = progress;
        }
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
