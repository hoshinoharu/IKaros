package com.reharu.ikaros.haru.cortana;

import com.orange.entity.sprite.AnimatedSprite;
import com.reharu.ikaros.haru.cortana.behavior.Feeling;
import com.reharu.ikaros.haru.cortana.listener.CortanaListener;

/**
 * Created by hoshino on 2017/3/22.
 */

public class CortanaFeelingController extends CortanaListener.Adapter {

    private CortanaAnimResManager animResManager;


    public CortanaFeelingController(CortanaAnimResManager animResManager) {
        this.animResManager = animResManager;
    }

    @Override
    public void onFeelingChanged(final Cortana cortana, Feeling feeling, boolean changed) {
        switch (feeling) {
            case NORMAL:
                cortana.nextCortanaAnimate(animResManager.getNormalAnimation(), true);
                break;
            case HAPPY:
                cortana.cortanaAnimate(animResManager.getHappyAnimation(), false, new CortanaAnimeListener.RealListener() {
                    @Override
                    public void onAnimationStarted(AnimatedSprite animatedSprite, int i) {
                        cortana.setIgnoreTouch(true);
                    }

                    @Override
                    public void onAnimationFinished(AnimatedSprite animatedSprite) {
                        cortana.setIgnoreTouch(false);
                        cortana.removeCortanaAnimeListener(this);
                    }
                });
                cortana.nextCortanaAnimate(animResManager.getNormalAnimation(), true);
                break;
            case DOUBT:
                cortana.cortanaAnimate(animResManager.getDoubtAnimation(), false);
                cortana.nextCortanaAnimate(animResManager.getNormalAnimation(), true);
                break;
        }
    }
}
