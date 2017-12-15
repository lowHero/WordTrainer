package com.nz2dev.wordtrainer.app.dependencies.components;

import android.content.Context;

import com.nz2dev.wordtrainer.app.dependencies.modules.AppModule;
import com.nz2dev.wordtrainer.app.dependencies.modules.DataModule;
import com.nz2dev.wordtrainer.app.preferences.AccountPreferences;
import com.nz2dev.wordtrainer.app.presentation.Navigator;
import com.nz2dev.wordtrainer.app.presentation.modules.startup.StartupActivity;
import com.nz2dev.wordtrainer.data.core.WordTrainerDatabase;
import com.nz2dev.wordtrainer.domain.execution.BackgroundExecutor;
import com.nz2dev.wordtrainer.domain.execution.ExecutionManager;
import com.nz2dev.wordtrainer.domain.execution.UIExecutor;
import com.nz2dev.wordtrainer.domain.interactors.AccountHistoryInteractor;
import com.nz2dev.wordtrainer.domain.interactors.AccountInteractor;
import com.nz2dev.wordtrainer.domain.interactors.TrainerInteractor;
import com.nz2dev.wordtrainer.domain.interactors.WordInteractor;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by nz2Dev on 29.11.2017
 */
@Singleton
@Component(modules = {AppModule.class, DataModule.class})
public interface AppComponent {
    void inject(StartupActivity startupActivity);

    Context appContext();
    UIExecutor uiExecutor();
    BackgroundExecutor backgroundExecutor();

    WordTrainerDatabase database();
    AccountPreferences accountPreferences();
    Navigator navigator();

    TrainerInteractor trainerInteractor();
    WordInteractor wordInteractor();
    AccountInteractor accountInteractor();
    AccountHistoryInteractor accountHistoryInteractor();
}
