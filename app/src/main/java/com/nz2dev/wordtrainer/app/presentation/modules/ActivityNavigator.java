package com.nz2dev.wordtrainer.app.presentation.modules;

import android.app.Activity;

import com.nz2dev.wordtrainer.app.presentation.modules.courses.creation.elevated.ElevatedCourseCreationActivity;
import com.nz2dev.wordtrainer.app.presentation.modules.home.elevated.ElevatedHomeActivity;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.exercising.elevated.ElevatedExerciseTrainingActivity;
import com.nz2dev.wordtrainer.app.presentation.modules.word.explore.elevated.ElevatedExploreWordsSourceActivity;
import com.nz2dev.wordtrainer.app.presentation.modules.word.exporting.elevated.ExportWordsActivity;
import com.nz2dev.wordtrainer.app.presentation.modules.word.importing.elevated.ImportWordsActivity;
import com.nz2dev.wordtrainer.domain.exceptions.NotImplementedException;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by nz2Dev on 30.11.2017
 */
@Singleton
public class ActivityNavigator {

    @Inject
    public ActivityNavigator() {
    }

    public void navigateToHomeFrom(Activity source) {
        // TODO perform some animation
        source.startActivity(ElevatedHomeActivity.getCallingIntent(source));
    }

    public void navigateToCourseCreationFrom(Activity source) {
        source.startActivity(ElevatedCourseCreationActivity.getCallingIntent(source));
    }

    public void navigateToWordsSearchingFrom(Activity source) {
        source.startActivity(ElevatedExploreWordsSourceActivity.getCallingIntent(source));
    }

    public void navigateToWordCreationFrom(Activity source) {
        throw new NotImplementedException("Activity for word creation");
    }

    public void navigateToWordsImportingFrom(Activity source, String filePath) {
        source.startActivity(ImportWordsActivity.getCallingIntent(source, filePath));
    }

    public void navigateToWordsExportingFrom(Activity source, long courseId) {
        source.startActivity(ExportWordsActivity.getCallingIntent(source, courseId));
    }

    public void navigateToWordTrainingFrom(Activity source, long trainingId) {
        source.startActivity(ElevatedExerciseTrainingActivity.getCallingIntent(source, trainingId));
    }

    public void navigateToWordShowing(Activity activity, long wordId) {
        throw new NotImplementedException("Activity for word showing");
    }

}
