package com.reharu.ikaros.haru.components;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.reharu.harubase.base.AutoInjecter;
import com.reharu.harubase.base.HaruViewHolder;
import com.reharu.ikaros.R;
import com.reharu.ikaros.haru.cortana.dto.OpIcon;

import java.util.List;

/**
 * Created by hoshino on 2017/3/22.
 */

public class OpIconAdapter extends BaseAdapter {


    public class ViewHolder extends HaruViewHolder{
        @AutoInjecter.ViewInject(R.id.icon) ImageView icon ;
        @AutoInjecter.ViewInject(R.id.desc) TextView desc ;
        public ViewHolder(View itemView) {
            super(itemView);
        }
        public View getContent(){
            return this.itemView ;
        }
    }

    private List<OpIcon> opIconList ;

    private Activity owner ;

    public OpIconAdapter(List<OpIcon> opIconList, Activity owner) {
        this.opIconList = opIconList;
        this.owner = owner;
    }

    @Override
    public int getCount() {
        return opIconList.size();
    }

    @Override
    public Object getItem(int position) {
        return opIconList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final OpIcon opIcon = opIconList.get(position) ;
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_icon, parent, false) ;
            ViewHolder viewHolder = new ViewHolder(convertView) ;
            final ObjectAnimator animator = ObjectAnimator.ofFloat(viewHolder.getContent(), "scaleX", 1f, 1.2f, 1f).setDuration(350);
            animator.addUpdateListener(new ObjectAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float val = (float) animation.getAnimatedValue();
                    View view = (View) animator.getTarget();
                    view.setScaleY(val);
                }
             });
            convertView.setTag(viewHolder);
                viewHolder.getContent().setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        animator.start();
                        return false;
                    }
                });
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.getContent().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                owner.startActivity(new Intent(owner, opIcon.acCls));
            }
        });
        viewHolder.icon.setImageResource(opIcon.imgId);
        viewHolder.desc.setText(opIcon.desc);
        return convertView;
    }
}
