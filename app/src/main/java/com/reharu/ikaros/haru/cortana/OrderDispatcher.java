package com.reharu.ikaros.haru.cortana;

import android.os.Bundle;

import com.reharu.ikaros.haru.activities.HCortanaActivity;
import com.reharu.ikaros.haru.cortana.listener.CortanaListener;
import com.reharu.ikaros.lingmar.fragment.QueryHotelFragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hoshino on 2017/3/25.
 */

public class OrderDispatcher extends CortanaListener.Adapter {

    private String hotelRegex = ".*?(订|定)(.*?)的?(旅馆|酒店).*?";
    private Pattern hotelPattern = Pattern.compile(hotelRegex);

    private HCortanaActivity cortanaActivity ;

    public OrderDispatcher(HCortanaActivity cortanaActivity) {
        this.cortanaActivity = cortanaActivity;
    }

    @Override
    public boolean onInterceptGetMessage(String message) {
        if(message.matches(hotelRegex)){
            return handleHotelOrder(message);
        }
        return false;
    }

    private boolean handleHotelOrder(String message) {
        Matcher matcher = hotelPattern.matcher(message);
        if(matcher.find()){
            String cityName = matcher.group(2) ;
            if(!cityName.endsWith("市")){
                cityName += "市";
            }
            Bundle bundle = new Bundle() ;
            bundle.putString(QueryHotelFragment.CITY_NAME, cityName);
            cortanaActivity.startFragment(QueryHotelFragment.class, bundle);
            return true ;
        }
        return false ;
    }
}
