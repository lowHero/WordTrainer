package com.nz2dev.wordtrainer.app.presentation.infrastructure;

import android.support.annotation.CallSuper;

/**
 * Created by nz2Dev on 29.11.2017
 */
public abstract class BasePresenter<V> {

    private V view;

    /**
     * Should be called from fragment somewhere inside {@link android.app.Fragment#onViewCreated} method
     * where it's already properly loaded and can receive call
     */
    public final void setView(V view) {
        if (this.view != null) {
            throw new RuntimeException("view is already set");
        }
        this.view = view;
        onViewReady();
    }

    protected V getView() {
        return view;
    }

    public boolean isViewAttached() {
        return view != null;
    }

    /**
     * Should be called from fragment somewhere inside {@link android.app.Fragment#onDestroy} method
     * where it's detached from activity and can't still receive call
     */
    @CallSuper
    public void detachView() {
        view = null;
    }

    /**
     * Called write after #setView method and indicate that view is properly loaded and can receive calls.
     * Override this method if you want to receive callback of this operation.
     * <br>
     * The default implementation is just to check if view != null
     */
    protected void onViewReady() {
        checkViewReady();
    }

    protected void checkViewReady() {
        if (!isViewAttached()) {
            throw new NullPointerException("view == null");
        }
    }
}
