package com.reharu.ikaros.haru.weather.vo;

/**
 * Created by hoshino on 2017/3/19.
 */

public class Basic {
    public String city;//城市名称
    public String cnty;//国家
    public String id;//城市ID
    public Double lat;//城市维度
    public Double lon;//城市经度
    public String prov;//城市所属省份（仅限国内城市）
    public Update update;//更新时间

    @Override
    public String toString() {
        return "Basic{" +
                "city='" + city + '\'' +
                ", cnty='" + cnty + '\'' +
                ", id='" + id + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", prov='" + prov + '\'' +
                ", update=" + update +
                '}';
    }
}
