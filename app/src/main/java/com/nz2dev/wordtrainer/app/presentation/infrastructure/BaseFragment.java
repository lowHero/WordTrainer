package com.nz2dev.wordtrainer.app.presentation.infrastructure;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by nz2Dev on 31.01.2018
 */
public abstract class BaseFragment extends Fragment {

    @CallSuper
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof BaseActivity)) {
            Log.w(getClass().getSimpleName(), "Fragment isn't attached to BaseActivity " +
                    "that why onBackPressed will not be called when its happened");
        }
        if (getParentFragment() != null && !(getParentFragment() instanceof BaseFragment)) {
            Log.w(getClass().getSimpleName(), "Fragment isn't added to BaseFragment " +
                    "that why onBackPressed will not be called when its happened");
        }
    }

    final boolean pressBack() {
        boolean backPressConsumed = false;
        for (Fragment fragment : getChildFragmentManager().getFragments()) {
            if (fragment instanceof BaseFragment && fragment.isVisible()) {
                if (((BaseFragment) fragment).pressBack()) {
                    backPressConsumed = true;
                }
            }
        }

        if (!backPressConsumed && onBackPressed()) {
            backPressConsumed = true;
        }
        return backPressConsumed;
    }

    protected boolean onBackPressed() {
        return false;
    }

}
