package com.reharu.ikaros.haru.cortana;

import com.reharu.ikaros.haru.cortana.behavior.Feeling;
import com.reharu.ikaros.haru.tuling.vo.TulingResp;

/**
 * Created by hoshino on 2017/3/20.
 */

public class TulingRespHandler {

    private Cortana cortana ;

    private static final int ERROR = 40000 ;

    public TulingRespHandler(Cortana cortana) {
        this.cortana = cortana;
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
}
