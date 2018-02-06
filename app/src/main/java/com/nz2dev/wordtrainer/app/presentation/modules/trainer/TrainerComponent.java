package com.nz2dev.wordtrainer.app.presentation.modules.trainer;

import com.nz2dev.wordtrainer.app.presentation.ForActions;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.exercising.ExerciseTrainingFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.overview.OverviewTrainingsFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.rules.SetUpRulesFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.scheduling.SetUpSchedulingFragment;

import dagger.Subcomponent;

/**
 * Created by nz2Dev on 16.01.2018
 */
@ForActions
@Subcomponent(modules = TrainerModule.class)
public interface TrainerComponent {

    void inject(TrainerFragment trainerFragment);
    void inject(OverviewTrainingsFragment overviewTrainingsFragment);
    void inject(SetUpSchedulingFragment setUpSchedulingFragment);
    void inject(SetUpRulesFragment setUpRulesFragment);
    void inject(ExerciseTrainingFragment exerciseTrainingFragment);

}
