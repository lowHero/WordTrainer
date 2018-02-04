package com.nz2dev.wordtrainer.app.services.training;

import com.nz2dev.wordtrainer.app.common.scopes.ForActions;

import dagger.Subcomponent;

/**
 * Created by nz2Dev on 22.12.2017
 */
@ForActions
@Subcomponent()
public interface TrainingScheduleComponent {
    void inject(TrainingScheduleService trainingScheduleService);
}
