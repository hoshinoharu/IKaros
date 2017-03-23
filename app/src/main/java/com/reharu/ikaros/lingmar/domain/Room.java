package com.reharu.ikaros.lingmar.domain;

/**
 * Created by Lingmar on 2017/3/21.
 */

public class Room {
    private String minAveragePriceSubTotal;
    private String coverImageUrl;
    private String roomInfoName;
    private String roomInfo;
    private String bookUrl;

    public String getMinAveragePriceSubTotal() {
        return minAveragePriceSubTotal;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public String getRoomInfoName() {
        return roomInfoName;
    }

    public String getRoomInfo() {
        return roomInfo;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public void setMinAveragePriceSubTotal(String minAveragePriceSubTotal) {
        this.minAveragePriceSubTotal = minAveragePriceSubTotal;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public void setRoomInfoName(String roomInfoName) {
        this.roomInfoName = roomInfoName;
    }

    public void setRoomInfo(String roomInfo) {
        this.roomInfo = roomInfo;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    @Override
    public String toString() {
        return "Room{" +
                "minAveragePriceSubTotal='" + minAveragePriceSubTotal + '\'' +
                ", coverImageUrl='" + coverImageUrl + '\'' +
                ", roomInfoName='" + roomInfoName + '\'' +
                ", roomInfo='" + roomInfo + '\'' +
                ", bookUrl='" + bookUrl + '\'' +
                '}';
    }
}
