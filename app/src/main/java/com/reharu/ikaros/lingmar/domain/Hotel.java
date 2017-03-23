package com.reharu.ikaros.lingmar.domain;

/**
 * Created by Lingmar on 2017/3/17.
 */

public class Hotel {
    private String hotelImg;
    private String hotelName;
    private String lowestPrice;
    private String commentScore;
    private String totalCommentCount;
    private String location;
    private String detailPageUrl;

    public String getDetailPageUrl() {
        return detailPageUrl;
    }

    public String getHotelImg() {
        return hotelImg;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getLowestPrice() {
        return lowestPrice;
    }

    public String getCommentScore() {
        return commentScore;
    }

    public String getTotalCommentCount() {
        return totalCommentCount;
    }

    public String getLocation() {
        return location;
    }

    public void setHotelImg(String hotelImg) {
        this.hotelImg = hotelImg;
    }

    public void setDetailPageUrl(String detailPageUrl) {
        this.detailPageUrl = detailPageUrl;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public void setLowestPrice(String lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public void setCommentScore(String commentScore) {
        this.commentScore = commentScore;
    }

    public void setTotalCommentCount(String totalCommentCount) {
        this.totalCommentCount = totalCommentCount;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "hotelImg='" + hotelImg + '\'' +
                ", hotelName='" + hotelName + '\'' +
                ", lowestPrice='" + lowestPrice + '\'' +
                ", commentScore='" + commentScore + '\'' +
                ", totalCommentCount='" + totalCommentCount + '\'' +
                ", location='" + location + '\'' +
                ", detailPageUrl='" + detailPageUrl + '\'' +
                '}';
    }
}
