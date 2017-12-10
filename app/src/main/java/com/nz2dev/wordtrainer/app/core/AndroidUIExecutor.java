package com.nz2dev.wordtrainer.app.core;

import com.nz2dev.wordtrainer.domain.execution.UIExecutor;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by nz2Dev on 06.12.2017
 */
@Singleton
public class AndroidUIExecutor implements UIExecutor {

    @Inject
    public AndroidUIExecutor() {}

    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
