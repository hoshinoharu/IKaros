package com.reharu.ikaros.haru.cortana;

import android.os.Bundle;

import com.reharu.harubase.tools.HLog;
import com.reharu.harubase.tools.OKHttpTool;
import com.reharu.ikaros.R;
import com.reharu.ikaros.haru.activities.HCortanaActivity;
import com.reharu.ikaros.haru.cortana.behavior.Feeling;
import com.reharu.ikaros.haru.cortana.listener.CortanaListener;
import com.reharu.ikaros.haru.tuling.service.TulingService;
import com.reharu.ikaros.haru.tuling.vo.TulingResp;
import com.reharu.ikaros.imxz.fragment.Fragment_Main;

import okhttp3.Call;

/**
 * Created by hoshino on 2017/3/20.
 */

public class TulingRespHandler  extends CortanaListener.Adapter{

    private Cortana cortana ;

    private HCortanaActivity cortanaActivity ;

    private static final int ERROR = 40000 ;

    public TulingRespHandler(Cortana cortana, HCortanaActivity cortanaActivity) {
        this.cortana = cortana;
        this.cortanaActivity = cortanaActivity;
    }

    public void handleTulingResp(TulingResp tulingResp){
        if(isError(tulingResp.code) ){
            if(tulingResp.code == TulingResp.EMPTY_INFO) {
                cortana.changeFeeling(Feeling.DOUBT);
                cortana.speak(tulingResp.text);
            }else {
                onError(tulingResp);
            }
        }else {
            if(tulingResp.code == TulingResp.URL){
                //表示是火车票查询信息
                if(tulingResp.url.matches(cortanaActivity.getString(R.string.tuling_train_resp))){
                    Bundle bundle = new Bundle() ;
                    bundle.putString(Fragment_Main.QUERY_URL, tulingResp.url);
                    cortanaActivity.startFragment(Fragment_Main.fragms[5], bundle);
                    return;
                }
            }
            cortana.changeFeeling(Feeling.HAPPY);
            cortana.speak(tulingResp.text);
        }
    }

    public boolean isError(long code){
        long offset = code - ERROR ;
        if( offset<= 7 && offset > 0){
            return  true ;
        }
        return false ;
    }

    public void onError(TulingResp resp){
        String error ="出现了未知错误";
        switch (resp.code){
            case TulingResp.ENOUGH_TIMES:error="思考次数不够啦";break;
            case TulingResp.FORMAT_EXCEPTION:error="数据格式错误";break;
            case TulingResp.KEY_ERROR:error="访问的键值错了哦";break;
        }
        cortana.onError(error);
    }


    @Override
    public boolean onInterceptGetMessage(String message) {
        HLog.e("TAG", "tuling：" + message);
        TulingService.get().queryTuling(message, new OKHttpTool.HCallBack<TulingResp>() {
            @Override
            public void onResponse(Call call, TulingResp tulingResp) {
                handleTulingResp(tulingResp);
            }
            @Override
            public void onFail(Call call, Exception e) {
                cortana.onError("发生了未知错误:"+e.getMessage());
                HLog.ex("TAG", e);
            }
        });
        return false ;
    }
}
