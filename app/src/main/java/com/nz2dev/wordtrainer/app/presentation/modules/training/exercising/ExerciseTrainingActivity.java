package com.nz2dev.wordtrainer.app.presentation.modules.training.exercising;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.HasDependencies;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

/**
 * Created by nz2Dev on 15.12.2017
 */
public class ExerciseTrainingActivity extends AppCompatActivity implements HasDependencies<ExerciseTrainingComponent> {

    private static final int REQUEST_CODE = 1;

    private static final String EXTRA_TRAINING_WORD_ID = "TrainingWordId";

    public static Intent getCallingIntent(Context context, long trainingWordId) {
        Intent intent = new Intent(context, ExerciseTrainingActivity.class);
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

    private ExerciseTrainingComponent dependencies;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);

        ExerciseTrainingFragment fragment = ExerciseTrainingFragment.newInstance(getTrainingWordId());
        fragment.listenDismissing(this::finish);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_default, fragment)
                .commit();
    }

    @Override
    public ExerciseTrainingComponent getDependencies() {
        if (dependencies == null) {
            dependencies = DependenciesUtils
                    .appComponentFrom(this)
                    .createExerciseTrainingComponent();
        }
        return dependencies;
    }

    private long getTrainingWordId() {
        return getIntent().getLongExtra(EXTRA_TRAINING_WORD_ID, -1L);
    }

}
