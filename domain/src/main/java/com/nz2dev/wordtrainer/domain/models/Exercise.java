package com.nz2dev.wordtrainer.domain.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by nz2Dev on 16.12.2017
 */
public class Exercise {

    private Training originalWordTraining;
    private List<Word> translationVariants;

    public Exercise(Training originalWordTraining, Collection<Word> translationVariants) {
        this.originalWordTraining = originalWordTraining;
        this.translationVariants = new ArrayList<>(translationVariants);
    }

    public Training getOriginalWordTraining() {
        return originalWordTraining;
    }

    public void setOriginalWordTraining(Training originalWordTraining) {
        this.originalWordTraining = originalWordTraining;
    }

    public void setTranslationVariants(Collection<Word> translationVariants) {
        this.translationVariants = new ArrayList<>(translationVariants);
    }

    public List<Word> getTranslationVariants() {
        return translationVariants;
    }

}
