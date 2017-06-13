package com.reharu.ikaros.haru.components;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.reharu.harubase.base.AutoInjecter;
import com.reharu.harubase.base.HaruViewHolder;
import com.reharu.ikaros.R;
import com.reharu.ikaros.haru.activities.HCortanaActivity;
import com.reharu.ikaros.haru.cortana.dto.OpIcon;

import java.util.List;

/**
 * Created by hoshino on 2017/3/22.
 */

public class OpIconAdapter extends RecyclerView.Adapter<OpIconAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_icon, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final OpIcon opIcon = opIconList.get(i);
        viewHolder.icon.setImageResource(opIcon.imgId);
        viewHolder.desc.setText(opIcon.desc);
        viewHolder.getContentView().setOnClickListener(opIcon.clickListener);
        viewHolder.bg.setImageResource(opIcon.getBgRes());
    }

    @Override
    public int getItemCount() {
        return opIconList.size();
    }

    public interface OnErrorListener {
        void onError();
    }


    class ViewHolder extends HaruViewHolder {
        @AutoInjecter.ViewInject(R.id.icon)
        ImageView icon;
        @AutoInjecter.ViewInject(R.id.desc)
        TextView desc;
        @AutoInjecter.ViewInject(R.id.bg) ImageView bg ;
        ViewHolder(View itemView) {
            super(itemView);
            final ObjectAnimator animator = ObjectAnimator.ofFloat(getContentView(), "scaleX", 1f, 1.2f, 1f).setDuration(350);
            animator.addUpdateListener(new ObjectAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float val = (float) animation.getAnimatedValue();
                    View view = (View) animator.getTarget();
                    view.setScaleY(val);
                }
            });
            getContentView().setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    animator.start();
                    return false;
                }
            });
        }
    }

    private List<OpIcon> opIconList;

    private HCortanaActivity owner;

    private OnErrorListener listener;

    public OpIconAdapter(List<OpIcon> opIconList, HCortanaActivity owner) {
        this.opIconList = opIconList;
        this.owner = owner;
    }

    public OpIconAdapter(List<OpIcon> opIconList, HCortanaActivity owner, OnErrorListener listener) {
        this.opIconList = opIconList;
        this.owner = owner;
        this.listener = listener;
    }



    private void onError() {
        if (this.listener != null) {
            this.listener.onError();
        }
    }

}
