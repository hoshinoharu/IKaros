package com.reharu.ikaros.haru.weather.vo;

import java.util.Arrays;

/**
 * Created by hoshino on 2017/3/19.
 */

public class Weather {
    public  HeWeather5[] HeWeather5 ;
    public HeWeather5 hWeather(){
        if(HeWeather5 != null && HeWeather5.length > 0){
            return HeWeather5[0] ;
        }
        return  new HeWeather5() ;
    }

    public String spInfo() {

        return hWeather().spInfo();
    }

    @Override
    public String toString() {
        return "Weather{" +
                "hWeather5=" + Arrays.toString(HeWeather5) +
                '}';
    }
}
