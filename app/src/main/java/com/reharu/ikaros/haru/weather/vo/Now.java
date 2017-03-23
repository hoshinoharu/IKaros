package com.reharu.ikaros.haru.weather.vo;

/**
 * Created by hoshino on 2017/3/19.
 */

public class Now {
    public Cond cond ; //天气状况

    public Double fl ;//体感温度
    public Double hum ;//相对湿度（%）
    public Double pcpn ;//降水量（mm）
    public Double pres ;//气压
    public Double tmp ;//温度
    public Double vis ; //能见度（km）

    public Wind wind ;//风力风向

    public String spInfo() {
        return "天气状况："+cond.txt+"\n" +
                "体感温度："+fl+"\n" +
                "风向："+wind.dir+"\n" +
                "风力："+wind.sc+"";
    }

    @Override
    public String toString() {
        return "Now{" +
                "cond=" + cond +
                ", f1=" + fl +
                ", hum=" + hum +
                ", pcpn=" + pcpn +
                ", pres=" + pres +
                ", tmp=" + tmp +
                ", vis=" + vis +
                ", wind=" + wind +
                '}';
    }
}
