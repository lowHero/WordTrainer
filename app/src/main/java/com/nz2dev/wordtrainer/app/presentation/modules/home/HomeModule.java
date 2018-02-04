package com.nz2dev.wordtrainer.app.presentation.modules.home;

import com.nz2dev.wordtrainer.app.common.scopes.ForActionsContainers;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nz2Dev on 16.01.2018
 */
@Module
public class HomeModule {

    private HomeFragment homeFragment;

    HomeModule(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
    }

    @Provides
    @ForActionsContainers
    HomeNavigator provideHomeNavigator() {
        return homeFragment;
    }

}
