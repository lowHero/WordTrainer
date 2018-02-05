package com.nz2dev.wordtrainer.app.presentation.modules.trainer.exercising.elevated;

import com.nz2dev.wordtrainer.app.presentation.scopes.ForActions;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.exercising.ExerciseTrainingFragment;

import dagger.Subcomponent;

/**
 * Created by nz2Dev on 08.01.2018
 */
@ForActions
@Subcomponent(modules = NavigationModule.class)
public interface ElevatedExerciseTrainingComponent {
    void inject(ExerciseTrainingFragment exerciseTrainingFragment);
}
