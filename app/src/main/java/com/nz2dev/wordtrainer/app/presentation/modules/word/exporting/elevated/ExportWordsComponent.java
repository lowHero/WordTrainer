package com.nz2dev.wordtrainer.app.presentation.modules.word.exporting.elevated;

import com.nz2dev.wordtrainer.app.presentation.ForActions;
import com.nz2dev.wordtrainer.app.presentation.modules.word.exporting.ExportWordsFragment;

import dagger.Subcomponent;

/**
 * Created by nz2Dev on 12.01.2018
 */
@ForActions
@Subcomponent
public interface ExportWordsComponent {
    void inject(ExportWordsFragment exportWordsFragment);
}
