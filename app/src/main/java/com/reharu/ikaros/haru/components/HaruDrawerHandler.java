package com.reharu.ikaros.haru.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.nineoldandroids.animation.ObjectAnimator;
import com.reharu.harubase.components.HaruOnGestureAdapter;
import com.reharu.harubase.tools.HLog;
import com.reharu.harubase.tools.ViewTool;
import com.reharu.ikaros.R;

/**
 * Created by hoshino on 2017/3/21.
 */

public class HaruDrawerHandler extends FrameLayout {

    private ImageView arrow ;

    public HaruDrawerHandler(Context context) {
        super(context);
    }

    public HaruDrawerHandler(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    private GestureDetector detector ;



    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        arrow = new ImageView(getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewTool.dip2px(96), ViewTool.dip2px(48) );
        layoutParams.gravity = Gravity.BOTTOM ;
        this.addView(arrow,layoutParams);
        arrow.setImageResource(R.drawable.arrow_down_48dp);
        this.detector = new GestureDetector(getContext(), new HaruOnGestureAdapter(){

            @Override
            public boolean onDown(MotionEvent e) {
                return super.onDown(e);
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                float y = e2.getRawY() ;
                HLog.e("TAG", "onScroll" , y);
                if (y >= 0 && y <= getHeight()) {
                    setY(y - getHeight());
                }
                return false;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                HLog.e("TAG", "onFling : " + velocityY);
                if(velocityY >= 200){
                    open();
                }else if(velocityY <= -200){
                    close();
                } else {
                  adjustTouchLoc(e2.getRawY());
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }

        }) ;
    }


    public void open() {
        ObjectAnimator.ofFloat(this, "y", this.getY(), 0).setDuration(500).start();
    }

    public void close() {
        ObjectAnimator.ofFloat(this, "y", this.getY(), arrow.getHeight()-getHeight()).setDuration(500).start();
    }


    public void adjustTouchLoc(float y){
        if(this.getY() >= -this.getHeight()/2.0){
            close();
        }else {
            open();
        }
    }

    public GestureDetector getDetector() {
        return detector;
    }

}
