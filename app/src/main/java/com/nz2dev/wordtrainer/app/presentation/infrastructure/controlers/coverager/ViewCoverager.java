package com.nz2dev.wordtrainer.app.presentation.infrastructure.controlers.coverager;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.utils.AnimationsUtils;

/**
 * Created by nz2Dev on 19.01.2018
 */
public class ViewCoverager extends FrameLayout {

    private Animation defaultEnterAnimation;
    private Animation defaultExitAnimation;
    private boolean displayBackground;

    private ViewsProxy viewsProxy;
    private int lastCoveredIndex;
    private boolean covered;

    public ViewCoverager(Context context) {
        this(context, null);
    }

    public ViewCoverager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(attrs);
//        setVisibility(INVISIBLE);
        setOnClickListener((view) -> uncover());
    }

    private void initAttr(AttributeSet attrs) {
        if (attrs == null)
            return;

        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.ViewCoverager, 0, 0);
        try {
            int defaultEnterAnimationId = typedArray.getResourceId(R.styleable.ViewCoverager_defaultEnterAnimation, R.anim.fade_in_and_slide_up);
            defaultEnterAnimation = AnimationUtils.loadAnimation(getContext(), defaultEnterAnimationId);

            displayBackground = typedArray.getBoolean(R.styleable.ViewCoverager_displayBackground, true);

            float elevation = typedArray.getFloat(R.styleable.ViewCoverager_coverager_elevation, 0f);
            ViewCompat.setElevation(this, elevation);
        } finally {
            typedArray.recycle();
        }
    }

    public void setViewsProxy(ViewsProxy viewsProxy) {
        this.viewsProxy = viewsProxy;
        this.viewsProxy.attach(this);
    }

    public void coverBy(int index) {
        coverBy(index, true);
    }

    public void coverBy(int index, boolean uncoverIfCovered) {
        if (uncoverIfCovered && covered && lastCoveredIndex == index) {
            uncover();
            return;
        }

        if (covered) {
            viewsProxy.hideView(lastCoveredIndex);
        } else {
            showBackground(true);
            covered = true;
        }

        lastCoveredIndex = index;
        viewsProxy.showView(index, this);
    }

    public void uncover() {
        if (!covered) {
            return;
        }
        covered = false;
        viewsProxy.hideView(lastCoveredIndex);
        lastCoveredIndex = -1;
        showBackground(false);
    }

    public int getCurrentCoveredIndex() {
        return lastCoveredIndex;
    }

    public boolean isCovered() {
        return covered;
    }

    public boolean isCovered(int index) {
        return covered && lastCoveredIndex == index;
    }

    private void showBackground(boolean show) {
        if (!displayBackground)
            return;

        if (show) {
            AnimationsUtils.animateToVisibleShort(this);
        } else {
            AnimationsUtils.animateToInvisibleShort(this);
        }
    }

}
