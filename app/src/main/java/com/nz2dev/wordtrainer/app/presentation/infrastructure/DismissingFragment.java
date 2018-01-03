package com.nz2dev.wordtrainer.app.presentation.infrastructure;

import android.support.v4.app.Fragment;

/**
 * Created by nz2Dev on 03.01.2018
 */
public class DismissingFragment extends Fragment {

    public interface DismissListener {

        void onDismiss();

    }

    private DismissListener dismissListener;

    public void listenDismissing(DismissListener dismissListener) {
        this.dismissListener = dismissListener;
    }

    public void dismissManual() {
        dismissInternal();
    }

    protected void dismissInternal() {
        if (dismissListener != null) {
            dismissListener.onDismiss();
        }
    }

}
