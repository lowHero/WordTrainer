package com.nz2dev.wordtrainer.app.presentation.modules.courses.creation.elevated;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.WordTrainerApp;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.HasDependencies;
import com.nz2dev.wordtrainer.app.presentation.modules.courses.creation.CourseCreationFragment;

/**
 * Created by nz2Dev on 30.12.2017
 */
public class ElevatedCourseCreationActivity extends AppCompatActivity
        implements HasDependencies<ElevatedCourseCreationComponent> {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, ElevatedCourseCreationActivity.class);
    }

    private ElevatedCourseCreationComponent dependencies;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_default, CourseCreationFragment.newInstance())
                .commit();
    }

    @Override
    public ElevatedCourseCreationComponent getDependencies() {
        if (dependencies == null) {
            dependencies = WordTrainerApp
                    .getDependencies(this)
                    .createElevatedCourseCreationComponent();
        }
        return dependencies;
    }
}
