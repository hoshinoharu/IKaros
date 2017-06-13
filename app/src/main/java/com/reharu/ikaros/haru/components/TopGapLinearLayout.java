package com.reharu.ikaros.haru.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.reharu.ikaros.R;

/**
 * Created by hoshino on 2017/4/20.
 */

public class TopGapLinearLayout extends LinearLayout {

    private float gapRadius = 0f ;

    public TopGapLinearLayout(Context context) {
        super(context);
    }

    public TopGapLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TopGapLinearLayout);
        gapRadius = ta.getFloat(R.styleable.TopGapLinearLayout_gapRadius, 0f) ;
    }

    public TopGapLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setGapRadius(float gapRadius) {
        this.gapRadius = gapRadius;
    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Paint paint = new Paint() ;
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR)) ;
        canvas.drawCircle(getWidth()/2, -30,gapRadius, paint);
    }
}
