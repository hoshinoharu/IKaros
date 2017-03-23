package com.reharu.ikaros.haru.weather.vo;

/**
 * Created by hoshino on 2017/3/19.
 */

public class Wind {
    public Double deg ;//风向（360度）
    public String dir ; //风向
    public String sc ; //风力
    public Double spd ;//风速（kmph）

    @Override
    public String toString() {
        return "Wind{" +
                "deg=" + deg +
                ", dir='" + dir + '\'' +
                ", sc='" + sc + '\'' +
                ", spd=" + spd +
                '}';
    }
}
