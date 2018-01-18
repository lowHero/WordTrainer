package com.nz2dev.wordtrainer.app.presentation.modules.home.elevated;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.HasDependencies;
import com.nz2dev.wordtrainer.app.presentation.modules.home.HomeFragment;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;

/**
 * Created by nz2Dev on 30.11.2017
 */
public class ElevatedHomeActivity extends AppCompatActivity implements HasDependencies<ElevatedHomeComponent> {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, ElevatedHomeActivity.class);
    }

    private ElevatedHomeComponent dependencies;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_default, Fragment.instantiate(this, HomeFragment.class.getName()))
                .commit();
    }

    @Override
    public ElevatedHomeComponent getDependencies() {
        if (dependencies == null) {
            dependencies = DependenciesUtils
                    .fromApplication(this)
                    .createElevatedHomeComponent();
        }
        return dependencies;
    }
}
