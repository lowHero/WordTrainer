package com.nz2dev.wordtrainer.domain.models;

/**
 * Created by nz2Dev on 30.12.2017
 */
public class Course {

    public static Course unidentified(long accountId, long schedulingId, String originalLanguage, String translationLanguage) {
        return new Course(0, accountId, schedulingId, originalLanguage, translationLanguage);
    }

    public static class Base {

        private long id;
        private String originalLanguage;
        private String translationLanguage;

        public Base(long id, String originalLanguage, String translationLanguage) {
            this.id = id;
            this.originalLanguage = originalLanguage;
            this.translationLanguage = translationLanguage;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getOriginalLanguage() {
            return originalLanguage;
        }

        public void setOriginalLanguage(String originalLanguage) {
            this.originalLanguage = originalLanguage;
        }

        public String getTranslationLanguage() {
            return translationLanguage;
        }

        public void setTranslationLanguage(String translationLanguage) {
            this.translationLanguage = translationLanguage;
        }
    }

    private long id;
    private long accountId;
    private long schedulingId;
    private String originalLanguage;
    private String translationLanguage;

    public Course(long id, long accountId, long schedulingId, String originalLanguage, String translationLanguage) {
        this.id = id;
        this.accountId = accountId;
        this.schedulingId = schedulingId;
        this.originalLanguage = originalLanguage;
        this.translationLanguage = translationLanguage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getSchedulingId() {
        return schedulingId;
    }

    public void setSchedulingId(long schedulingId) {
        this.schedulingId = schedulingId;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTranslationLanguage() {
        return translationLanguage;
    }

    public void setTranslationLanguage(String translationLanguage) {
        this.translationLanguage = translationLanguage;
    }
}
