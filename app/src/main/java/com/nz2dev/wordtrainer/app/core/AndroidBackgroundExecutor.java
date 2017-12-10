package com.nz2dev.wordtrainer.app.core;

import android.support.annotation.NonNull;

import com.nz2dev.wordtrainer.domain.execution.BackgroundExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by nz2Dev on 06.12.2017
 */
@Singleton
public class AndroidBackgroundExecutor implements BackgroundExecutor, Executor {

    private final ThreadPoolExecutor mThreadPoolExecutor;

    @Inject
    public AndroidBackgroundExecutor() {
        mThreadPoolExecutor = new ThreadPoolExecutor(4, 10, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }

    @Override
    public Scheduler getScheduler() {
        return Schedulers.from(this);
    }

    @Override
    public void execute(@NonNull Runnable runnable) {
        mThreadPoolExecutor.execute(runnable);
    }
}
