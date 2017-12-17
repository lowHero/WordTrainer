package com.nz2dev.wordtrainer.app.presentation.modules.word.train;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.dependencies.HasDependencies;
import com.nz2dev.wordtrainer.app.dependencies.components.DaggerTrainWordComponent;
import com.nz2dev.wordtrainer.app.dependencies.components.TrainWordComponent;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;

/**
 * Created by nz2Dev on 15.12.2017
 */
public class TrainWordActivity extends AppCompatActivity implements HasDependencies<TrainWordComponent> {

    private TrainWordComponent dependencies;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
        dependencies = DaggerTrainWordComponent.builder()
            .appComponent(DependenciesUtils.getAppDependenciesFrom(this))
            .build();

        addFragments();
    }

    private void addFragments() {
        // TODO add appropriate fragment
    }

    @Override
    public TrainWordComponent getDependencies() {
        return dependencies;
    }
}
