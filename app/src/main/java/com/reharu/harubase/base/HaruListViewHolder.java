package com.reharu.harubase.base;

import android.view.View;

/**
 * Created by hoshino on 2017/3/20.
 */

public class HaruListViewHolder implements AutoInjecter.AutoInjectable {

    private View contentView ;

    public HaruListViewHolder(View contentView) {
        this.contentView = contentView;
        AutoInjecter.autoInjectAllField(this);
    }

    @Override
    public View findInjectViewById(int resId) {
        return this.contentView.findViewById(resId);
    }
}
