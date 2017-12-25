package com.nz2dev.wordtrainer.app.presentation.modules.startup;

import android.app.Service;
import android.content.Intent;

import com.nz2dev.wordtrainer.app.preferences.AccountPreferences;
import com.nz2dev.wordtrainer.app.presentation.Navigator;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.BasePresenter;
import com.nz2dev.wordtrainer.app.services.training.TrainingScheduleService;

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

//    TODO it can be useful to store name of services with should be started right after app starts
//    private void initAutoStartedServices() {
//        try {
//            String autoStartedServiceName = TrainingScheduleService.class.getName();
//            Class<?> clazz = Class.forName(autoStartedServiceName);
//
//            if (clazz.isAssignableFrom(Service.class)) {
//                context.startService(new Intent(context, clazz));
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
}
