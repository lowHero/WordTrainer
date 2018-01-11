package com.nz2dev.wordtrainer.domain.interactors.training;

import com.nz2dev.wordtrainer.domain.models.Training;

/**
 * Created by nz2Dev on 11.01.2018
 */
public final class TrainingEvent {

    public enum Type {
        Updated
    }

    static TrainingEvent newUpdated(Training training) {
        return new TrainingEvent(training, Type.Updated);
    }

    private Training training;
    private Type type;

    private TrainingEvent(Training training, Type type) {
        this.training = training;
        this.type = type;
    }

    public boolean isUpdated() {
        return Type.Updated.equals(type);
    }

    public Training getTraining() {
        return training;
    }

}
