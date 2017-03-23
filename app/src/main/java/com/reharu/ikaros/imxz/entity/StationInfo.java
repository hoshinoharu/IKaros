package com.reharu.ikaros.imxz.entity;

/**
 * Created by Imxz on 2017/3/18.
 */

public class StationInfo {
    public String getaStation() {
        return aStation;
    }

    public void setaStation(String aStation) {
        this.aStation = aStation;
    }

    public String getdStation() {
        return dStation;
    }

    public void setdStation(String dStation) {
        this.dStation = dStation;
    }

    public String getaTime() {
        return aTime;
    }

    public void setaTime(String aTime) {
        this.aTime = aTime;
    }

    public String getdTime() {
        return dTime;
    }

    public void setdTime(String dTime) {
        this.dTime = dTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getTrainStatusDes() {
        return trainStatusDes;
    }

    public void setTrainStatusDes(String trainStatusDes) {
        this.trainStatusDes = trainStatusDes;
    }

    private String aStation;//到达站点
    private String dStation;//出发站点
    private String aTime;//到达时间
    private String dTime;//出发时间
    private String time;//耗时
    private String trainNumber;//列车号
    private String trainStatusDes;//是否可预订

    @Override
    public String toString() {
        return "StationInfo{" +
                "aStation='" + aStation + '\'' +
                ", dStation='" + dStation + '\'' +
                ", aTime='" + aTime + '\'' +
                ", dTime='" + dTime + '\'' +
                ", time='" + time + '\'' +
                ", trainNumber='" + trainNumber + '\'' +
                ", trainStatusDes='" + trainStatusDes + '\'' +
                '}';
    }
}
