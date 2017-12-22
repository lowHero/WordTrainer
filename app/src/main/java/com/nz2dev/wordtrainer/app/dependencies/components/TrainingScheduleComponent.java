package com.nz2dev.wordtrainer.app.dependencies.components;

import com.nz2dev.wordtrainer.app.dependencies.PerService;
import com.nz2dev.wordtrainer.app.services.training.TrainingScheduleService;

import dagger.Component;

/**
 * Created by nz2Dev on 22.12.2017
 */
@PerService
@Component(dependencies = AppComponent.class)
public interface TrainingScheduleComponent {
    void inject(TrainingScheduleService trainingScheduleService);
}
