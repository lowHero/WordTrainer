package com.nz2dev.wordtrainer.app.dependencies.components;

import com.nz2dev.wordtrainer.app.dependencies.PerActivity;
import com.nz2dev.wordtrainer.app.presentation.modules.courses.creation.CreateCourseFragment;

import dagger.Component;

/**
 * Created by nz2Dev on 30.12.2017
 */
@PerActivity
@Component(dependencies = AppComponent.class)
public interface CreateCourseComponent {
    void inject(CreateCourseFragment createCourseFragment);
}
