package com.nz2dev.wordtrainer.app.presentation.modules.settings.scheduling;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.modules.home.HomeActivity;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by nz2Dev on 23.12.2017
 */
public class SchedulingSettingsFragment extends Fragment implements SchedulingSettingsView {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

    static {
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public static SchedulingSettingsFragment newInstance() {
        return new SchedulingSettingsFragment();
    }

    @BindView(R.id.tv_next_training_time)
    TextView nextTrainingTime;

    @BindView(R.id.btn_scheduling_launcher)
    Button schedulingLauncher;

    @BindView(R.id.sb_training_interval)
    SeekBar trainingInterval;

    @Inject SchedulingSettingsPresenter presenter;

    private boolean launched = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings_scheduling, container, false);
        ButterKnife.bind(this, root);
        trainingInterval.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                presenter.schedulingIntervalChanged(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // no-op
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // no-op
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.setView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @OnClick(R.id.btn_send_pending)
    public void onSendPendingClick() {
        presenter.sendPendingClick();
    }

    @OnClick(R.id.btn_send_calling)
    public void onSendCallingClick() {
        presenter.sendCallingClick();
    }

    @OnClick(R.id.btn_scheduling_launcher)
    public void onLauncherClick() {
        if (launched) {
            presenter.stopSchedulingClick();
        } else {
            presenter.startSchedulingClick();
        }
    }

    @Override
    public void showNextTrainingUnspecified() {
        nextTrainingTime.setText(R.string.error_next_time_unspecified);
    }

    @Override
    public void showSchedulingLaunched() {
        schedulingLauncher.setText(R.string.action_stop_scheduling);
        launched = true;
    }

    @Override
    public void showSchedulingStopped() {
        schedulingLauncher.setText(R.string.action_start_scheduling);
        launched = false;
    }

    @Override
    public void setTimeReminded(long millisUntilFinished) {
        nextTrainingTime.setText(DATE_FORMAT.format(new Date(millisUntilFinished)));
    }

    @Override
    public void setMaxInterval(int maxIntervalMinutes) {
        trainingInterval.setMax(maxIntervalMinutes);
    }

    private void injectDependencies() {
        if (getActivity() instanceof HomeActivity) {
            DependenciesUtils.fromAttachedActivity(this, HomeActivity.class).inject(this);
        }
    }
}
