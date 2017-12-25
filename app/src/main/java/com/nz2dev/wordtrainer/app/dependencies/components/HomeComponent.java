package com.nz2dev.wordtrainer.app.dependencies.components;

import com.nz2dev.wordtrainer.app.dependencies.PerActivity;
import com.nz2dev.wordtrainer.app.presentation.modules.debug.DebugFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.home.HomeFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.settings.scheduling.SchedulingSettingsFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.training.exercising.ExerciseTrainingFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.training.overview.OverviewTrainingsFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.word.add.AddWordFragment;

import dagger.Component;

/**
 * Created by nz2Dev on 30.11.2017
 */
@PerActivity
@Component(dependencies = AppComponent.class)
public interface HomeComponent {
    void inject(HomeFragment homeFragment);
    void inject(OverviewTrainingsFragment overviewTrainingsFragment);
    void inject(SchedulingSettingsFragment schedulingSettingsFragment);
    void inject(AddWordFragment addWordFragment);
    void inject(ExerciseTrainingFragment exerciseTrainingFragment);

    void inject(DebugFragment debugFragment);
}
