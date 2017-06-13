package com.reharu.ikaros.haru.components;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by yungcs on 2016/1/7.
 */
public class HaruLinearLayoutManager extends LinearLayoutManager {

    private static final String TAG = HaruLinearLayoutManager.class.getSimpleName();

    public HaruLinearLayoutManager(Context context) {
        super(context);
    }

    public HaruLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }
}