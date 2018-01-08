package com.nz2dev.wordtrainer.device.execution;

import android.support.annotation.NonNull;

import com.nz2dev.wordtrainer.domain.execution.ExecutionProxy;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by nz2Dev on 04.01.2018
 */
@Singleton
public class AndroidExecution implements ExecutionProxy, Executor {

    private final ThreadPoolExecutor mThreadPoolExecutor;

    @Inject
    public AndroidExecution() {
        mThreadPoolExecutor = new ThreadPoolExecutor(4, 10, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }

    @Override
    public void execute(@NonNull Runnable runnable) {
        mThreadPoolExecutor.execute(runnable);
    }

    @Override
    public Scheduler background() {
        return Schedulers.from(this);
    }

    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

}
