package com.nz2dev.wordtrainer.app.services.training;

import com.nz2dev.wordtrainer.domain.models.Training;

/**
 * Created by nz2Dev on 22.12.2017
 */
public interface TrainingScheduleHandler {

    void notifyTraining(Training training);
    void handleError(String error);
    void finishWork();

}
