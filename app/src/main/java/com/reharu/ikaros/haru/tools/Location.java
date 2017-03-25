package com.reharu.ikaros.haru.tools;

/**
 * Created by hoshino on 2017/3/22.
 */

public class Location {
    public boolean isSuccess ;
    public int respCode ;
    public String cityCode ;
    public String cityName ;
    public String province ;
    public String country;

    public String getLoc(){
        return country + province+ cityName ;
    }

    @Override
    public String toString() {
        return "Location{" +
                "isSuccess=" + isSuccess +
                ", respCode=" + respCode +
                ", cityCode='" + cityCode + '\'' +
                ", cityName='" + cityName + '\'' +
                ", province='" + province + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
