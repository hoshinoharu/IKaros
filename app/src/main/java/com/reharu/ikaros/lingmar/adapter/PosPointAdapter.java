package com.reharu.ikaros.lingmar.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.reharu.ikaros.R;
import com.reharu.ikaros.lingmar.domain.PosPoint;

import java.util.List;

/**
 * Created by Lingmar on 2017/3/19.
 */

public class PosPointAdapter extends BaseAdapter {

    private Context context;
    private List<PosPoint> posPoints;

    public PosPointAdapter(Context context, List<PosPoint> posPoints) {
        this.context = context;
        this.posPoints = posPoints;
    }

    @Override
    public int getCount() {
        return posPoints.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(context, R.layout.listview_queryhotel_city_item, null);
        }
        TextView tv_city_item = (TextView) view.findViewById(R.id.tv_city_item);
        TextView tv_regionType = (TextView) view.findViewById(R.id.tv_regionType);

        tv_city_item.setText(posPoints.get(i).getComposedName());
        tv_regionType.setText(posPoints.get(i).getRegionType());

        return view;
    }
}
