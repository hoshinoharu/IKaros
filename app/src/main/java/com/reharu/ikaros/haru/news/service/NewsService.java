package com.reharu.ikaros.haru.news.service;

import com.reharu.harubase.base.HaruApp;
import com.reharu.harubase.tools.HLog;
import com.reharu.harubase.tools.OKHttpTool;
import com.reharu.ikaros.R;
import com.reharu.ikaros.haru.news.NewsResult;

/**
 * Created by hoshino on 2017/4/20.
 */

public class NewsService {
    public static void queryNews(String type, int page, int limit, OKHttpTool.HCallBack<NewsResult> callBack){
        if(callBack == null){
            return;
        }
        callBack.setGsonDateFormat("yyyy-MM-dd HH:mm:ss");
        String url = HaruApp.context().getString(R.string.newApi)+"?type="+type+"&page="+page+"&limit="+limit;
        HLog.e("TAG", url);
        OKHttpTool.sendOkHttpRequest(url, callBack);
    }
}
