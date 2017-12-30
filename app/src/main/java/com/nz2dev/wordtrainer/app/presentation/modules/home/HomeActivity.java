package com.nz2dev.wordtrainer.app.presentation.modules.home;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.dependencies.HasDependencies;
import com.nz2dev.wordtrainer.app.dependencies.components.DaggerHomeComponent;
import com.nz2dev.wordtrainer.app.dependencies.components.HomeComponent;
import com.nz2dev.wordtrainer.app.presentation.modules.courses.overview.CoursesOverviewFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.debug.DebugFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.TrainerFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.TrainerFragment.WordAdditionExhibitor;
import com.nz2dev.wordtrainer.app.presentation.modules.training.exercising.ExerciseTrainingFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.training.exercising.ExerciseTrainingFragment.ExerciseTrainingHandler;
import com.nz2dev.wordtrainer.app.presentation.modules.training.overview.OverviewTrainingsFragment.TrainingExhibitor;
import com.nz2dev.wordtrainer.app.presentation.modules.word.add.AddWordFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.word.add.AddWordFragment.AddWordHandler;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;
import static android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS;

/**
 * Created by nz2Dev on 30.11.2017
 */
public class HomeActivity extends AppCompatActivity implements HasDependencies<HomeComponent>,
        ExerciseTrainingHandler, TrainingExhibitor,
        AddWordHandler, WordAdditionExhibitor {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    private HomeComponent dependencies;
    private int backStackIdExercise;
    private int backStackIdAdding;

    @BindView(R.id.fl_adding_word_place)
    FrameLayout addingPlace;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

        dependencies = DaggerHomeComponent.builder()
                .appComponent(DependenciesUtils.appComponentFrom(this))
                .build();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_trainer_place, TrainerFragment.newInstance())
                .replace(R.id.fl_navigation_place, CoursesOverviewFragment.newInstance())
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

        // TODO Should I check and remove fragment from FragmentManager and commit in this case
        // or just popBackStack?
        getSupportFragmentManager().popBackStack(backStackIdExercise, POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void onWordAdditionFinished(AddWordFragment fragment) {
        animateBackground(addingPlace, ContextCompat.getColor(this, R.color.backgroundTransparentHard), 0, 300);
        getSupportFragmentManager().popBackStack(backStackIdAdding, POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void showTraining(Fragment fragment) {
        backStackIdExercise = getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in_full, R.anim.fade_out_full, R.anim.fade_in_full, R.anim.fade_out_full)
                .replace(R.id.fl_training_place, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showWordAddition(Fragment fragment) {
        animateBackground(addingPlace, 0, ContextCompat.getColor(this, R.color.backgroundTransparentHard), 300);
        backStackIdAdding = getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in_full, R.anim.slide_up, R.anim.fade_in_full, R.anim.slide_up)
                .replace(R.id.fl_adding_word_place, fragment)
                .addToBackStack(null)
                .commit();
    }

    private static void animateBackground(View view, int colorFrom, int colorTo, int duration) {
        ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        animator.addUpdateListener(animation -> view.setBackgroundColor((Integer) animation.getAnimatedValue()));
        animator.setDuration(duration);
        animator.start();
    }
}
