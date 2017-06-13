package com.reharu.ikaros.haru.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.reharu.harubase.tools.HLog;
import com.superrecycleview.superlibrary.recycleview.SuperRecyclerView;

/**
 * Created by hoshino on 2017/4/20.
 */

public class HaruRecyclerView extends SuperRecyclerView {

    private float preEvY ;
    private float curEvY ;


    public HaruRecyclerView(Context context) {
        super(context);
    }

    public HaruRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HaruRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    //    public boolean dispatchTouchEvent(MotionEvent ev) {
//        int[] loc = new int[2] ;
//        this.getLocationOnScreen(loc) ;
//        switch (ev.getAction()){
//            case MotionEvent.ACTION_DOWN:preEvY = ev.getRawY();break;
//            case MotionEvent.ACTION_MOVE:
//                curEvY = ev.getRawY();
//                if(curEvY < preEvY){
//                    if(loc[1] > 0){
//                        return true ;
//                    }
//                }
//                preEvY = curEvY ;
//                break;
//
//        }
//        return super.dispatchTouchEvent(ev);
//    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        HLog.e("TAG", dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }
}
