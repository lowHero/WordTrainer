package com.nz2dev.wordtrainer.app.presentation.modules.trainer;

import com.nz2dev.wordtrainer.app.common.scopes.ForActions;
import com.nz2dev.wordtrainer.app.utils.generic.Optional;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nz2Dev on 16.01.2018
 */
@ForActions
@Module
public class TrainerModule {

    private TrainerFragment trainerFragment;

    TrainerModule(TrainerFragment trainerFragment) {
        this.trainerFragment = trainerFragment;
    }

    @Provides
    @ForActions
    Optional<TrainerNavigation> provideTrainerNavigation() {
        return new Optional<>(trainerFragment);
    }

}
