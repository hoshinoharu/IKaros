package com.reharu.ikaros.lingmar.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.reharu.ikaros.R;

/**
 * Created by Lingmar on 2017/3/21.
 */

public class SettingHotelServiceIcon extends LinearLayout {

    private TextView tv_hotel_service_name;
    private ImageView iv_hotel_service_icon;
    private static final String NAME_SPACE = "http://schemas.android.com/apk/res/com.reharu.ikaros";
    private String desicon;
    private String desname;

    public SettingHotelServiceIcon(Context context) {
        super(context);
    }

    public SettingHotelServiceIcon(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        View view = View.inflate(context, R.layout.hotel_service_icon, this);
        tv_hotel_service_name = (TextView) view.findViewById(R.id.tv_hotel_service_name);
        iv_hotel_service_icon = (ImageView) view.findViewById(R.id.iv_hotel_service_icon);

        initAttr(attrs);
        // 为标签设值
        tv_hotel_service_name.setText(desname);
        iv_hotel_service_icon.setImageResource(Integer.parseInt(desicon.substring(1)));
    }

    public SettingHotelServiceIcon(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void initAttr(AttributeSet attrs) {
        desname = attrs.getAttributeValue(NAME_SPACE, "desname");
        desicon = attrs.getAttributeValue(NAME_SPACE, "desicon");

//        Log.d("123", "desicon: " + desicon);
//        Log.d("123", "R.mipmap.park: " + R.mipmap.park);
    }
}
