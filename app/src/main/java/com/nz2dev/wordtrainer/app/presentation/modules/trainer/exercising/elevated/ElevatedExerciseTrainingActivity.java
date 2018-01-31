package com.nz2dev.wordtrainer.app.presentation.modules.trainer.exercising.elevated;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.HasDependencies;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.exercising.ExerciseTrainingFragment;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.Dependencies;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

/**
 * Created by nz2Dev on 15.12.2017
 */
public class ElevatedExerciseTrainingActivity extends AppCompatActivity
        implements HasDependencies<ElevatedExerciseTrainingComponent> {

    private static final int REQUEST_CODE = 1;

    private static final String EXTRA_TRAINING_WORD_ID = "TrainingWordId";

    public static Intent getCallingIntent(Context context, long trainingWordId) {
        Intent intent = new Intent(context, ElevatedExerciseTrainingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(EXTRA_TRAINING_WORD_ID, trainingWordId);
        return intent;
    }

    public static PendingIntent getPendingIntent(Context context, long trainingWordId) {
        return PendingIntent.getActivity(context,
                REQUEST_CODE,
                getCallingIntent(context, trainingWordId),
                FLAG_UPDATE_CURRENT);
    }

    private ElevatedExerciseTrainingComponent dependencies;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_default, ExerciseTrainingFragment.newInstance(getTrainingWordId()))
                .commit();
    }

    @Override
    public ElevatedExerciseTrainingComponent getDependencies() {
        if (dependencies == null) {
            dependencies = Dependencies
                    .fromApplication(getApplicationContext())
                    .createElevatedExerciseTrainingComponent();
        }
        return dependencies;
    }

    private long getTrainingWordId() {
        return getIntent().getLongExtra(EXTRA_TRAINING_WORD_ID, -1L);
    }

}
