package com.nz2dev.wordtrainer.app.presentation.modules.home;

import com.nz2dev.wordtrainer.app.dependencies.scopes.ForActionsContainers;
import com.nz2dev.wordtrainer.app.presentation.modules.courses.overview.CoursesOverviewComponent;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.TrainerComponent;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.TrainerModule;
import com.nz2dev.wordtrainer.app.presentation.modules.word.WordsComponent;

import dagger.Subcomponent;

/**
 * Created by nz2Dev on 30.11.2017
 */
@ForActionsContainers
@Subcomponent(modules = HomeModule.class)
public interface HomeComponent {

    TrainerComponent createTrainerComponent(TrainerModule trainerModule);
    CoursesOverviewComponent createCoursesOverviewComponent();
    WordsComponent createWordsComponent();

}
