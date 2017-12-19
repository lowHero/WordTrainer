package com.nz2dev.wordtrainer.domain.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by nz2Dev on 16.12.2017
 */
public class Exercise {

    private Training training;
    private List<Word> translationVariants;

    public Exercise(Training training, Collection<Word> translationVariants) {
        this.training = training;
        this.translationVariants = new ArrayList<>(translationVariants);
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public void setTranslationVariants(Collection<Word> translationVariants) {
        this.translationVariants = new ArrayList<>(translationVariants);
    }

    public List<Word> getTranslationVariants() {
        return translationVariants;
    }

}
