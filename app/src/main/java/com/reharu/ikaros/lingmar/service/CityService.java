package com.reharu.ikaros.lingmar.service;

import com.reharu.harubase.base.HaruApp;
import com.reharu.harubase.tools.OKHttpTool;
import com.reharu.ikaros.R;

import okhttp3.Callback;

/**
 * Created by hoshino on 2017/3/25.
 */

public class CityService {

    public static void getCityId(String cityName, Callback callback){
        OKHttpTool.sendOkHttpRequest(HaruApp.context().getString(R.string.action_getCityId) + cityName, callback);
    }
}
