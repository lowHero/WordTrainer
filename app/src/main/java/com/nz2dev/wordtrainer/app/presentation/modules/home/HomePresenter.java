package com.nz2dev.wordtrainer.app.presentation.modules.home;

import android.content.Context;
import android.content.Intent;

import com.nz2dev.wordtrainer.app.dependencies.PerActivity;
import com.nz2dev.wordtrainer.app.preferences.AccountPreferences;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.BasePresenter;
import com.nz2dev.wordtrainer.app.services.AlarmService;
import com.nz2dev.wordtrainer.app.services.check.CheckDatabaseService;
import com.nz2dev.wordtrainer.app.utils.ErrorHandler;
import com.nz2dev.wordtrainer.app.utils.UncheckedObserver;
import com.nz2dev.wordtrainer.domain.interactors.WordInteractor;
import com.nz2dev.wordtrainer.domain.models.Word;

import java.util.Collection;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by nz2Dev on 30.11.2017
 */
@PerActivity
public class HomePresenter extends BasePresenter<HomeView> {

    private static final long HALF_MINUTE = 30 * 1000;

    private final WordInteractor wordInteractor;
    private final AccountPreferences accountPreferences;
    private final Context appContext;

    private int accountId;

    @Inject
    public HomePresenter(WordInteractor wordInteractor, AccountPreferences accountPreferences, Context appContext) {
        this.wordInteractor = wordInteractor;
        this.accountPreferences = accountPreferences;
        this.accountId = accountPreferences.getSignedAccountId();
        this.appContext = appContext;
    }

    @Override
    protected void onViewReady() {
        super.onViewReady();
        wordInteractor.loadWords(accountId, new DisposableSingleObserver<Collection<Word>>() {
            @Override
            public void onSuccess(Collection<Word> words) {
                getView().showWords(words);
            }
            @Override
            public void onError(Throwable e) {
                getView().showError(ErrorHandler.describe(e));
            }
        });

        wordInteractor.attachRepoObserver(new UncheckedObserver<Collection<Word>>() {
            @Override
            public void onNext(Collection<Word> words) {
                getView().showWords(words);
            }
        });
    }

    public void addWordClick() {
        getView().navigateWordAdding();
    }

    public void signOutSelected() {
        accountPreferences.signOut();
        getView().navigateAccount();
    }

    public void startTestSchedule() {
        Intent serviceIntent = new Intent(appContext, AlarmService.class);
        serviceIntent.putExtra(AlarmService.EXTRA_START_ALARM, true);
        appContext.startService(serviceIntent);
    }

    public void stopTestSchedule() {
        Intent serviceIntent = new Intent(appContext, AlarmService.class);
        serviceIntent.putExtra(AlarmService.EXTRA_START_ALARM, false);
        appContext.startService(serviceIntent);
    }

    public void manualCallService() {
        Intent serviceIntent = new Intent(appContext, CheckDatabaseService.class);
        appContext.startService(serviceIntent);
    }
}
