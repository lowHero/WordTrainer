package com.nz2dev.wordtrainer.domain.device;

import io.reactivex.Scheduler;

/**
 * Created by nz2Dev on 06.12.2017
 */
public interface SchedulersFacade {

    Scheduler background();

    Scheduler ui();

}
