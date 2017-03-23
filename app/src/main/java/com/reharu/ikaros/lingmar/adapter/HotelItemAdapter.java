package com.reharu.ikaros.lingmar.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.reharu.ikaros.R;
import com.reharu.ikaros.lingmar.domain.Hotel;
import com.reharu.ikaros.lingmar.utils.AsyncImageLoader;

import java.util.List;

/**
 * Created by Lingmar on 2017/3/17.
 */

public class HotelItemAdapter extends BaseAdapter {
    private Context context;
    private List<Hotel> hotels;

    public HotelItemAdapter(Context context, List<Hotel> hotels) {
        this.context = context;
        this.hotels = hotels;
    }

    @Override
    public int getCount() {
        return hotels.size();
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
            view = View.inflate(context, R.layout.listview_queryhotel_hotel_item, null);
        }
        ImageView iv_hotel_img = (ImageView) view.findViewById(R.id.iv_hotel_img);
        TextView tv_hotelName = (TextView) view.findViewById(R.id.tv_hotelName);
        TextView lowest_price = (TextView) view.findViewById(R.id.tv_lowest_price);
        TextView tv_commentScore = (TextView) view.findViewById(R.id.tv_commentScore);
        TextView tv_total_comment_count = (TextView) view.findViewById(R.id.tv_total_comment_count);
        TextView tv_location = (TextView) view.findViewById(R.id.tv_location);

        String imagURL = hotels.get(i).getHotelImg();
        AsyncImageLoader imageLoader = new AsyncImageLoader(iv_hotel_img, imagURL);
        imageLoader.showImageByAsyncTask();

        tv_hotelName.setText(hotels.get(i).getHotelName());
        lowest_price.setText(hotels.get(i).getLowestPrice());
        tv_commentScore.setText(hotels.get(i).getCommentScore());
        tv_total_comment_count.setText(hotels.get(i).getTotalCommentCount());
        tv_location.setText(hotels.get(i).getLocation());

        return view;
    }
}
