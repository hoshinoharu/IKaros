package com.reharu.ikaros.lingmar.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.reharu.ikaros.R;
import com.reharu.ikaros.lingmar.domain.Room;
import com.reharu.ikaros.lingmar.utils.AsyncImageLoader;

import java.util.List;

/**
 * Created by Lingmar on 2017/3/21.
 */

public class RoomAdapter extends BaseAdapter {

    private Context context;
    private List<Room> roomList;

    public RoomAdapter(Context context, List<Room> roomList) {
        this.context = context;
        this.roomList = roomList;
    }

    @Override
    public int getCount() {
        return roomList.size();
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
            view = View.inflate(context, R.layout.hotel_room_layout, null);
        }
        TextView tv_room_name = (TextView) view.findViewById(R.id.tv_room_name);
        TextView tv_room_info = (TextView) view.findViewById(R.id.tv_room_info);
        TextView tv_room_price = (TextView) view.findViewById(R.id.tv_room_price);
        ImageView iv_room = (ImageView) view.findViewById(R.id.iv_room);

//        Log.d("123", "roomList size: " + roomList.size());

        Room room = roomList.get(i);
        // 异步加载图片
        AsyncImageLoader asyncLoader = new AsyncImageLoader(iv_room, room.getCoverImageUrl());
        asyncLoader.showImageByAsyncTask();
        tv_room_name.setText(room.getRoomInfoName());
        tv_room_info.setText(room.getRoomInfo());
        tv_room_price.setText(room.getMinAveragePriceSubTotal());

        return view;
    }
}
