package com.reharu.ikaros.haru.weather.vo;

import java.util.Date;

/**
 * Created by hoshino on 2017/3/19.
 */

public class Update {//更新时间
    public Date loc;//当地时间
    public Date utc;//UTC时间

    @Override
    public String toString() {
        return "Update{" +
                "loc=" + loc +
                ", utc=" + utc +
                '}';
    }
}
