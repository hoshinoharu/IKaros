package com.reharu.ikaros.lingmar.utils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Lingmar on 2017/3/17.
 */

public class OKHttpTool {

    public static void OKHttpGet(String requestUrl, CallBack callBack) {
        OkHttpClient OkHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(requestUrl)
                .build();
        Call call = OkHttpClient.newCall(request);
        try{
            Response reponse = call.execute();
            callBack.onResponse(call, reponse);
        }catch (Exception e){
            e.printStackTrace();
            callBack.onFail(call, e);
        }
    }

    public abstract static class CallBack implements Callback {
        public abstract void onResponse(Call call, Response reponse);

        public abstract void onFail(Call call, Exception e);

        @Override
        public void onFailure(Call call, IOException e) {onFail(call, e);}
    }
}
