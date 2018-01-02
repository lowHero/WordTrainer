package com.nz2dev.wordtrainer.app.presentation.modules.training.exercising;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.dependencies.HasDependencies;
import com.nz2dev.wordtrainer.app.dependencies.components.DaggerTrainWordComponent;
import com.nz2dev.wordtrainer.app.dependencies.components.TrainWordComponent;
import com.nz2dev.wordtrainer.app.presentation.modules.training.exercising.ExerciseTrainingFragment.ExerciseTrainingHandler;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

/**
 * Created by nz2Dev on 15.12.2017
 */
public class ExerciseTrainingActivity extends AppCompatActivity implements HasDependencies<TrainWordComponent>, ExerciseTrainingHandler {

    private static final int REQUEST_CODE = 1;

    private static final String EXTRA_TRAINING_ID = "TrainingId";

    public static Intent getCallingIntent(Context context, long trainingId) {
        Intent intent = new Intent(context, ExerciseTrainingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(EXTRA_TRAINING_ID, trainingId);
        return intent;
    }

    public static PendingIntent getPendingIntent(Context context, long trainingId) {
        return PendingIntent.getActivity(context,
                REQUEST_CODE,
                getCallingIntent(context, trainingId),
                FLAG_UPDATE_CURRENT);
    }

    private TrainWordComponent dependencies;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
        dependencies = DaggerTrainWordComponent.builder()
            .appComponent(DependenciesUtils.appComponentFrom(this))
            .build();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_default, ExerciseTrainingFragment.newInstance(getTrainingId()))
                .commit();
    }

    @Override
    public TrainWordComponent getDependencies() {
        return dependencies;
    }

    private long getTrainingId() {
        return getIntent().getLongExtra(EXTRA_TRAINING_ID, -1L);
    }

    @Override
    public void onTrainingFinished(ExerciseTrainingFragment fragment) {
        finish();
    }
}
