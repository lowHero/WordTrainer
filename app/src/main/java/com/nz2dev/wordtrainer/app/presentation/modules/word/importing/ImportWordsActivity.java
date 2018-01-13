package com.nz2dev.wordtrainer.app.presentation.modules.word.importing;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.HasDependencies;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;
import com.nz2dev.wordtrainer.domain.exceptions.NotImplementedException;

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
            dependencies = DependenciesUtils
                    .appComponentFrom(this)
                    .createImportWordsComponent();
        }
        return dependencies;
    }

    private String getPathFromIntent() {
        return getIntent().getData().getEncodedPath();
    }

}