package com.nz2dev.wordtrainer.app.presentation.modules.trainer.exercising.elevated;

import com.nz2dev.wordtrainer.app.presentation.ForActions;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.TrainerNavigation;
import com.nz2dev.wordtrainer.app.utils.generic.Optional;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nz2Dev on 17.01.2018
 */
@Module
public class NavigationModule {

    @Provides
    @ForActions
    Optional<TrainerNavigation> provideTrainerNavigation() {
        return new Optional<>(null);
    }

}
