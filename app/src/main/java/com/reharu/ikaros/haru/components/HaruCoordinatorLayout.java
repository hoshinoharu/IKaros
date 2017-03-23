package com.reharu.ikaros.haru.components;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by hoshino on 2017/3/21.
 */

public class HaruCoordinatorLayout extends CoordinatorLayout {

    public interface OnDispatchTouchEventListener{
        void onDispatchTouchEvent(MotionEvent event) ;
    }

    private OnDispatchTouchEventListener onDispatchTouchEventListener ;


    public HaruCoordinatorLayout(Context context) {
        super(context);
    }

    public HaruCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HaruCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(onDispatchTouchEventListener != null){
            onDispatchTouchEventListener.onDispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setOnDispatchTouchEventListener(OnDispatchTouchEventListener onDispatchTouchEventListener) {
        this.onDispatchTouchEventListener = onDispatchTouchEventListener;
    }
}
