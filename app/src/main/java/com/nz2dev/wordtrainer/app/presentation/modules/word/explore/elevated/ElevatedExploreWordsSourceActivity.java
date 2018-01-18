package com.nz2dev.wordtrainer.app.presentation.modules.word.explore.elevated;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.HasDependencies;
import com.nz2dev.wordtrainer.app.presentation.modules.word.explore.ExploreWordsSourceFragment;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;

/**
 * Created by nz2Dev on 13.01.2018
 */
public class ElevatedExploreWordsSourceActivity extends AppCompatActivity implements HasDependencies<ElevatedExploreWordsSourceComponent> {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, ElevatedExploreWordsSourceActivity.class);
    }

    private ElevatedExploreWordsSourceComponent dependencies;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_default, ExploreWordsSourceFragment.newInstance())
                .commit();
    }

    @Override
    public ElevatedExploreWordsSourceComponent getDependencies() {
        if (dependencies == null) {
            dependencies = DependenciesUtils
                    .fromApplication(this)
                    .createElevatedExploreWordsSourceComponent();
        }
        return dependencies;
    }
}