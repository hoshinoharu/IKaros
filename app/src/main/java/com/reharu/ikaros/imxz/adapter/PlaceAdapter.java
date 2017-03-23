package com.reharu.ikaros.imxz.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.reharu.ikaros.R;
import com.reharu.ikaros.imxz.entity.Place;

import java.util.List;

/**
 * Created by Imxz on 2017/3/23.
 */

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ContactsViewHolder> {
    private List<Place> datas;
    private int layoutId;
    private LayoutInflater inflater;
    private View.OnClickListener listener;

    public PlaceAdapter(List<Place> datas, int layoutId, View.OnClickListener listener) {
        this.datas = datas;
        this.layoutId = layoutId;
        this.listener = listener;
    }

    @Override
    public PlaceAdapter.ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layoutId, null);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaceAdapter.ContactsViewHolder holder, int position) {
        Place contact = datas.get(position);
        if (position == 0 || !datas.get(position - 1).getTitle().equals(contact.getTitle())) {
            holder.tvTitle.setVisibility(View.VISIBLE);
            holder.tvTitle.setText(contact.getTitle());
            holder.vLine.setVisibility(View.VISIBLE);
        } else {
            holder.tvTitle.setVisibility(View.GONE);
        }
        holder.tvName.setText(contact.getName());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    class ContactsViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvName;
        public View vLine;
        private LinearLayout ll_item_place;

        public ContactsViewHolder(View itemView) {
            super(itemView);
            ll_item_place = (LinearLayout) itemView.findViewById(R.id.ll_item_place);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_placetitle);
            tvName = (TextView) itemView.findViewById(R.id.tv_placename);
            vLine = (View) itemView.findViewById(R.id.v_line);
            ll_item_place.setOnClickListener(listener);
        }
    }

}
