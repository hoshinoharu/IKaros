package com.reharu.ikaros.haru.tools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by hoshino on 2017/3/22.
 */

public class GsonTool {
    public static Gson getGson(){
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
    }
}
