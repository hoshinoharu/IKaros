package com.reharu.ikaros.lingmar.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Lingmar on 2017/3/25.
 */

public class OverLayCardLayoutManager extends RecyclerView.LayoutManager {
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        // 初始化参数
        new CardConfig();
        detachAndScrapAttachedViews(recycler);
        int itemCount = getItemCount();
        if (itemCount >= CardConfig.MAX_SHOW_COUNT) {
            // 从可见的最底层View开始layout，一次层叠上去
            for (int position = itemCount - CardConfig.MAX_SHOW_COUNT; position < itemCount; position++) {
                View view = recycler.getViewForPosition(position);
                addView(view);
                measureChildWithMargins(view, 0, 0);
                int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
                int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
                // 让childView居中
                layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 2,
                        widthSpace / 2 + getDecoratedMeasuredWidth(view),
                        heightSpace / 2 + getDecoratedMeasuredHeight(view));

                // 不是顶层的View都要进行位置的移动和大小的更改
                int level = itemCount - position - 1;
                if (level > 0) {
                    view.setScaleX(1 - CardConfig.SCALE_GAP * level);
                    //前N层，依次向下位移和Y方向的缩小
                    if (level < CardConfig.MAX_SHOW_COUNT - 1) {
                        view.setTranslationY(CardConfig.TRANS_Y_GAP * level);
                        view.setScaleY(1 - CardConfig.SCALE_GAP * level);
                    } else {//第N层在 向下位移和Y方向的缩小的成都与 N-1层保持一致
                        view.setTranslationY(CardConfig.TRANS_Y_GAP * (level - 1));
                        view.setScaleY(1 - CardConfig.SCALE_GAP * (level - 1));
                    }
                }
            }
        }
    }
}
