package com.nz2dev.wordtrainer.app.presentation.modules.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.dependencies.HasDependencies;
import com.nz2dev.wordtrainer.app.dependencies.components.AccountComponent;
import com.nz2dev.wordtrainer.app.dependencies.components.DaggerAccountComponent;
import com.nz2dev.wordtrainer.app.presentation.Navigator;
import com.nz2dev.wordtrainer.app.presentation.modules.account.authorization.AuthorizationFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.account.authorization.AuthorizationFragment.RegistrationProvider;
import com.nz2dev.wordtrainer.app.presentation.modules.account.registration.RegistrationFragment;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;

import javax.inject.Inject;

/**
 * Created by nz2Dev on 30.11.2017
 */
public class AccountActivity extends AppCompatActivity implements HasDependencies<AccountComponent>, RegistrationProvider {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, AccountActivity.class);
    }

    @Inject Navigator navigator;

    private AccountComponent dependencies;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
        dependencies = DaggerAccountComponent.builder()
                .appComponent(DependenciesUtils.getAppDependenciesFrom(this))
                .build();

        dependencies.inject(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_default, AuthorizationFragment.newInstance())
                .commit();
    }

    @Override
    public void startRegistration(String typedName) {
        RegistrationFragment.newInstance(typedName)
                .show(getSupportFragmentManager(), "Registration");
    }

    @Override
    public AccountComponent getDependencies() {
        return dependencies;
    }
}
