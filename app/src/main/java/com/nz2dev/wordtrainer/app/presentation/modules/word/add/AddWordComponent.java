package com.nz2dev.wordtrainer.app.presentation.modules.word.add;

import com.nz2dev.wordtrainer.app.presentation.ForActions;

import dagger.Subcomponent;

/**
 * Created by nz2Dev on 06.02.2018
 */
@ForActions
@Subcomponent
public interface AddWordComponent {
    void inject(AddWordFragment fragment);
}
