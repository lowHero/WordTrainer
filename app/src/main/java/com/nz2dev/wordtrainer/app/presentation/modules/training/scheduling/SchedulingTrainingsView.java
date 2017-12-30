package com.nz2dev.wordtrainer.app.presentation.modules.training.scheduling;

/**
 * Created by nz2Dev on 23.12.2017
 */
public interface SchedulingTrainingsView {

    void showNextTrainingUnspecified();

    void showSchedulingLaunched();
    void showSchedulingStopped();

    void setTimeReminded(long millisUntilFinished);
    void setMaxInterval(int maxIntervalMinutes);
}
