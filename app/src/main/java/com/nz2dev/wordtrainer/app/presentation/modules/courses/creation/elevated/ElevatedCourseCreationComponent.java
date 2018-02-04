package com.nz2dev.wordtrainer.app.presentation.modules.courses.creation.elevated;

import com.nz2dev.wordtrainer.app.dependencies.scopes.ForActions;
import com.nz2dev.wordtrainer.app.presentation.modules.courses.creation.CourseCreationFragment;

import dagger.Subcomponent;

/**
 * Created by nz2Dev on 30.12.2017
 */
@ForActions
@Subcomponent()
public interface ElevatedCourseCreationComponent {
    void inject(CourseCreationFragment createCourseFragment);
}
