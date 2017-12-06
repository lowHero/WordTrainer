package com.nz2dev.wordtrainer.domain.execution;

import java.util.concurrent.Executor;

import io.reactivex.Scheduler;

/**
 * Created by nz2Dev on 06.12.2017
 */
public interface BackgroundExecutor extends Executor {
    Scheduler getSubscribeScheduler();
}
