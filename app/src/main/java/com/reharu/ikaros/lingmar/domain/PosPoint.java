package com.reharu.ikaros.lingmar.domain;

/**
 * Created by Lingmar on 2017/3/19.
 */

public class PosPoint {

    private String cityId;
    private String cityName;
    private String composedName;
    private String keyWord;
    private String regionType;

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setComposedName(String composedName) {
        this.composedName = composedName;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public void setRegionType(String regionType) {
        this.regionType = regionType;
    }

    public String getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public String getComposedName() {
        return composedName;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public String getRegionType() {
        return regionType;
    }

    @Override
    public String toString() {
        return "PosPoint{" +
                "cityId='" + cityId + '\'' +
                ", cityName='" + cityName + '\'' +
                ", composedName='" + composedName + '\'' +
                ", keyWord='" + keyWord + '\'' +
                ", regionType='" + regionType + '\'' +
                '}';
    }
}
