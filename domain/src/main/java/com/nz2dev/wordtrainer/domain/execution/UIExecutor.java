package com.nz2dev.wordtrainer.domain.execution;

import io.reactivex.Scheduler;

/**
 * Created by nz2Dev on 06.12.2017
 */
public interface UIExecutor {
    Scheduler getScheduler();
}
