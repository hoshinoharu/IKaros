package com.reharu.ikaros.lingmar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Lingmar on 2017/3/21.
 */

public class ListViewinNestScrollView extends ListView {

    public ListViewinNestScrollView(Context context) {
        super(context);
    }

    public ListViewinNestScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewinNestScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


}
