package com.reharu.ikaros.lingmar.domain;

import java.util.List;

/**
 * Created by Lingmar on 2017/3/21.
 */

public class HotelInfo {
    private String hotelName;
    private String hotelID;
    private String hotelService;
    private String hotelPhone;
    private String hotelLocal;
    private String hotelOpenTime;
    private String detailPageUrl;
    private List<Room> rooms;

    public String getHotelName() {
        return hotelName;
    }

    public String getHotelID() {
        return hotelID;
    }

    public String getHotelService() {
        return hotelService;
    }

    public String getHotelPhone() {
        return hotelPhone;
    }

    public String getHotelLocal() {
        return hotelLocal;
    }

    public String getHotelOpenTime() {
        return hotelOpenTime;
    }

    public String getDetailPageUrl() {
        return detailPageUrl;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }

    public void setHotelService(String hotelService) {
        this.hotelService = hotelService;
    }

    public void setHotelPhone(String hotelPhone) {
        this.hotelPhone = hotelPhone;
    }

    public void setHotelLocal(String hotelLocal) {
        this.hotelLocal = hotelLocal;
    }

    public void setHotelOpenTime(String hotelOpenTime) {
        this.hotelOpenTime = hotelOpenTime;
    }

    public void setDetailPageUrl(String detailPageUrl) {
        this.detailPageUrl = detailPageUrl;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "HotelInfo{" +
                "hotelName='" + hotelName + '\'' +
                ", hotelID='" + hotelID + '\'' +
                ", hotelService='" + hotelService + '\'' +
                ", hotelPhone='" + hotelPhone + '\'' +
                ", hotelLocal='" + hotelLocal + '\'' +
                ", hotelOpenTime='" + hotelOpenTime + '\'' +
                ", detailPageUrl='" + detailPageUrl + '\'' +
                ", rooms=" + rooms +
                '}';
    }
}
