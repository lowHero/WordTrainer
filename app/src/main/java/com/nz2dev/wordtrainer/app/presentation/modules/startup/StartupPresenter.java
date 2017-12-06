package com.nz2dev.wordtrainer.app.presentation.modules.startup;

import com.nz2dev.wordtrainer.app.preferences.AccountPreferences;
import com.nz2dev.wordtrainer.app.presentation.Navigator;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.BasePresenter;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by nz2Dev on 30.11.2017
 */
@Singleton
public class StartupPresenter extends BasePresenter<StartupView> {

    private static final long SPLASH_DELAY_MS = 500;

    private AccountPreferences accountPreferences;

    @Inject
    public StartupPresenter(AccountPreferences accountPreferences) {
        this.accountPreferences = accountPreferences;
    }

    @Override
    protected void onViewReady() {
        super.onViewReady();
        Single.timer(SPLASH_DELAY_MS, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Long>() {
                    @Override
                    public void onSuccess(Long aLong) {
                        if (accountPreferences.isSignIn()) {
                            getView().navigateHome();
                        } else {
                            getView().navigateAccount();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // I guess, it will never cause
                    }
                });
    }
}
