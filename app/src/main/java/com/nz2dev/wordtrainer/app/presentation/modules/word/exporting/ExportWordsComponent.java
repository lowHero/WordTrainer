package com.nz2dev.wordtrainer.app.presentation.modules.word.exporting;

import com.nz2dev.wordtrainer.app.common.dependencies.scopes.PerActivity;

import dagger.Subcomponent;

/**
 * Created by nz2Dev on 12.01.2018
 */
@PerActivity
@Subcomponent
public interface ExportWordsComponent {
    void inject(ExportWordsFragment exportWordsFragment);
}
