package com.nz2dev.wordtrainer.app.presentation.modules.settings.scheduling;

/**
 * Created by nz2Dev on 23.12.2017
 */
public interface SchedulingSettingsView {

    void showNextTrainingUnspecified();

    void showSchedulingLaunched();
    void showSchedulingStopped();

    void setTimeReminded(long millisUntilFinished);
    void setMaxInterval(int maxIntervalMinutes);
}
