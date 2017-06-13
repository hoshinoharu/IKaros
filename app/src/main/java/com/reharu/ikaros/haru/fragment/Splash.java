package com.reharu.ikaros.haru.fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.reharu.harubase.base.AutoInjecter;
import com.reharu.harubase.tools.ScreenTool;
import com.reharu.ikaros.R;

public class Splash extends HaruGameFragment {

    @AutoInjecter.ViewInject(R.id.iv_clouds_left)
    private ImageView iv_clouds_left;
    @AutoInjecter.ViewInject(R.id.iv_clouds_center)
    private ImageView iv_clouds_center;
    @AutoInjecter.ViewInject(R.id.iv_clouds_right)
    private ImageView iv_clouds_right;
    @AutoInjecter.ViewInject(R.id.clouds_left_clone)
    private ImageView clouds_left_clone;
    @AutoInjecter.ViewInject(R.id.clouds_right_clone)
    private ImageView clouds_right_clone;
    @AutoInjecter.ViewInject(R.id.iv_clouds_center_clone)
    private ImageView iv_clouds_center_clone;
    @AutoInjecter.ViewInject(R.id.iv_clouds_center_clone_2)
    private ImageView iv_clouds_center_clone_2;
    @AutoInjecter.ViewInject(R.id.iv_clouds_center_clone_3)
    private ImageView iv_clouds_center_clone_3;
    @AutoInjecter.ViewInject(R.id.iv_buildings)
    private ImageView iv_buildings;
    @AutoInjecter.ViewInject(R.id.iv_na)
    private ImageView iv_na;
    @AutoInjecter.ViewInject(R.id.iv_sun)
    private ImageView iv_sun;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        initData();
        return view ;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_splash;
    }

    private void initData() {
        int winWid = ScreenTool.getScreenSize().widthPixels;
        float clouds_left_wid = (float) getViewWidth(iv_clouds_left);
        float clouds_right_wid = (float) getViewWidth(iv_clouds_right);
        float clouds_center_wid = (float) getViewWidth(iv_clouds_center);
//        Log.d("123", "clouds_right_wid: " + clouds_right_wid);
        // 自然过渡
//        TranslateAnimNoRepeat(8000, iv_clouds_left, 0, winWid);
//        TranslateAnimNoRepeat(6000, clouds_left_clone, 0, winWid);
//        TranslateAnimNoRepeat(10000, iv_clouds_right, 0, winWid);
//        TranslateAnimNoRepeat(9000, clouds_right_clone, 0, winWid);
//        TranslateAnimNoRepeat(12000, iv_clouds_center, 0, winWid);
        TranslateAnimNoRepeat(14000, iv_clouds_center_clone_3, 0, 3 * winWid + clouds_center_wid);
        // 平移动画
//        TranslateAnim(8000, iv_clouds_left, -clouds_left_wid, 2 * winWid);
//        TranslateAnim(8000, clouds_left_clone, -clouds_left_wid - winWid, winWid);
//        TranslateAnim(10000, iv_clouds_right, -clouds_right_wid, 2 * winWid);
//        TranslateAnim(10000, clouds_right_clone, -clouds_right_wid - winWid, winWid);
        TranslateAnim(14000, iv_clouds_center, -(winWid / 2 + clouds_center_wid), 2 * winWid + winWid / 2 + clouds_center_wid / 2);
        TranslateAnim(14000, iv_clouds_center_clone, -(winWid + winWid / 2 + clouds_center_wid), winWid + winWid / 2 + clouds_center_wid / 2);
        TranslateAnim(14000, iv_clouds_center_clone_2, -(2 * winWid + winWid / 2 + clouds_center_wid), winWid / 2 + clouds_center_wid / 2);
        // 旋转动画
        RotateAnim();
    }

    private void TranslateAnim(int duration, ImageView view, float starPos, float endPos) {
        ObjectAnimator animation = ObjectAnimator.ofFloat(view, "translationX", starPos, endPos);
        animation.setDuration(duration);
        animation.setRepeatCount(TranslateAnimation.INFINITE);
        // 设置插入器
        animation.setInterpolator(new LinearInterpolator());
        animation.start();
    }

    private void TranslateAnimNoRepeat(int duration, ImageView view, float starPos, float endPos) {
        ObjectAnimator animation = ObjectAnimator.ofFloat(view, "translationX", starPos, endPos);
        animation.setDuration(duration);
        // 设置插入器
        animation.setInterpolator(new LinearInterpolator());
        animation.start();
    }

    private void RotateAnim() {
        RotateAnimation animation = new RotateAnimation(0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        // 设置保持动画最后的状态
        animation.setFillAfter(true);
        // 设置动画时间
        animation.setDuration(3000);
        // 持续播放
        animation.setRepeatCount(RotateAnimation.INFINITE);
        // 设置插入器
        animation.setInterpolator(new LinearInterpolator());
        iv_sun.startAnimation(animation);
    }


    private int getViewWidth(final ImageView view) {
        return view.getLayoutParams().width;
//        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//        view.measure(w, h);
//
//        return view.getMeasuredWidth();
    }
}
