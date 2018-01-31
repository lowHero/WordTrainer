package com.nz2dev.wordtrainer.app.presentation.infrastructure;

import android.support.v4.app.Fragment;

/**
 * Created by nz2Dev on 29.11.2017
 */
public abstract class BasePresenter<V> {

    private V view;

    /**
     * should be called from fragment somewhere inside {@link Fragment#onActivityCreated} method
     * where it's already properly loaded and can receive call
     */
    public final void setView(V view) {
        if (this.view != null) {
            throw new RuntimeException("view is already set");
        }
        this.view = view;
        onViewReady();
    }

    /**
     * should be called from fragment somewhere inside {@link Fragment#onDestroy} method
     * where it's detached from activity and can't still receive call
     */
    public final void detachView() {
        view = null;
        onViewRecycled();
    }

    public boolean isViewAttached() {
        return view != null;
    }

    protected void onViewReady() {
    }

    protected void onViewRecycled() {
    }

    protected V getView() {
        return view;
    }

}
