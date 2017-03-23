package com.reharu.harubase.tools;

import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hoshino on 2017/3/19.
 */

public class HttpTool {
    public interface Callback {
        void onResponse(HttpURLConnection connection);

        void onFail(Exception e);
    }
    public static abstract class HCallBack<Result> implements Callback{

        @Override
        public void onResponse(HttpURLConnection connection) {
            try {
                String json = StreamTool.getString(connection.getInputStream()) ;
                Type type = ReflectTool.getGenericType(getClass()) ;
                Result r = Constant.GSON.fromJson(json,type) ;
                connection.disconnect();
                onResponse(r);
            }catch (Exception e){
                onFail(e);
            }
        }

        public abstract void onResponse(Result result) ;
    }
    public static void sendHttpRequest(final String url, final Callback callback) {
        HLog.e("TAG", "sendHttpRequest");
        new Thread() {
            @Override
            public void run() {
                try {
                    HLog.e("TAG", "sendStart");
                    HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    connection.connect();
                    HLog.e("TAG", "sendOver");
                    callback.onResponse(connection);
                } catch (Exception e) {
                    HLog.ex("TAG", e);
                    callback.onFail(e);
                }
            }
        }.start();
    }
}
