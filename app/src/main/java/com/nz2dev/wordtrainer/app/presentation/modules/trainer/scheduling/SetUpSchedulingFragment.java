package com.nz2dev.wordtrainer.app.presentation.modules.trainer.scheduling;

import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.TrainerFragment;
import com.nz2dev.wordtrainer.app.utils.AnimationsUtils;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.Dependencies;
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
public class SetUpSchedulingFragment extends Fragment implements SetUpSchedulingView {

    public static final String TAG = SetUpSchedulingFragment.class.getSimpleName();

    public static SetUpSchedulingFragment newInstance() {
        return new SetUpSchedulingFragment();
    }

    @BindView(R.id.cl_future_changes_container)
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

    @Inject SetUpSchedulingPresenter presenter;

    private long intervalMultiplier = 15 * Millisecond.MINUTE;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dependencies.fromParentFragment(this, TrainerFragment.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trainer_set_up_scheduling, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        updateSeekBar();

        trainingIntervalBar.setOnSeekBarChangeListener(new OnSeekBarChangeAdapter() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
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

//    @OnClick(R.id.btn_interval_multiplier_hour)
//    public void onHourMultiplierClick() {
//        intervalMultiplier = Millisecond.HOUR;
//        updateSeekBar();
//    }
//
//    @OnClick(R.id.btn_interval_multiplier_15min)
//    public void onFifteenMinutesMultiplierClick() {
//        intervalMultiplier = 15 * Millisecond.MINUTE;
//        updateSeekBar();
//    }

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
        } else {
            color = ContextCompat.getColor(getContext(), R.color.secondaryColor);
        }

        trainingIntervalBar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            trainingIntervalBar.getThumb().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
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
    }

    private void updateSeekBar() {
        if (trainingIntervalBar.getProgress() > intervalMultiplier) {
            presenter.schedulingIntervalChanged(intervalMultiplier);
        }
        trainingIntervalBar.setMax((int) intervalMultiplier);
    }

}
