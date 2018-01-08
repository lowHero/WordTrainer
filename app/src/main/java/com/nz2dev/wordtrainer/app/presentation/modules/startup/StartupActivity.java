package com.nz2dev.wordtrainer.app.presentation.modules.startup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nz2dev.wordtrainer.app.presentation.Navigator;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;

import javax.inject.Inject;

/**
 * Created by nz2Dev on 29.11.2017
 */
public class StartupActivity extends AppCompatActivity implements StartupView {

    @Inject StartupPresenter presenter;
    @Inject Navigator navigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DependenciesUtils.appComponentFrom(this)
                .createStartupComponent()
                .inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.detachView();
    }

    @Override
    public void navigateHome() {
        navigator.navigateHomeFrom(this);
    }

    @Override
    public void navigateCourseCreation() {
        navigator.navigateCourseCreationFrom(this);
    }
}
