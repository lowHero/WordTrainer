package com.nz2dev.wordtrainer.app.presentation.modules.training.exercising;

import com.nz2dev.wordtrainer.app.common.dependencies.scopes.PerActivity;

import dagger.Subcomponent;

/**
 * Created by nz2Dev on 08.01.2018
 */
@PerActivity
@Subcomponent
public interface ExerciseTrainingComponent {
    void inject(ExerciseTrainingFragment exerciseTrainingFragment);
}
