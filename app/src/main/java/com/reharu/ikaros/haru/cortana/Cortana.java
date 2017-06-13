package com.reharu.ikaros.haru.cortana;

import com.orange.entity.layer.Layer;
import com.orange.entity.scene.Scene;
import com.orange.entity.sprite.AnimatedSprite;
import com.orange.entity.text.Text;
import com.orange.entity.text.TextOptions;
import com.orange.input.touch.TouchEvent;
import com.orange.util.HorizontalAlign;
import com.reharu.harubase.tools.HLog;
import com.reharu.harubase.tools.ScreenTool;
import com.reharu.harubase.tools.ViewTool;
import com.reharu.ikaros.haru.activities.HCortanaActivity;
import com.reharu.ikaros.haru.cortana.behavior.Feeling;
import com.reharu.ikaros.haru.cortana.dto.SpeackContent;
import com.reharu.ikaros.haru.cortana.listener.CortanaListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hoshino on 2017/3/18.
 */

public class Cortana extends Layer {

    //每一帧动画持续时间
    private long eachDuration = 30;

    private float scale = 0.3f ;

    private AnimatedSprite curAnima;

    private AnimatedSprite preAnima;

    private String curAnimaName ;

    private CortanaAnimeListener animateListener = new CortanaAnimeListener();

    private float lowestLocY = 0;

    //显示话语
    private Text wordsDiplayer ;

    private List<CortanaListener> cortanaListenerList = new ArrayList<>();


    private Feeling feeling ;


    private CortanaAnimResManager animResManager ;

    private float maxHeight = 0;

    private float offsetY = 300 ;



    private AnimatedSprite maxHeiAnimaSp;

    public Cortana(Scene pScene) {
        super(pScene);
        this.animResManager = new CortanaAnimResManager(this) ;
        this.setIgnoreTouch(false);
        this.setHeight(ScreenTool.getScreenSize().heightPixels/2);
        List<Animation> allAnimations = animResManager.getAllAnimations();
        init(allAnimations);
        offsetAnimation(allAnimations);
    }

    public void init(List<Animation> animationList) {
        for (Animation animation : animationList) {
            initAnimation(animation);
        }
    }

    private void initFont() {
        try{
            wordsDiplayer = new Text(0, lowestLocY, HCortanaActivity.getBitmapFont(),"今天天气是阴天东风3-4级!",new TextOptions(HorizontalAlign.CENTER),getVertexBufferObjectManager());
            wordsDiplayer.setX((ScreenTool.getScreenSize().widthPixels-wordsDiplayer.getWidth())/2);
            this.attachChild(wordsDiplayer, 0);
        }catch (Exception e){
            HLog.ex("TAG", e);
        }
    }
    //初始化小娜
    private void initAnimation(Animation animation) {
        //添加动画
        AnimatedSprite animatedSprite = animation.getAnimatedSprite() ;
        animatedSprite.setScale(scale, scale);
        if(animatedSprite == null){
            return;
        }
        animatedSprite.setX((ScreenTool.getScreenSize().widthPixels - animatedSprite.getWidth()) / 2 + animation.getOffsetX());
        animatedSprite.setY((ScreenTool.getScreenSize().heightPixels / 2 - animatedSprite.getHeight())  + animation.getOffsetY() );
        float y = animatedSprite.getBottomY() ;
        lowestLocY =  y > lowestLocY ? y : lowestLocY;
        float height = animatedSprite.getHeight() ;
        if(maxHeight < height){
            maxHeight = height ;
            maxHeiAnimaSp = animatedSprite ;
            offsetY = - maxHeiAnimaSp.getY()- ViewTool.dip2px(64);
        }
        animatedSprite.setVisible(false);
        this.attachChild(animatedSprite, 0);
    }

    //小娜动画
    public void cortanaAnimate(Animation animation, boolean isLoop){
        this.cortanaAnimate(animation, isLoop, null);
    }
    public void cortanaAnimate(Animation animation, boolean isLoop, CortanaAnimeListener.RealListener realListener) {
        preAnima = curAnima ;
        if(preAnima != null){
            //让之前的动画不显示
            preAnima.setVisible(false);
        }
        //获取动画
        curAnima = animation.getAnimatedSprite() ;
        curAnimaName = animation.getName() ;
        if (curAnima != null) {
            //展示动画
            curAnima.setVisible(true);
            if(realListener != null){
                animateListener.addRealListener(realListener);
            }
            curAnima.animate(eachDuration, isLoop, animateListener);
        }
    }

