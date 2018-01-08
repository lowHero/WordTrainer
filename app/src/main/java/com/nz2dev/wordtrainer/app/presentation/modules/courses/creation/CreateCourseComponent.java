package com.nz2dev.wordtrainer.app.presentation.modules.courses.creation;

import com.nz2dev.wordtrainer.app.common.dependencies.scopes.PerActivity;
import com.nz2dev.wordtrainer.app.common.dependencies.modules.AppModule;
import com.nz2dev.wordtrainer.app.common.dependencies.modules.DataModule;

import dagger.Subcomponent;

/**
 * Created by nz2Dev on 30.12.2017
 */
@PerActivity
@Subcomponent(modules = {AppModule.class, DataModule.class})
public interface CreateCourseComponent {
    void inject(CreateCourseFragment createCourseFragment);
}
