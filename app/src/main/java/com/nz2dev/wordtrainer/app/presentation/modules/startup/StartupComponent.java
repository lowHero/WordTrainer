package com.nz2dev.wordtrainer.app.presentation.modules.startup;

import com.nz2dev.wordtrainer.app.dependencies.scopes.ForActions;

import dagger.Subcomponent;

/**
 * Created by nz2Dev on 08.01.2018
 */
@ForActions
@Subcomponent
public interface StartupComponent {
    void inject(StartupActivity startupActivity);
}
