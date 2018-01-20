package com.nz2dev.wordtrainer.app.presentation.controlers.coverager;

import android.view.ViewGroup;

/**
 * Created by nz2Dev on 19.01.2018
 */
public abstract class ViewsProxy {

    private ViewCoverager viewCoverager;

    void attach(ViewCoverager viewCoverager) {
        this.viewCoverager = viewCoverager;
        initializeContainer(viewCoverager);
    }

    protected abstract void initializeContainer(ViewGroup container);

    protected abstract void showView(int index, ViewGroup container);

    protected abstract void hideView(int index);

    protected ViewGroup getContainer() {
        return viewCoverager;
    }

}
