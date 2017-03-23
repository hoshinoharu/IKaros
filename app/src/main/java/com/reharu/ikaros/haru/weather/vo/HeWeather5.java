package com.reharu.ikaros.haru.weather.vo;

import com.reharu.harubase.tools.FormatTool;

/**
 * Created by hoshino on 2017/3/19.
 */

public class HeWeather5 {

    public Basic basic ;
    public Now now ;
    public String status ;

    public String spInfo() {
        return now.spInfo() + "\n更新时间："+ FormatTool.formatDate("yyyy-MM-dd HH:mm", basic.update.loc)+"\n" ;
    }

    @Override
    public String toString() {
        return "HeWeather5{" +
                "basic=" + basic +
                ", now=" + now +
                ", status='" + status + '\'' +
                '}';
    }
}
