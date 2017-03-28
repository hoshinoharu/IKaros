package com.reharu.ikaros.lingmar.utils;

import android.util.Log;

import com.reharu.harubase.tools.HLog;
import com.reharu.ikaros.lingmar.domain.Goods;
import com.reharu.ikaros.lingmar.domain.Hotel;
import com.reharu.ikaros.lingmar.domain.HotelInfo;
import com.reharu.ikaros.lingmar.domain.PosPoint;
import com.reharu.ikaros.lingmar.domain.Room;
import com.reharu.ikaros.lingmar.domain.UserComment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lingmar on 2017/3/17.
 */

public class JSONTool {
    private static List<Hotel> holters = null;
    private static List<PosPoint> posPoints = null;

    public static List<Hotel> getHotelInfo(String url) {
        holters = new ArrayList<Hotel>();
        try {
            String json = readStream(new URL(url).openConnection().getInputStream());
            Log.d("123", "json: " + json);

            JSONObject jsonObj = new JSONObject(json);
            JSONArray jsonAry = jsonObj.getJSONArray("hotelList");
            for (int i = 0; i < jsonAry.length(); i++) {
                Hotel hotel = new Hotel();
                JSONObject jsonObj2 = new JSONObject(jsonAry.get(i).toString());

                // 解析JSON获取信息
                String hotelName = jsonObj2.getString("hotelName");
                String picUrl = "http:" + jsonObj2.getString("picUrl");
                String commentScore = jsonObj2.getString("commentScore") + "分";
                String location = jsonObj2.getString("districtName")
                        + "|" + jsonObj2.getString("businessAreaName");
                String totalCommentCount = jsonObj2.getString("totalCommentCount") + "条评论";
                String lowestPrice = "￥" + jsonObj2.getString("lowestPrice");
                String detailPageUrl = jsonObj2.getString("detailPageUrl");

                hotel.setHotelName(hotelName);
                hotel.setLowestPrice(lowestPrice);
                hotel.setHotelImg(picUrl);
                hotel.setCommentScore(commentScore);
                hotel.setLocation(location);
                hotel.setTotalCommentCount(totalCommentCount);
                hotel.setDetailPageUrl(detailPageUrl);
                holters.add(hotel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return holters;
    }

    public static List<PosPoint> getPosPointInfo(String url) {
        posPoints = new ArrayList<PosPoint>();
        try {
            String json = readStream(new URL(url).openConnection().getInputStream());

            JSONArray jsonArray = new JSONArray(json);
            PosPoint posPoint;
            for (int i = 0; i < jsonArray.length(); i++) {
                posPoint = new PosPoint();
                JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                String cityId = jsonObject.getString("cityId");
                String cityName = jsonObject.getString("cityName");
                String composedName = jsonObject.getString("composedName");
                String keyWord = jsonObject.getString("keyWord");
                String regionType = jsonObject.getString("regionType");
                switch (regionType) {
                    case "0":
                        regionType = "城市";
                        break;
                    case "1":
                        regionType = "行政区";
                        break;
                    case "2":
                        regionType = "地点";
                        break;
                }

                posPoint.setCityId(cityId);
                posPoint.setCityName(cityName);
                posPoint.setComposedName(composedName);
                posPoint.setKeyWord(keyWord);
                HLog.d("123", "json keyWord: " + posPoint.getKeyWord());
                posPoint.setRegionType(regionType);
                posPoints.add(posPoint);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return posPoints;
    }

    public static HotelInfo getHotelInfoByLingmar(String url) {
        HotelInfo hotelInfo = new HotelInfo();
        try {
            String json = readStream(new URL(url).openConnection().getInputStream());
            JSONObject jsonObject = new JSONObject(json);
//            Log.d("123", "json: " + json);

            JSONArray jsonArray = jsonObject.getJSONArray("rooms");
            List<Room> rooms = new ArrayList<Room>();
            for (int i = 0; i < jsonArray.length(); i++) {
                Room room = new Room();
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                room.setCoverImageUrl("http:" + jsonObject1.getString("coverImageUrl"));
                room.setRoomInfoName(jsonObject1.getString("roomInfoName"));
                room.setRoomInfo(jsonObject1.getString("roomInfo"));
                room.setBookUrl(jsonObject1.getString("bookUrl"));
                room.setMinAveragePriceSubTotal("￥" + jsonObject1.getString("minAveragePriceSubTotal"));

                rooms.add(room);
            }
            hotelInfo.setRooms(rooms);
            hotelInfo.setHotelService(jsonObject.getString("hotelService"));
            hotelInfo.setHotelPhone(jsonObject.getString("hotelPhone"));
            hotelInfo.setHotelLocal(jsonObject.getString("hotelLocal"));
            hotelInfo.setHotelOpenTime(jsonObject.getString("hotelOpenTime"));

            Log.d("123", "hotelInfo: " + hotelInfo);
        } catch (Exception e) {
            e.printStackTrace();
            HLog.ex("123", e);
        }

        return hotelInfo;
    }

    public static List<UserComment> getUserComment(String url) {
        List<UserComment> userComments = new ArrayList<UserComment>();
        try {
            String json = readStream(new URL(url).openConnection().getInputStream());
            JSONObject jsonObject = new JSONObject(json);

            JSONArray comments = jsonObject.getJSONArray("comments");
            for (int i = 0; i < comments.length(); i++) {
                UserComment userComment = new UserComment();
                JSONObject jsonObject1 = comments.getJSONObject(i);
                try {
                    userComment.setUserName(jsonObject1.getString("userName"));
                    userComment.setContent(jsonObject1.getString("content"));
                    userComment.setCommentDateTime(jsonObject1.getString("commentDateTime"));
                    userComment.setRoomTypeName(jsonObject1.getString("roomTypeName"));
                } catch (Exception e) {
                }

                userComments.add(userComment);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userComments;
    }

    public static List<Goods> getGoods(String url) {
        List<Goods> goodsList = new ArrayList<Goods>();

        try {
            String json = readStream(new URL(url).openConnection().getInputStream());

            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONObject("result").getJSONObject("1164535").getJSONArray("result");
            for (int i = 0; i < result.length(); i++) {
                Goods goods = new Goods(i+1);
                JSONObject jsonObject1 = result.getJSONObject(i);
                String content = jsonObject1.getString("content");
                String title = jsonObject1.getString("title");
                String pic = "http:" + jsonObject1.getString("pic");
                String zanTotal = jsonObject1.getString("zanTotal") + " 人都说好";
                String goodsURL = "http:" + jsonObject1.getString("url");
                String goodsId = jsonObject1.getString("itemId");

                goods.setGoodsTitle(title);
                goods.setGoodsContent(content);
                goods.setGoodsPic(pic);
                goods.setZanTotal(zanTotal);
                goods.setGoodsURL(goodsURL);
                goods.setGoodsId(goodsId);

                goodsList.add(goods);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return goodsList;
    }

    /**
     * 读取inputstream中的文本
     *
     * @param is
     * @return
     */
    private static String readStream(InputStream is) {
        InputStreamReader isr;
        String result = "";

        try {
            String line = "";
            isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
