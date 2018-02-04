package com.nz2dev.wordtrainer.app.presentation.modules.courses.overview;

import com.nz2dev.wordtrainer.app.common.scopes.ForActions;

import dagger.Subcomponent;

/**
 * Created by nz2Dev on 17.01.2018
 */
@ForActions
@Subcomponent
public interface CoursesOverviewComponent {
    void inject(CoursesOverviewFragment coursesOverviewFragment);
}
