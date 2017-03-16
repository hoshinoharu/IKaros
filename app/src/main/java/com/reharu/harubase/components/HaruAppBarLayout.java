package com.reharu.harubase.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.AppBarLayout;
import android.util.AttributeSet;

import com.reharu.harubase.tools.ScreenTool;
import com.reharu.ikaros.R;

/**
 * Created by 星野悠 on 2017/2/27.
 */

public class HaruAppBarLayout extends AppBarLayout {
    private boolean containStatusBar;
    public HaruAppBarLayout(Context context) {
        super(context);
        init();
    }
    public HaruAppBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.HaruAppBarLayout) ;
        containStatusBar = array.getBoolean(R.styleable.HaruAppBarLayout_containStatusBar, false);
        init();
    }

    private void init() {

    }
     @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = ScreenTool.getActionBarSize()*2 ;
         int top = 0 ;
        if(containStatusBar) {
            height += ScreenTool.getStatusBarHeight() ;
            top += ScreenTool.getStatusBarHeight() ;
        }
         this.setPadding(0, top, 0, 0);
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, heightMode));
    }

}
