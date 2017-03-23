package com.reharu.ikaros.haru.tuling.service;

import com.reharu.harubase.base.HaruApp;
import com.reharu.harubase.tools.OKHttpTool;
import com.reharu.ikaros.R;
import com.reharu.ikaros.haru.tuling.vo.TulingResp;

/**
 * Created by hoshino on 2017/3/20.
 */

public class TulingService {

    private static final String apiKey = "bd494c8efd8d4b759d8d0c7738f53dd9" ;
    private static final String userid= "292417" ;

    private static TulingService service = new TulingService();
    public static TulingService get(){
        return service ;
    }
    public void queryTuling(String query, OKHttpTool.HCallBack<TulingResp> callBack){
        OKHttpTool.sendOKHttpRequest(HaruApp.context().getString(R.string.tuling_host)+"key="+apiKey+"&info="+query+"&userid="+userid, callBack);
    }
}
