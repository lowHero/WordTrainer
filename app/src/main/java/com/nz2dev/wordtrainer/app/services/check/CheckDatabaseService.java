package com.nz2dev.wordtrainer.app.services.check;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.nz2dev.wordtrainer.app.core.WordTrainerApp;
import com.nz2dev.wordtrainer.app.dependencies.components.DaggerCheckDatabaseComponent;

import javax.inject.Inject;

/**
 * Created by nz2Dev on 12.12.2017
 */
public class CheckDatabaseService extends IntentService {

    private static final String TAG = CheckDatabaseService.class.getSimpleName();

    @Inject CheckDatabaseWorker worker;

    public CheckDatabaseService() {
        super(CheckDatabaseService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent");
        initDependencies();
        worker.check(() -> WakefulBroadcastReceiver.completeWakefulIntent(intent));
    }

    private void initDependencies() {
        DaggerCheckDatabaseComponent.builder()
                .appComponent(((WordTrainerApp) getApplicationContext()).getDependenciesComponent())
                .build()
                .inject(this);
    }
}
