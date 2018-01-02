package com.nz2dev.wordtrainer.app.dependencies.modules;

import android.content.Context;

import com.nz2dev.wordtrainer.app.core.AndroidBackgroundExecutor;
import com.nz2dev.wordtrainer.app.core.AndroidExceptionHandler;
import com.nz2dev.wordtrainer.app.core.AndroidUIExecutor;
import com.nz2dev.wordtrainer.app.core.WordTrainerApp;
import com.nz2dev.wordtrainer.domain.exceptions.ExceptionHandler;
import com.nz2dev.wordtrainer.domain.execution.BackgroundExecutor;
import com.nz2dev.wordtrainer.domain.execution.UIExecutor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nz2Dev on 29.11.2017
 */
@Module
public class AppModule {

    private final WordTrainerApp appInstance;

    public AppModule(WordTrainerApp appInstance) {
        this.appInstance = appInstance;
    }

    @Provides
    @Singleton
    Context provideAppContext() {
        return appInstance;
    }

    @Provides
    @Singleton
    BackgroundExecutor provideBackgroundExecutor(AndroidBackgroundExecutor backgroundExecutor) {
        return backgroundExecutor;
    }

    @Provides
    @Singleton
    UIExecutor provideUiExecutor(AndroidUIExecutor uiExecutor) {
        return uiExecutor;
    }

    @Provides
    @Singleton ExceptionHandler provideExceptionHandler(AndroidExceptionHandler exceptionHandler) {
        return exceptionHandler;
    }

}
