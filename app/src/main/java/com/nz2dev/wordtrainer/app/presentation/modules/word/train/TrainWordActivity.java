package com.nz2dev.wordtrainer.app.presentation.modules.word.train;

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
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

/**
 * Created by nz2Dev on 15.12.2017
 */
public class TrainWordActivity extends AppCompatActivity implements HasDependencies<TrainWordComponent> {

    private static final int REQUEST_CODE = 1;

    private static final String EXTRA_TRAINING_ID = "TrainingId";

    public static Intent getCallingIntent(Context context, int trainingId) {
        Intent intent = new Intent(context, TrainWordActivity.class);
        intent.putExtra(EXTRA_TRAINING_ID, trainingId);
        return intent;
    }

    public static PendingIntent getPendingIntent(Context context, int trainingId) {
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
                .replace(R.id.fl_default, TrainWordFragment.newInstance(getTrainingId()))
                .commit();
    }

    @Override
    public TrainWordComponent getDependencies() {
        return dependencies;
    }

    private int getTrainingId() {
        return getIntent().getIntExtra(EXTRA_TRAINING_ID, -1);
    }
}