    public void nextCortanaAnimate(Animation animation, boolean isLoop){
        this.nextCortanaAnimate(animation, isLoop, null);
    }
    public void nextCortanaAnimate(final Animation animation, final boolean isLoop, CortanaAnimeListener.RealListener realListener) {
        //当前动画不在播放的话立刻播放下一动画
        if (curAnima.isAnimationRunning()) {
            //添加监听
                this.addCortanaAnimeListener(new CortanaAnimeListener.RealListener(curAnima, curAnimaName) {
                    @Override
                    public void onAnimationFinished(AnimatedSprite animatedSprite) {
                        //动画结束后判断是是否显示动画
                        if (animatedSprite == this.curAnim) {
                            cortanaAnimate(animation, isLoop);
                        }
                            //删除监听节省内存
                            removeCortanaAnimeListener(this);
                    }
                });
            if(realListener != null){
                addCortanaAnimeListener(realListener);
            }
        } else {
            this.cortanaAnimate(animation, isLoop);
        }
    }

    public void addCortanaAnimeListener(CortanaAnimeListener.RealListener realListener) {
        this.animateListener.addRealListener(realListener);
    }

    public void removeCortanaAnimeListener(CortanaAnimeListener.RealListener realListener) {
        this.animateListener.removeRealListener(realListener);
    }

    @Override
    public boolean onTouch(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        if (curAnima != null && curAnima.contains(pTouchAreaLocalX, pTouchAreaLocalY)) {
            if(!isIgnoreTouch()){
                this.respOnTouch();
            }
        }
        return super.onTouch(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
    }

    //响应触摸
    private void respOnTouch() {
        changeFeeling(Feeling.HAPPY);
        onTouch();
    }

    private void onTouch() {
        for(CortanaListener listener : cortanaListenerList){
            listener.onTouch(this);
        }
    }

    public void welcome() {
        cortanaAnimate(animResManager.getApperaAnimation(), false);
        nextCortanaAnimate(animResManager.getNormalAnimation(), true);
        changeFeeling(Feeling.NORMAL);
    }



    void speak(String content){
        for(CortanaListener cortanaListener : cortanaListenerList) {
                cortanaListener.onSpeack(this, content);
        }
    }

    public void onApperance(){
        this.welcome();
        for(CortanaListener cortanaListener : cortanaListenerList) {
            cortanaListener.onApperance(this);
        }
    }

    public void addCortanaListener(CortanaListener cortanaListener, int index) {
        this.cortanaListenerList.add(index,cortanaListener) ;
    }
    public void addCortanaListener(CortanaListener cortanaListener) {
        this.cortanaListenerList.add(cortanaListener) ;
    }

    public void removeCortanaListener(CortanaListener cortanaListener){
        this.cortanaListenerList.remove(cortanaListener) ;
    }

    //从外部接受到信息
    public void getMessage(CharSequence msg){
        onGetMessage(msg);
    }

    private void onGetMessage(CharSequence msg) {
        if(msg != null){
            for(CortanaListener cortanaListener : cortanaListenerList){
                if(cortanaListener.onInterceptGetMessage(msg.toString())){
                    return;
                }
            }
        }
    }

    public void speak(SpeackContent content){
        if(content != null){
            this.changeFeeling(content.feeling);
            this.speak(content.content);
        }
    }
    void onError(String msg){
        changeFeeling(Feeling.SAD);
        speak(msg);
    }

    void changeFeeling(Feeling feeling){
        if(this.feeling == feeling){
            onFeelingChanged(feeling, false);
        }else {
            this.feeling = feeling ;
            onFeelingChanged(feeling, true);
        }
    }

    private void onFeelingChanged(Feeling feeling, boolean changed){
        for(CortanaListener cortanaListener : cortanaListenerList){
            cortanaListener.onFeelingChanged(this, feeling, changed);
        }
    }

    public void reportError(String error) {
        onError(error);
    }

    public CortanaAnimResManager getAnimResManager() {
        return animResManager;
    }

    public float getMaxHeight() {
        return maxHeight;
    }

    public void offsetAnimation(List<Animation> animationList){
        HLog.e("TAG", offsetY);
        for(Animation animation : animationList){
            AnimatedSprite animatedSprite = animation.getAnimatedSprite() ;
            animatedSprite.setY((ScreenTool.getScreenSize().heightPixels / 2 - animatedSprite.getHeight())  + animation.getOffsetY() + offsetY);
        }
    }
}
