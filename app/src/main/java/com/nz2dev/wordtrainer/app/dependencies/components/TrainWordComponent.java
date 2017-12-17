package com.nz2dev.wordtrainer.app.dependencies.components;

import com.nz2dev.wordtrainer.app.dependencies.PerActivity;
import com.nz2dev.wordtrainer.app.presentation.modules.word.train.TrainWordFragment;

import dagger.Component;

/**
 * Created by nz2Dev on 16.12.2017
 */
@PerActivity
@Component(dependencies = AppComponent.class)
public interface TrainWordComponent {
    void inject(TrainWordFragment trainWordFragment);
}
