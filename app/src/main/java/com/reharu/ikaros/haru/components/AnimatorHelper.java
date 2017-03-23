package com.reharu.ikaros.haru.components;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;

import java.util.List;

/**
 * Created by hoshino on 2017/3/23.
 */

public class AnimatorHelper {
    public static Animator scaleAppera(final List<View> views) {
        ObjectAnimator animator = new ObjectAnimator();
        animator.setFloatValues(0.2f, 1);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float val = (float) animation.getAnimatedValue();
                for (View view : views) {
                    view.setScaleX(val);
                    view.setScaleY(val);
                }
            }
        });
        return animator;
    }

    public static Animator scaleAppera(final View view) {
        ObjectAnimator animator = new ObjectAnimator();
        animator.setFloatValues(0.2f, 1);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float val = (float) animation.getAnimatedValue();
                view.setScaleX(val);
                view.setScaleY(val);
            }
        });
        return animator;
    }
}
