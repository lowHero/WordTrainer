package com.nz2dev.wordtrainer.app.presentation.modules.word.importing;

import com.nz2dev.wordtrainer.app.common.dependencies.scopes.PerActivity;

import dagger.Subcomponent;

/**
 * Created by nz2Dev on 11.01.2018
 */
@PerActivity
@Subcomponent
public interface ImportWordsComponent {
    void inject(ImportWordsFragment importWordsFragment);
}
