package com.nz2dev.wordtrainer.app.presentation.modules.training.scheduling;

/**
 * Created by nz2Dev on 23.12.2017
 */
public interface SchedulingTrainingsView {

    long UNSPECIFIED_INTERVAL = 0L;

    void showError(String error);
    void showSchedulingLaunched();
    void showSchedulingStopped();
    void showIntervalChanging(boolean show);
    void showTimeLeft(long millisUntilFinished);

    void setFutureInterval(long intervalMillis);
    void setActualInterval(long intervalMillis);

}
