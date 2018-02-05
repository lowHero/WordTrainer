package com.nz2dev.wordtrainer.app;

import android.content.Context;

import com.nz2dev.wordtrainer.domain.device.SchedulersFacade;
import com.nz2dev.wordtrainer.domain.events.AppEventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


/**
 * Created by nz2Dev on 29.11.2017
 */
@Module
class AppModule {

    private final WordTrainerApp appInstance;

    AppModule(WordTrainerApp appInstance) {
        this.appInstance = appInstance;
    }

    @Provides
    @Singleton
    Context provideAppContext() {
        return appInstance;
    }

    @Provides
    @Singleton
    AppEventBus provideAppEventBus(SchedulersFacade schedulersFacade) {
        return new AppEventBus(schedulersFacade);
    }

}
