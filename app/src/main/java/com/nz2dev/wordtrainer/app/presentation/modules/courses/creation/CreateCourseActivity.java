package com.nz2dev.wordtrainer.app.presentation.modules.courses.creation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.dependencies.HasDependencies;
import com.nz2dev.wordtrainer.app.dependencies.components.CreateCourseComponent;
import com.nz2dev.wordtrainer.app.dependencies.components.DaggerCreateCourseComponent;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;

/**
 * Created by nz2Dev on 30.12.2017
 */
public class CreateCourseActivity extends AppCompatActivity implements HasDependencies<CreateCourseComponent> {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, CreateCourseActivity.class);
    }

    private CreateCourseComponent dependencies;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
        dependencies = DaggerCreateCourseComponent.builder()
            .appComponent(DependenciesUtils.appComponentFrom(this))
            .build();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_default, CreateCourseFragment.newInstance())
                .commit();
    }

    @Override
    public CreateCourseComponent getDependencies() {
        return dependencies;
    }
}
