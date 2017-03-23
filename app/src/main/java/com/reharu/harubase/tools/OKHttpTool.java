package com.reharu.harubase.tools;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Scanner;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 星野悠 on 2017/1/6.
 */

public class OKHttpTool {

    public static abstract class HCallBack<Result> implements Callback {
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            try {
                String json = response.body().string();
                HLog.e("TAG", json);
                Type type = ReflectTool.getGenericType(getClass()) ;
                Result r = Constant.GSON.fromJson(json,type) ;
                onResponse(call, r);
            } catch (Exception e) {
                onFail(call, e);
            }
        }

        @Override
        public final void onFailure(Call call, IOException e) {
            onFail(call, e);
        }

        public abstract void onResponse(Call call, Result result);

        public abstract void onFail(Call call, Exception e);


    }

    public static Headers emptyHeaders = new Headers.Builder().build();

    public static void sendOkHttpRequest(String url, Headers headers, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder().url(url);
        if (headers != null) {
            builder.headers(headers);
        }
        client.newCall(builder.build()).enqueue(callback);
    }

    public static void sendOkHttpRequest(String url, Callback callback) {
        sendOkHttpRequest(url, emptyHeaders, callback);
    }

    public static String getHtml(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        StringBuffer buffer = new StringBuffer();
        while (scanner.hasNextLine()) {
            buffer.append(scanner.nextLine());
        }
        scanner.close();
        return buffer.toString();
    }

    public static void sendOKHttpRequest(String url, HCallBack callBack) {
        sendOkHttpRequest(url, emptyHeaders, callBack);
    }
}
