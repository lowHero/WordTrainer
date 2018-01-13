package com.nz2dev.wordtrainer.app.presentation;

import android.app.Activity;

import com.nz2dev.wordtrainer.app.presentation.modules.account.AccountActivity;
import com.nz2dev.wordtrainer.app.presentation.modules.courses.creation.CreateCourseActivity;
import com.nz2dev.wordtrainer.app.presentation.modules.home.HomeActivity;
import com.nz2dev.wordtrainer.app.presentation.modules.startup.StartupActivity;
import com.nz2dev.wordtrainer.app.presentation.modules.word.explore.ExploreWordsSourceActivity;
import com.nz2dev.wordtrainer.app.presentation.modules.word.exporting.ExportWordsActivity;
import com.nz2dev.wordtrainer.app.presentation.modules.word.importing.ImportWordsActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by nz2Dev on 30.11.2017
 */
@Singleton
public class Navigator {

    @Inject
    public Navigator() {
    }

    public void navigateHomeFrom(Activity source) {
        // TODO perform some animation
        source.startActivity(HomeActivity.getCallingIntent(source));
    }

    public void navigateAccountFrom(Activity source) {
        source.startActivity(AccountActivity.getCallingIntent(source));
    }

    public void navigateCourseCreationFrom(Activity source) {
        source.startActivity(CreateCourseActivity.getCallingIntent(source));
    }

    public void navigateWordsExportingFrom(Activity source, long courseId) {
        source.startActivity(ExportWordsActivity.getCallingIntent(source, courseId));
    }

    public void navigateWordsExploring(Activity source) {
        source.startActivity(ExploreWordsSourceActivity.getCallingIntent(source));
    }

    public void navigateWordsImportingFrom(Activity source, String filePath) {
        source.startActivity(ImportWordsActivity.getCallingIntent(source, filePath));
    }

}
