package com.nz2dev.wordtrainer.app.presentation.modules.word.importing.elevated;

import com.nz2dev.wordtrainer.app.presentation.ForActions;
import com.nz2dev.wordtrainer.app.presentation.modules.word.importing.ImportWordsFragment;

import dagger.Subcomponent;

/**
 * Created by nz2Dev on 11.01.2018
 */
@ForActions
@Subcomponent
public interface ImportWordsComponent {
    void inject(ImportWordsFragment importWordsFragment);
}
