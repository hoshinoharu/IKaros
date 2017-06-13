package com.reharu.harubase.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by 星野悠 on 2017/3/3.
 */

public class HaruViewHolder extends RecyclerView.ViewHolder implements AutoInjecter.AutoInjectable{

    public HaruViewHolder(View itemView) {
        super(itemView);
        AutoInjecter.autoInjectAllField(this);
    }

    @Override
    public View findInjectViewById(int resId) {
        return this.itemView.findViewById(resId);
    }

    public View getContentView(){
        return this.itemView ;
    }
}
