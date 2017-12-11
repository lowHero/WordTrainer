package com.nz2dev.wordtrainer.app.dependencies.components;

import com.nz2dev.wordtrainer.app.dependencies.PerActivity;
import com.nz2dev.wordtrainer.app.presentation.modules.word.add.AddWordFragment;

import dagger.Component;

/**
 * Created by nz2Dev on 11.12.2017
 */
@PerActivity
@Component(dependencies = AppComponent.class)
public interface AddWordComponent {
    void inject(AddWordFragment addWordFragment);
}
