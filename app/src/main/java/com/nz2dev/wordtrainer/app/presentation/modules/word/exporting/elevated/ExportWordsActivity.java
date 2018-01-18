package com.nz2dev.wordtrainer.app.presentation.modules.word.exporting.elevated;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.HasDependencies;
import com.nz2dev.wordtrainer.app.presentation.modules.word.exporting.ExportWordsFragment;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;

/**
 * Created by nz2Dev on 12.01.2018
 */
public class ExportWordsActivity extends AppCompatActivity implements HasDependencies<ExportWordsComponent> {

    private final static String EXTRA_COURSE_ID = "CourseId";

    public static Intent getCallingIntent(Context context, long courseId) {
        Intent intent = new Intent(context, ExportWordsActivity.class);
        intent.putExtra(EXTRA_COURSE_ID, courseId);
        return intent;
    }

    private ExportWordsComponent dependencies;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_default, ExportWordsFragment.newInstance(getCourseIdFromIntent()))
                .commit();
    }

    @Override
    public ExportWordsComponent getDependencies() {
        if (dependencies == null) {
            dependencies = DependenciesUtils
                    .fromApplication(this)
                    .createExportWordsComponent();
        }
        return dependencies;
    }

    private long getCourseIdFromIntent() {
        return getIntent().getLongExtra(EXTRA_COURSE_ID, -1);
    }

}