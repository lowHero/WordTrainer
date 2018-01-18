package com.nz2dev.wordtrainer.app.common.dependencies;

import com.nz2dev.wordtrainer.app.common.dependencies.modules.AppModule;
import com.nz2dev.wordtrainer.app.common.dependencies.modules.DataModule;
import com.nz2dev.wordtrainer.app.common.dependencies.modules.DeviceModule;
import com.nz2dev.wordtrainer.app.presentation.modules.account.registration.elevated.ElevatedRegistrationComponent;
import com.nz2dev.wordtrainer.app.presentation.modules.courses.creation.elevated.ElevatedCourseCreationComponent;
import com.nz2dev.wordtrainer.app.presentation.modules.home.elevated.ElevatedHomeComponent;
import com.nz2dev.wordtrainer.app.presentation.modules.startup.StartupComponent;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.exercising.elevated.ElevatedExerciseTrainingComponent;
import com.nz2dev.wordtrainer.app.presentation.modules.word.explore.elevated.ElevatedExploreWordsSourceComponent;
import com.nz2dev.wordtrainer.app.presentation.modules.word.exporting.elevated.ExportWordsComponent;
import com.nz2dev.wordtrainer.app.presentation.modules.word.importing.elevated.ImportWordsComponent;
import com.nz2dev.wordtrainer.app.services.training.TrainingScheduleComponent;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by nz2Dev on 29.11.2017
 */
@Singleton
@Component(modules = {AppModule.class, DataModule.class, DeviceModule.class})
public interface AppComponent {

    ElevatedHomeComponent createElevatedHomeComponent();
    ElevatedExerciseTrainingComponent createElevatedExerciseTrainingComponent();
    ElevatedCourseCreationComponent createElevatedCourseCreationComponent();
    ElevatedRegistrationComponent createElevatedRegistrationComponent();
    ElevatedExploreWordsSourceComponent createElevatedExploreWordsSourceComponent();

    StartupComponent createStartupComponent();
    TrainingScheduleComponent createTrainingScheduleComponent();

    // TODO make naming with elevated prefix or if will found better approach change appropriate
    ExportWordsComponent createExportWordsComponent();
    ImportWordsComponent createImportWordsComponent();

}