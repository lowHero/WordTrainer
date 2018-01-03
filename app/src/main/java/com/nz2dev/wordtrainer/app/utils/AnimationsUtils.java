package com.nz2dev.wordtrainer.app.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.view.View;

/**
 * Created by nz2Dev on 31.12.2017
 */
public class AnimationsUtils {

    public static void animateBackground(View view, int colorFrom, int colorTo, int duration) {
        ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        animator.addUpdateListener(animation -> view.setBackgroundColor((Integer) animation.getAnimatedValue()));
        animator.setDuration(duration);
        animator.start();
    }

    public static void animateToVisibleShort(View view) {
        animateToVisible(view, view.getContext().getResources().getInteger(android.R.integer.config_shortAnimTime));
    }

    public static void animateToVisibleMedium(View view) {
        animateToVisible(view, view.getContext().getResources().getInteger(android.R.integer.config_mediumAnimTime));
    }

    public static void animateToVisibleLong(View view) {
        animateToVisible(view, view.getContext().getResources().getInteger(android.R.integer.config_longAnimTime));
    }

    public static void animateToVisible(View view, int duration) {
        view.animate()
                .setListener(null)
                .cancel();

        view.setAlpha(0f);
        view.setVisibility(View.VISIBLE);

        view.animate()
                .alpha(1f)
                .setDuration(duration)
                .setListener(null);
    }

    public static void animateToInvisibleShort(View view) {
        animateToInvisible(view, view.getContext().getResources().getInteger(android.R.integer.config_shortAnimTime));
    }

    public static void animateToInvisibleMedium(View view) {
        animateToInvisible(view, view.getContext().getResources().getInteger(android.R.integer.config_mediumAnimTime));
    }

    public static void animateToInvisibleLong(View view) {
        animateToInvisible(view, view.getContext().getResources().getInteger(android.R.integer.config_longAnimTime));
    }

    public static void animateToInvisible(View view, int duration) {
        view.animate()
                .setListener(null)
                .cancel();

        view.animate()
                .alpha(0f)
                .setDuration(duration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(View.INVISIBLE);
                    }
                });
    }

}
