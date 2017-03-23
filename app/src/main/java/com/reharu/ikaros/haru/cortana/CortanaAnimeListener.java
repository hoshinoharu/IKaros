package com.reharu.ikaros.haru.cortana;

import com.orange.entity.sprite.AnimatedSprite;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by hoshino on 2017/3/18.
 */

//代理设计模式
public class CortanaAnimeListener implements AnimatedSprite.IAnimationListener {


    //真实响应回调的类
    public static class RealListener implements AnimatedSprite.IAnimationListener{

        protected AnimatedSprite curAnim ;

        protected String curAnimaName ;

        public RealListener(AnimatedSprite curAnim, String curAnimaName) {
            this.curAnim = curAnim;
            this.curAnimaName = curAnimaName;
        }

        public RealListener() {
        }

        @Override
        public void onAnimationStarted(AnimatedSprite animatedSprite, int i) {

        }

        @Override
        public void onAnimationFrameChanged(AnimatedSprite animatedSprite, int i, int i1) {

        }

        @Override
        public void onAnimationLoopFinished(AnimatedSprite animatedSprite, int i, int i1) {

        }

        @Override
        public void onAnimationFinished(AnimatedSprite animatedSprite) {

        }
    }

    private List<RealListener> realListenerList = new CopyOnWriteArrayList<>();

    @Override
    public void onAnimationStarted(AnimatedSprite animatedSprite, int i) {
            for(RealListener realListener : realListenerList){
                realListener.onAnimationStarted(animatedSprite, i);
            }
    }


    @Override
    public void onAnimationFrameChanged(AnimatedSprite animatedSprite, int i, int i1) {
        for(RealListener realListener : realListenerList){
            realListener.onAnimationFrameChanged(animatedSprite, i, i1);
        }
    }

    @Override
    public void onAnimationLoopFinished(AnimatedSprite animatedSprite, int i, int i1) {
        for(RealListener realListener : realListenerList){
            realListener.onAnimationLoopFinished(animatedSprite, i, i1);
        }
    }

    @Override
    public void onAnimationFinished(AnimatedSprite animatedSprite) {
        for(RealListener realListener : realListenerList){
            realListener.onAnimationFinished(animatedSprite);
        }
    }

    public void addRealListener(RealListener realListener) {
        this.realListenerList.add(realListener);
    }

    public void removeRealListener(RealListener realListener){
        this.realListenerList.remove(realListener) ;
    }
}
