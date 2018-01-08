package com.nz2dev.wordtrainer.domain.models;

/**
 * Created by nz2Dev on 30.12.2017
 */
public class Course {

    public static class Primitive {

        public static Primitive unidentified(long accountId, long schedulingId, String originalLanguageKey, String translationLanguageKey) {
            return new Primitive(0, accountId, schedulingId, originalLanguageKey, translationLanguageKey);
        }

        public final long id;
        public final long accountId;
        public final long schedulingId;
        public final String originalLanguageKey;
        public final String translationLanguageKey;

        public Primitive(long id, long accountId, long schedulingId, String originalLanguageKey, String translationLanguageKey) {
            this.id = id;
            this.accountId = accountId;
            this.schedulingId = schedulingId;
            this.originalLanguageKey = originalLanguageKey;
            this.translationLanguageKey = translationLanguageKey;
        }
    }

    public static class Base {

        private long id;
        private Language originalLanguage;
        private Language translationLanguage;

        public Base(long id, Language originalLanguage, Language translationLanguage) {
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

        public Language getOriginalLanguage() {
            return originalLanguage;
        }

        public void setOriginalLanguage(Language originalLanguage) {
            this.originalLanguage = originalLanguage;
        }

        public Language getTranslationLanguage() {
            return translationLanguage;
        }

        public void setTranslationLanguage(Language translationLanguage) {
            this.translationLanguage = translationLanguage;
        }
    }

    private long id;
    private long accountId;
    private long schedulingId;
    private Language originalLanguage;
    private Language translationLanguage;

    public Course(long id, long accountId, long schedulingId, Language originalLanguage, Language translationLanguage) {
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

    public Language getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(Language originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public Language getTranslationLanguage() {
        return translationLanguage;
    }

    public void setTranslationLanguage(Language translationLanguage) {
        this.translationLanguage = translationLanguage;
    }
}
