package com.nz2dev.wordtrainer.app.presentation.modules.home;

import com.nz2dev.wordtrainer.app.common.dependencies.scopes.PerActivity;
import com.nz2dev.wordtrainer.app.presentation.modules.courses.overview.CoursesOverviewFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.debug.DebugFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.TrainerFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.training.exercising.ExerciseTrainingFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.training.overview.OverviewTrainingsFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.training.scheduling.SchedulingTrainingsFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.word.add.AddWordFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.word.edit.EditWordFragment;

import dagger.Subcomponent;

/**
 * Created by nz2Dev on 30.11.2017
 */
@PerActivity
@Subcomponent()
public interface HomeComponent {
    void inject(TrainerFragment trainerFragment);
    void inject(OverviewTrainingsFragment overviewTrainingsFragment);
    void inject(SchedulingTrainingsFragment schedulingTrainingsFragment);
    void inject(AddWordFragment addWordFragment);
    void inject(EditWordFragment editWordFragment);
    void inject(ExerciseTrainingFragment exerciseTrainingFragment);
    void inject(CoursesOverviewFragment coursesOverviewFragment);

    void inject(DebugFragment debugFragment);
}
