package com.nz2dev.wordtrainer.app.presentation.modules.word;

import com.nz2dev.wordtrainer.app.common.scopes.ForActions;
import com.nz2dev.wordtrainer.app.presentation.modules.word.add.AddWordFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.word.edit.EditWordFragment;

import dagger.Subcomponent;

/**
 * Created by nz2Dev on 30.01.2018
 */
@ForActions
@Subcomponent
public interface WordsComponent {
    void inject(AddWordFragment fragment);
    void inject(EditWordFragment editWordFragment);
}
