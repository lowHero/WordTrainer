package com.nz2dev.wordtrainer.app.presentation.modules.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.dependencies.HasDependencies;
import com.nz2dev.wordtrainer.app.dependencies.components.DaggerHomeComponent;
import com.nz2dev.wordtrainer.app.dependencies.components.HomeComponent;
import com.nz2dev.wordtrainer.app.presentation.modules.debug.DebugFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.training.exercising.ExerciseTrainingFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.training.exercising.ExerciseTrainingFragment.ExerciseTrainingHandler;
import com.nz2dev.wordtrainer.app.presentation.modules.training.overview.OverviewTrainingsFragment.TrainingExhibitor;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;

/**
 * Created by nz2Dev on 30.11.2017
 */
public class HomeActivity extends AppCompatActivity implements HasDependencies<HomeComponent>,
        ExerciseTrainingHandler, TrainingExhibitor {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    private HomeComponent dependencies;
    private int backStackId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        dependencies = DaggerHomeComponent.builder()
                .appComponent(DependenciesUtils.getAppDependenciesFrom(this))
                .build();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_home_place, HomeFragment.newInstance())
                .commit();

        getSupportFragmentManager().beginTransaction()
                .add(DebugFragment.newInstance(), "DEBUG")
                .commit();
    }

    @Override
    public HomeComponent getDependencies() {
        return dependencies;
    }

    @Override
    public void onTrainingFinished(ExerciseTrainingFragment fragment) {
        if (getSupportFragmentManager().findFragmentById(R.id.fl_training_place) != fragment) {
            throw new RuntimeException("fragment != exerciseTrainingFragment");
        }

        getSupportFragmentManager().popBackStack(backStackId, 0);

        // TODO get homeFragment and call refreshData with Training model object?
        // or use Room LiveData for managing this behaviour
        HomeFragment homeFragment = null;
    }

    @Override
    public void showTraining(ExerciseTrainingFragment fragment) {
        backStackId = getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in_full, R.anim.fade_out_full, R.anim.fade_in_full, R.anim.fade_out_full)
                .replace(R.id.fl_training_place, fragment)
                .addToBackStack(null)
                .commit();
    }
}
