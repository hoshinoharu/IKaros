package com.reharu.ikaros.haru.cortana.sprite;

import android.view.animation.DecelerateInterpolator;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.orange.entity.scene.Scene;
import com.orange.entity.sprite.AnimatedSprite;
import com.reharu.harubase.tools.Constant;

/**
 * Created by hoshino on 2017/3/19.
 */

public class WaveSprite extends AnimatedSprite {

    private ObjectAnimator animator ;

    private Scene scene ;

    public static final String WAVE_XML = "texture/wave.xml";
    private static final String WAVE = "wave";

    public WaveSprite(final Scene scene) {
        super(0, 0, WAVE, scene.getVertexBufferObjectManager());
        this.scene = scene ;
        this.setVisible(false);
        animator = ObjectAnimator.ofFloat(this, "scale", 0.2f, 1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction() ;
                WaveSprite.this.setAlpha(1-fraction);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                setVisible(true);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                scene.detachChild(WaveSprite.this) ;
            }
        });
        animator.setDuration(1000) ;
        animator.setInterpolator(new DecelerateInterpolator());
    }

    public void show(float cx, float cy){
        this.scene.attachChild(this);
        this.setCentrePosition(cx, cy);
        Constant.MainHandler.post(new Runnable() {
            @Override
            public void run() {
                animator.start();
            }
        }) ;
    }
}
