package com.nz2dev.wordtrainer.app.common.dependencies;

import com.nz2dev.wordtrainer.app.common.dependencies.modules.AppModule;
import com.nz2dev.wordtrainer.app.common.dependencies.modules.DataModule;
import com.nz2dev.wordtrainer.app.common.dependencies.modules.DeviceModule;
import com.nz2dev.wordtrainer.app.presentation.modules.account.AccountComponent;
import com.nz2dev.wordtrainer.app.presentation.modules.account.AccountModule;
import com.nz2dev.wordtrainer.app.presentation.modules.courses.creation.CreateCourseComponent;
import com.nz2dev.wordtrainer.app.presentation.modules.home.HomeComponent;
import com.nz2dev.wordtrainer.app.presentation.modules.startup.StartupActivity;
import com.nz2dev.wordtrainer.app.presentation.modules.startup.StartupComponent;
import com.nz2dev.wordtrainer.app.presentation.modules.training.exercising.ExerciseTrainingComponent;
import com.nz2dev.wordtrainer.app.services.training.TrainingScheduleComponent;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by nz2Dev on 29.11.2017
 */
@Singleton
@Component(modules = {AppModule.class, DataModule.class, DeviceModule.class})
public interface AppComponent {

    StartupComponent createStartupComponent();

    HomeComponent createHomeComponent();

    AccountComponent createAccountComponent(AccountModule accountModule);

    ExerciseTrainingComponent createExerciseTrainingComponent();

    CreateCourseComponent createCreateCourseComponent();

    TrainingScheduleComponent createTrainingScheduleComponent();

}
