package com.nz2dev.wordtrainer.app.presentation.modules.training.scheduling;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.modules.home.HomeActivity;
import com.nz2dev.wordtrainer.app.utils.AnimationsUtils;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;
import com.nz2dev.wordtrainer.app.utils.TimeUtils;
import com.nz2dev.wordtrainer.app.utils.defaults.OnSeekBarChangeAdapter;
import com.nz2dev.wordtrainer.domain.utils.Millisecond;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by nz2Dev on 23.12.2017
 */
public class SchedulingTrainingsFragment extends Fragment implements SchedulingTrainingsView {

    public static SchedulingTrainingsFragment newInstance() {
        return new SchedulingTrainingsFragment();
    }

    @BindView(R.id.rl_future_changes_container)
    View futureContainer;

    @BindView(R.id.tv_interval_future)
    TextView futureIntervalTimeText;

    @BindView(R.id.tv_interval_actual)
    TextView actualIntervalTimeText;

    @BindView(R.id.tv_next_training_time)
    TextView nextTrainingTimeLeftText;

    @BindView(R.id.btn_scheduling_launch)
    View schedulingLauncher;

    @BindView(R.id.btn_scheduling_stop)
    View schedulingStopper;

    @BindView(R.id.sb_interval)
    SeekBar trainingIntervalBar;

    @Inject SchedulingTrainingsPresenter presenter;

    private long intervalMultiplier = 15 * Millisecond.MINUTE;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_trainings_scheduling, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        trainingIntervalBar.setMax((int) intervalMultiplier);
        trainingIntervalBar.setOnSeekBarChangeListener(new OnSeekBarChangeAdapter() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
//                    presenter.schedulingIntervalChanged((long) ((progress / 100f) * intervalMultiplier));
                    presenter.schedulingIntervalChanged(progress);
                }
            }
        });

        presenter.setView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @OnClick(R.id.btn_interval_multiplier_hour)
    public void onHourMultiplierClick() {
        intervalMultiplier = Millisecond.HOUR;

        if (trainingIntervalBar.getProgress() > intervalMultiplier) {
            presenter.schedulingIntervalChanged(intervalMultiplier);
        }

        trainingIntervalBar.setMax((int) intervalMultiplier);
    }

    @OnClick(R.id.btn_interval_multiplier_15min)
    public void onFifteenMinutesMultiplierClick() {
        intervalMultiplier = 15 * Millisecond.MINUTE;

        if (trainingIntervalBar.getProgress() > intervalMultiplier) {
            presenter.schedulingIntervalChanged(intervalMultiplier);
        }

        trainingIntervalBar.setMax((int) intervalMultiplier);
    }

    @OnClick(R.id.btn_scheduling_launch)
    public void onLauncherClick() {
        presenter.startSchedulingClick();
    }

    @OnClick(R.id.btn_scheduling_stop)
    public void onStopClick() {
        presenter.stopSchedulingClick();
    }

    @OnClick(R.id.btn_accept)
    public void onAcceptFutureClick() {
        presenter.acceptFutureIntervalClick();
    }

    @OnClick(R.id.btn_reject)
    public void onRejectFutureClick() {
        presenter.rejectFutureIntervalClick();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSchedulingLaunched() {
        if (schedulingStopper.getVisibility() != View.VISIBLE) {
            AnimationsUtils.animateToVisibleShort(schedulingStopper);
            AnimationsUtils.animateToInvisibleShort(schedulingLauncher);
        }
    }

    @Override
    public void showSchedulingStopped() {
        if (schedulingLauncher.getVisibility() != View.VISIBLE) {
            AnimationsUtils.animateToVisibleShort(schedulingLauncher);
            AnimationsUtils.animateToInvisibleShort(schedulingStopper);
        }
    }

    @Override
    public void showIntervalChanging(boolean show) {
        futureContainer.setVisibility(show ? View.VISIBLE : View.INVISIBLE);

        int color;
        if (show) {
            color = ContextCompat.getColor(getContext(), R.color.primaryColor);
            trainingIntervalBar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        } else {
            color = ContextCompat.getColor(getContext(), R.color.secondaryColor);
        }
        trainingIntervalBar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void showTimeLeft(long millisUntilFinished) {
        if (millisUntilFinished == UNSPECIFIED_INTERVAL) {
            nextTrainingTimeLeftText.setText(R.string.error_next_time_unspecified);
        } else {
            nextTrainingTimeLeftText.setText(TimeUtils.formatTimer(millisUntilFinished));
        }
    }

    @Override
    public void setFutureInterval(long intervalMillis) {
        futureIntervalTimeText.setText(String.format("%s min.", intervalMillis / Millisecond.MINUTE));
    }

    @Override
    public void setActualInterval(long intervalMillis) {
        actualIntervalTimeText.setText(String.format("%s min.", intervalMillis / Millisecond.MINUTE));
        trainingIntervalBar.setProgress((int) intervalMillis);

        // because actual interval called when we want to set in once and show what is actual stored in scheduling
        // so there we also set out progressBar progress value based on multiplier value
        // finally, simply divide interval on multiplier
        // trainingIntervalBar.setProgress((int) (((double) intervalMillis / intervalMultiplier) * 100));
    }

    private void injectDependencies() {
        if (getActivity() instanceof HomeActivity) {
            DependenciesUtils.fromAttachedActivity(this, HomeActivity.class).inject(this);
        }
    }

}
