package com.nz2dev.wordtrainer.app.presentation.modules.account.registration.elevated;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.HasDependencies;
import com.nz2dev.wordtrainer.app.presentation.modules.account.registration.RegistrationFragment;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;

/**
 * Created by nz2Dev on 17.01.2018
 */
public class ElevatedRegistrationActivity extends AppCompatActivity implements HasDependencies<ElevatedRegistrationComponent> {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, ElevatedRegistrationActivity.class);
    }

    private ElevatedRegistrationComponent dependencies;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_default);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_default, RegistrationFragment.newInstance("TODO"))
                .commit();
    }

    @Override
    public ElevatedRegistrationComponent getDependencies() {
        if (dependencies == null) {
            dependencies = DependenciesUtils.fromApplication(this)
                    .createElevatedRegistrationComponent();
        }
        return dependencies;
    }
}
