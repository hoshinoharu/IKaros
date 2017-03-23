package com.reharu.ikaros.haru.weather.service;

import com.reharu.harubase.base.HaruApp;
import com.reharu.harubase.tools.OKHttpTool;
import com.reharu.ikaros.R;
import com.reharu.ikaros.haru.weather.vo.Weather;

/**
 * Created by hoshino on 2017/3/19.
 */

public class WeatherService {
    private static final String appkey = "c1028412fa954e529dde8d0672a15987";

    private static WeatherService service = new WeatherService();
    public static WeatherService get(){
        return service ;
    }
    public void queryNow(String city, OKHttpTool.HCallBack<Weather> callBack){
        OKHttpTool.sendOKHttpRequest(HaruApp.context().getString(R.string.hweather_query_now)+"city="+city+"&key="+appkey, callBack);
    }
}
