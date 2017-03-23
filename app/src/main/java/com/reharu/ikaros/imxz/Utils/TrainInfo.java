package com.reharu.ikaros.imxz.Utils;

import android.util.Log;

import com.reharu.ikaros.imxz.entity.Place;
import com.reharu.ikaros.imxz.entity.StationInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Imxz on 2017/3/17.
 */

public class TrainInfo {


    private static final String URL_STATIONINFO = "http://touch.train.qunar.com/api/train/trains2s?startStation=#START#&endStation=#END#&date=#DATE#&searchType=stasta&bd_source=qunar&filterNewDepTimeRange=00%3A00-24%3A00&filterNewArrTimeRange=00%3A00-24%3A00";
    private static final String URL_PLACEINFO = "http://touch.train.qunar.com/api/train/TrainStationData?rtype=4&jsonpCallback=&version=0&_=1490244943040";

    private static OkHttpClient okClient = null;

    static {
        okClient = new OkHttpClient.Builder().build();
    }

    public static List<StationInfo> getStationsInfo(String howDate, String fromPlace, String toPlace) {
        String url = getUrlToReplace(howDate, fromPlace, toPlace);
        Request.Builder builder = new Request.Builder().url(url);
        builder.addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 10_3 like Mac OS X) AppleWebKit/603.1.30 (KHTML, like Gecko) Mobile/14E5260b QQ/6.7.0.446 V1_IPH_SQ_6.7.0_1_APP_A Pixel/640 Core/UIWebView NetType/WIFI");
        try {
            Response response = okClient.newCall(builder.build()).execute();
            if (response.isSuccessful()) {
                String stationJson = response.body().string();
                Log.e("TAG", stationJson);
                return getStationsListByJson(stationJson);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static List<Place> getPlacesInfo() {
        Request.Builder builder = new Request.Builder().url(URL_PLACEINFO);
        builder.addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 10_3 like Mac OS X) AppleWebKit/603.1.30 (KHTML, like Gecko) Mobile/14E5260b QQ/6.7.0.446 V1_IPH_SQ_6.7.0_1_APP_A Pixel/640 Core/UIWebView NetType/WIFI");
        try {
            Response response = okClient.newCall(builder.build()).execute();
            if (response.isSuccessful()) {
                String placeJson = response.body().string();
                Log.e("TAG", placeJson);
                return getPlacesListByJson(placeJson);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<Place> getPlacesListByJson(String json) {
        List<Place> lists = new ArrayList<>();
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        try {
            jsonObject = new JSONObject(json);
            jsonObject = jsonObject.getJSONObject("dataMap");
            jsonObject = jsonObject.getJSONObject("staData");
            jsonArray = jsonObject.getJSONArray("cityList");
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.has("citys")) {
                    JSONArray citys = jsonObject.getJSONArray("citys");
                    String title = jsonObject.getString("title");
                    for (int j = 0; j < citys.length(); j++) {
                        lists.add(new Place(title, citys.getJSONObject(j).getString("name")));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lists;
    }


    private static String getUrlToReplace(String howDate, String fromPlace, String toPlace) {
        String url = new String(URL_STATIONINFO);
        url = url.replace("#DATE#", howDate);
        url = url.replace("#START#", fromPlace);
        url = url.replace("#END#", toPlace);
        return url;
    }

    private static List<StationInfo> getStationsListByJson(String stationJson) {
        List<StationInfo> lists = new ArrayList<>();
        if (stationJson != null) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(stationJson);
                jsonObject = jsonObject.getJSONObject("dataMap");
                jsonObject = jsonObject.getJSONObject("directTrainInfo");
                JSONArray trains = jsonObject.getJSONArray("trains");
                for (int i = 0; i < trains.length(); i++) {
                    StationInfo sInfo = new StationInfo();
                    jsonObject = trains.getJSONObject(i);
                    sInfo.setaStation(jsonObject.getString("aStation"));
                    sInfo.setdStation(jsonObject.getString("dStation"));
                    sInfo.setaTime(jsonObject.getString("aTime"));
                    sInfo.setdTime(jsonObject.getString("dTime"));
                    sInfo.setTime(jsonObject.getString("time"));
                    sInfo.setTrainNumber(jsonObject.getString("trainNumber"));
                    sInfo.setTrainStatusDes(jsonObject.getString("trainStatusDes"));
                    lists.add(sInfo);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return lists;
            }
        }
        Log.e("TAG", lists.toString());
        return lists;
    }


}
