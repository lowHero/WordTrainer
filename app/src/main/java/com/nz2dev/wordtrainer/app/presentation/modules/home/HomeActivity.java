package com.nz2dev.wordtrainer.app.presentation.modules.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.dependencies.HasDependencies;
import com.nz2dev.wordtrainer.app.dependencies.components.DaggerHomeComponent;
import com.nz2dev.wordtrainer.app.dependencies.components.HomeComponent;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;

/**
 * Created by nz2Dev on 30.11.2017
 */
public class HomeActivity extends AppCompatActivity implements HasDependencies<HomeComponent> {

    private HomeComponent dependencies;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dependencies = DaggerHomeComponent.builder()
                .appComponent(DependenciesUtils.getAppDependenciesFrom(this))
                .build();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_default, HomeFragment.newInstance())
                .commit();
    }

    @Override
    public HomeComponent getDependencies() {
        return dependencies;
    }
}
