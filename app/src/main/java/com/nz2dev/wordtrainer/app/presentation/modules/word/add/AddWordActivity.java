package com.nz2dev.wordtrainer.app.presentation.modules.word.add;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.dependencies.HasDependencies;
import com.nz2dev.wordtrainer.app.dependencies.components.AddWordComponent;
import com.nz2dev.wordtrainer.app.dependencies.components.DaggerAddWordComponent;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;

/**
 * Created by nz2Dev on 11.12.2017
 */
public class AddWordActivity extends AppCompatActivity implements HasDependencies<AddWordComponent> {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, AddWordActivity.class);
    }

    private AddWordComponent dependencies;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
        dependencies = DaggerAddWordComponent.builder()
                .appComponent(DependenciesUtils.getAppDependenciesFrom(this))
                .build();
    }

    @Override
    public AddWordComponent getDependencies() {
        return dependencies;
    }
}
