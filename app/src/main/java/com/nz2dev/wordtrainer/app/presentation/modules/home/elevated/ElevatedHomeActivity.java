package com.nz2dev.wordtrainer.app.presentation.modules.home.elevated;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.common.WordTrainerApp;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.BaseActivity;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.HasDependencies;
import com.nz2dev.wordtrainer.app.presentation.modules.home.HomeFragment;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.Dependencies;

/**
 * Created by nz2Dev on 30.11.2017
 */
public class ElevatedHomeActivity extends BaseActivity implements HasDependencies<ElevatedHomeComponent> {

    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, ElevatedHomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
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
    protected boolean onBackPressedConsuming() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.question_exit)
                .setPositiveButton(R.string.action_agree, (dialog, which) -> {
                    pressBack();
                })
                .setNegativeButton(R.string.action_disagree, (dialog, which) -> {
                    dialog.dismiss();
                })
                .create()
                .show();
        return true;
    }

    @Override
    public ElevatedHomeComponent getDependencies() {
        if (dependencies == null) {
            dependencies = WordTrainerApp
                    .getDependencies(this)
                    .createElevatedHomeComponent();
        }
        return dependencies;
    }
}
