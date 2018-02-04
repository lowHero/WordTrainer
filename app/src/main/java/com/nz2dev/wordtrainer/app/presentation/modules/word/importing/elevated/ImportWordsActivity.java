package com.nz2dev.wordtrainer.app.presentation.modules.word.importing.elevated;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.common.WordTrainerApp;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.HasDependencies;
import com.nz2dev.wordtrainer.app.presentation.modules.word.importing.ImportWordsFragment;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.Dependencies;

/**
 * Created by nz2Dev on 11.01.2018
 */
public class ImportWordsActivity extends AppCompatActivity implements HasDependencies<ImportWordsComponent> {

    public static Intent getCallingIntent(Context context, String path) {
        Intent intent = new Intent(context, ImportWordsActivity.class);
        intent.setData(Uri.parse(path));
        return intent;
    }

    private ImportWordsComponent dependencies;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_default, ImportWordsFragment.newInstance(getPathFromIntent()))
                .commit();
    }

    @Override
    public ImportWordsComponent getDependencies() {
        if (dependencies == null) {
            dependencies = WordTrainerApp
                    .getDependencies(this)
                    .createImportWordsComponent();
        }
        return dependencies;
    }

    private String getPathFromIntent() {
        return getIntent().getData().getEncodedPath();
    }

}