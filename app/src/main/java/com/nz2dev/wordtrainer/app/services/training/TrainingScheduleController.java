package com.nz2dev.wordtrainer.app.services.training;

import com.nz2dev.wordtrainer.app.services.PerService;
import com.nz2dev.wordtrainer.app.utils.TimeUtils;
import com.nz2dev.wordtrainer.domain.interactors.scheduling.PlanNextSchedulingUseCase;
import com.nz2dev.wordtrainer.domain.interactors.scheduling.StopSchedulingUseCase;
import com.nz2dev.wordtrainer.domain.interactors.training.LoadProposedTrainingUseCase;

import javax.inject.Inject;

/**
 * Created by nz2Dev on 22.12.2017
 */
@SuppressWarnings("WeakerAccess")
@PerService
public class TrainingScheduleController {

    private final PlanNextSchedulingUseCase planNextSchedulingUseCase;
    private final StopSchedulingUseCase stopSchedulingUseCase;
    private final LoadProposedTrainingUseCase loadProposedTrainingUseCase;

    private TrainingScheduleHandler handler;

    @Inject
    public TrainingScheduleController(PlanNextSchedulingUseCase planNextSchedulingUseCase, StopSchedulingUseCase stopSchedulingUseCase, LoadProposedTrainingUseCase loadProposedTrainingUseCase) {
        this.planNextSchedulingUseCase = planNextSchedulingUseCase;
        this.stopSchedulingUseCase = stopSchedulingUseCase;
        this.loadProposedTrainingUseCase = loadProposedTrainingUseCase;
    }

    public void setHandler(TrainingScheduleHandler handler) {
        this.handler = handler;
    }

    public void planeNextTraining() {
        planNextSchedulingUseCase.execute(TimeUtils.now()).subscribe(handler::scheduleNextTime);
    }

    public void prepareNextTraining() {
        // fetch data, analise it, look at restriction,
        // make exercise and remember last time in preferences or somewhere else
        // and show notification
        loadProposedTrainingUseCase.execute()
                .doFinally(handler::finishWork)
                .subscribe(handler::notifyTraining);
    }

    public void cancelSchedule() {
        stopSchedulingUseCase.execute().subscribe(r -> handler.stopSchedule());
    }

}
