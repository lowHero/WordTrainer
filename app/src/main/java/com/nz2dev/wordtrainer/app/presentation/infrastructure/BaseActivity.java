package com.nz2dev.wordtrainer.app.presentation.infrastructure;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by nz2Dev on 31.01.2018
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public final void onBackPressed() {
        boolean pressBackConsumed = false;

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof  BaseFragment) {
                if (((BaseFragment) fragment).pressBack()) {
                    pressBackConsumed = true;
                }
            }
        }

        if (!pressBackConsumed) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                pressBack();
            } else if (!onBackPressedConsuming()) {
                pressBack();
            }
        }
    }

    protected void pressBack() {
        super.onBackPressed();
    }

    protected boolean onBackPressedConsuming() {
        return false;
    }

}
