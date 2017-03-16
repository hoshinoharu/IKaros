package com.reharu.harubase.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.reharu.harubase.base.AutoInjecter;

/**
 * Created by 星野悠 on 2017/3/5.
 */

public class HaruAdapter extends RecyclerView.Adapter<HaruAdapter.HaruViewHolder> {

    @Override
    public HaruViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(HaruViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class HaruViewHolder extends RecyclerView.ViewHolder implements AutoInjecter.AutoInjectable {
        public HaruViewHolder(View itemView) {
            super(itemView);
            AutoInjecter.autoInjectAllField(this);
        }

        @Override
        public View findInjectViewById(int resId) {
            return this.itemView.findViewById(resId);
        }
    }
}
