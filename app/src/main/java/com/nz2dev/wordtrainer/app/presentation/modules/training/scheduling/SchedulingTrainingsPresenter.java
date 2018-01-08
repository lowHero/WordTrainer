package com.nz2dev.wordtrainer.app.presentation.modules.training.scheduling;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;

import com.nz2dev.wordtrainer.app.common.dependencies.scopes.PerActivity;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.BasePresenter;
import com.nz2dev.wordtrainer.app.services.training.TrainingScheduleService;
import com.nz2dev.wordtrainer.domain.interactors.scheduling.ChangeSchedulingIntervalUseCase;
import com.nz2dev.wordtrainer.domain.interactors.scheduling.DownloadSchedulingUseCase;
import com.nz2dev.wordtrainer.domain.models.Scheduling;
import com.nz2dev.wordtrainer.domain.utils.Millisecond;

import javax.inject.Inject;

import static com.nz2dev.wordtrainer.app.presentation.modules.training.scheduling.SchedulingTrainingsView.UNSPECIFIED_INTERVAL;

/**
 * Created by nz2Dev on 23.12.2017
 */
@PerActivity
public class SchedulingTrainingsPresenter extends BasePresenter<SchedulingTrainingsView> {

    private static final long FUTURE_INTERVAL_UNSPECIFIED = -1L;

    private final IntentFilter ALARM_STARTED = new IntentFilter(TrainingScheduleService.ACTION_ALARM_STARTED);

    private final DownloadSchedulingUseCase downloadSchedulingUseCase;
    private final ChangeSchedulingIntervalUseCase changeSchedulingIntervalUseCase;

    private final Context context;

    private CountDownTimer timer;
    private BroadcastReceiver receiver;
    private Scheduling currentScheduling;

    private long futureInterval = FUTURE_INTERVAL_UNSPECIFIED;

    @Inject
    public SchedulingTrainingsPresenter(DownloadSchedulingUseCase downloadSchedulingUseCase, ChangeSchedulingIntervalUseCase changeSchedulingIntervalUseCase, Context context) {
        this.downloadSchedulingUseCase = downloadSchedulingUseCase;
        this.changeSchedulingIntervalUseCase = changeSchedulingIntervalUseCase;
        this.context = context;
    }

    @Override
    protected void onViewReady() {
        prepareScheduler();
        context.registerReceiver(receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                prepareTimer();
            }
        }, ALARM_STARTED);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (timer != null) {
            timer.cancel();
        }
        context.unregisterReceiver(receiver);
    }

    public void schedulingIntervalChanged(long intervalMillis) {
        if (intervalMillis < Scheduling.MIN_INTERVAL) {
            intervalMillis = Scheduling.MIN_INTERVAL;
        }

        if (futureInterval == FUTURE_INTERVAL_UNSPECIFIED) {
            getView().showIntervalChanging(true);
        }

        futureInterval = intervalMillis;
        getView().setFutureInterval(intervalMillis);
    }

    public void acceptFutureIntervalClick() {
        changeSchedulingIntervalUseCase.execute(futureInterval).subscribe(result -> {
            getView().showIntervalChanging(false);
            getView().setActualInterval(futureInterval);
            futureInterval = FUTURE_INTERVAL_UNSPECIFIED;

            if (currentScheduling.getLastTrainingDate() != null) {
                startSchedulingClick();
            }
        });
    }

    public void rejectFutureIntervalClick() {
        futureInterval = FUTURE_INTERVAL_UNSPECIFIED;
        getView().showIntervalChanging(false);
        getView().setActualInterval(currentScheduling.getInterval());
    }

    public void startSchedulingClick() {
        getView().showSchedulingLaunched();
        context.startService(TrainingScheduleService.getStartingIntent(context));
    }

    public void stopSchedulingClick() {
        if (timer != null) {
            timer.cancel();
        }

        getView().showSchedulingStopped();
        getView().showTimeLeft(UNSPECIFIED_INTERVAL);

        context.startService(TrainingScheduleService.getCancelIntent(context));
    }

    private void prepareScheduler() {
        downloadSchedulingUseCase.execute().subscribe(scheduling -> {
            currentScheduling = scheduling;
            getView().setActualInterval(currentScheduling.getInterval());

            if (scheduling.getLastTrainingDate() != null) {
                startSchedulingClick();
            } else {
                getView().showSchedulingStopped();
                getView().showTimeLeft(0L);
            }
        });
    }

    private void prepareTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        // TODO preparing Timer logic can be complicated when we would be check last training date
        // but now just start time for interval

        timer = new CountDownTimer(currentScheduling.getInterval(), Millisecond.SECOND) {
            @Override
            public void onTick(long millisUntilFinished) {
                getView().showTimeLeft(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                getView().showTimeLeft(UNSPECIFIED_INTERVAL);
            }
        };

        timer.start();
    }

}
