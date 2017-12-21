package com.nz2dev.wordtrainer.app.dependencies.components;

import com.nz2dev.wordtrainer.app.dependencies.PerActivity;
import com.nz2dev.wordtrainer.app.presentation.modules.home.HomeFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.word.add.AddWordFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.word.train.TrainWordFragment;

import dagger.Component;

/**
 * Created by nz2Dev on 30.11.2017
 */
@PerActivity
@Component(dependencies = AppComponent.class)
public interface HomeComponent {
    void inject(HomeFragment homeFragment);
    void inject(AddWordFragment addWordFragment);
    void inject(TrainWordFragment trainWordFragment);
}
