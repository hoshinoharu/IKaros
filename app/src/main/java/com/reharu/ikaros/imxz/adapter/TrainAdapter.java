package com.reharu.ikaros.imxz.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.reharu.harubase.base.HaruApp;
import com.reharu.ikaros.R;
import com.reharu.ikaros.imxz.entity.StationInfo;

import java.util.List;

/**
 * Created by Imxz on 2017/3/18.
 */

public class TrainAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<StationInfo> mDatas;

    public TrainAdapter(List<StationInfo> datas) {
        mInflater = LayoutInflater.from(HaruApp.context());
        mDatas = datas;
    }

    @Override
    public int getCount() {
        if (mDatas == null) {
            return 0;
        }
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = mInflater.inflate(R.layout.train_info_item, null);
        }
        StationInfo stationInfo = mDatas.get(position);
        TextView tv_start_time = (TextView) view.findViewById(R.id.tv_start_time);
        tv_start_time.setText(stationInfo.getdTime());

        TextView tv_arrive_time = (TextView) view.findViewById(R.id.tv_arrive_time);
        tv_arrive_time.setText(stationInfo.getaTime());

        TextView tv_from_station_name = (TextView) view.findViewById(R.id.tv_from_station_name);
        tv_from_station_name.setText(stationInfo.getdStation());

        TextView tv_to_station_name = (TextView) view.findViewById(R.id.tv_to_station_name);
        tv_to_station_name.setText(stationInfo.getaStation());

        TextView tv_train_code = (TextView) view.findViewById(R.id.tv_train_code);
        tv_train_code.setText(stationInfo.getTrainNumber());

        TextView tv_use_time = (TextView) view.findViewById(R.id.tv_use_time);
        tv_use_time.setText(stationInfo.getTime());

        TextView tv_train_status = (TextView) view.findViewById(R.id.tv_train_status);
        if ("已发车".equals(stationInfo.getTrainStatusDes())) {
            tv_train_status.setText("已发车");
        } else {
            tv_train_status.setText("可订票");
        }
        return view;
    }
}
