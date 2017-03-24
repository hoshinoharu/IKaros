package com.reharu.ikaros.haru.components;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.reharu.harubase.base.AutoInjecter;
import com.reharu.harubase.base.HaruApp;
import com.reharu.harubase.tools.ActivityTool;
import com.reharu.harubase.tools.Constant;
import com.reharu.harubase.tools.HLog;
import com.reharu.ikaros.R;

import java.io.IOException;

/**
 * Created by hoshino on 2017/3/24.
 */

public class SpeakVolumeDialog implements AutoInjecter.AutoInjectable{

    public interface Listener{
        void onDialogShow();
        void onDialogDismiss();
    }

    @AutoInjecter.ViewInject(R.id.volumeImg) private ImageView volumeImg;
    private View mainView ;
    private AnimationDrawable showAnimation;
    private AnimationDrawable dismissAnimation ;
    private AlertDialog dialog ;

    private int duration = 20 ;
    private int maxSize = 32 ;

    private Listener listener ;

    private ObjectAnimator curAnime ;

    public SpeakVolumeDialog(Activity activity) {
        mainView = LayoutInflater.from(activity).inflate(R.layout.dialog_speak_volume, ActivityTool.getRootView(activity), false);
        AutoInjecter.autoInjectAllField(this);
        dialog = new AlertDialog.Builder(activity).create();
        dialog.setCancelable(false);
        loadAnimation();
    }

    @Override
    public View findInjectViewById(int resId) {
        return this.mainView.findViewById(resId);
    }

    public void show(){
        dialog.show();
        dialog.setContentView(mainView);
        volumeImg.setScaleX(1);
        volumeImg.setScaleY(1);
        volumeImg.setImageDrawable(showAnimation);
        showAnimation.start();
        Constant.MainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onShow();
            }
        }, duration*maxSize) ;
    }

    public void dismiss(){
        volumeImg.setImageDrawable(dismissAnimation);
        dismissAnimation.start();
        HLog.e("TAG", "动画开始");
        Constant.MainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onDismiss();
            }
        }, duration*maxSize) ;
    }
    private void loadAnimation(){
        showAnimation = new AnimationDrawable();
        dismissAnimation= new AnimationDrawable();
        String pre = "speak_vol/speck_volume_";
        int index = 1 ;
        try {
            for(index = 1; index <= maxSize; index ++){
                showAnimation.addFrame(new BitmapDrawable(HaruApp.context().getResources(), HaruApp.context().getAssets().open(pre + index + ".png")), duration);
                dismissAnimation.addFrame(new BitmapDrawable(HaruApp.context().getResources(), HaruApp.context().getAssets().open(pre + (maxSize+1-index) + ".png")), duration);
            }
            showAnimation.setOneShot(true);
            dismissAnimation.setOneShot(true);
        } catch (IOException e) {
            HLog.ex("TAG", e);
        }
    }

    private void onShow(){
        if(listener != null){
            listener.onDialogShow();
        }
    }

    private void onDismiss(){
        dialog.dismiss();
        if(listener!=null){
            listener.onDialogDismiss();
        }
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void changVolume(int vol){
        if(curAnime != null){
            curAnime.cancel();
        }
        float scale = vol/20f + 1;
        curAnime = new ObjectAnimator();
        curAnime.setFloatValues(volumeImg.getScaleX(), scale);
        curAnime.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float val = (float) animation.getAnimatedValue();
                volumeImg.setScaleX(val);
                volumeImg.setScaleY(val);
            }
        });
        curAnime.setDuration(100);
        curAnime.start();
    }
}
