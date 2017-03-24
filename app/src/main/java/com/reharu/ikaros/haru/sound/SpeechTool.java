package com.reharu.ikaros.haru.sound;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvent;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.reharu.ikaros.haru.sound.dto.SpeechResult;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hoshino on 2017/3/24.
 */

public class SpeechTool {

    private static boolean isInited = false ;

    public static void init(Context context, String appid) {
        if(isInited){
            return;
        }
        isInited = true ;
        SpeechUtility.createUtility(context, "appid=" + appid);
    }
    public interface SpeechListener{
        void onVolumeChanged(int volumeChanged) ;
        void onResult(String content) ;
        void onFail(String error);
        void onStart();
    }

    private static class HaruRecognizerListener implements RecognizerListener{

        private SpeechListener listener ;

        private SpeechRecognizer recognizer ;

        private List<SpeechResult> speechResults = new ArrayList<>() ;

        public HaruRecognizerListener(SpeechListener listener, SpeechRecognizer recognizer) {
            this.listener = listener;
            this.recognizer = recognizer;
        }

        @Override
        public void onVolumeChanged(int i, byte[] bytes) {
            listener.onVolumeChanged(i);
        }

        @Override
        public void onBeginOfSpeech() {

        }

        @Override
        public void onEndOfSpeech() {
        }

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            try{
                String json = recognizerResult.getResultString() ;
                Log.e("TAG","onResult:" + json) ;
                SpeechResult result = new Gson().fromJson(json, SpeechResult.class) ;
                speechResults.add(result) ;
                if(result.ls){
                    onStop();
                }
            }catch (Exception e){
                Log.e("TAG", e.toString()) ;
            }
        }

        @Override
        public void onError(SpeechError speechError) {
            listener.onFail(speechError.getErrorDescription());
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {
            switch (i){
                case SpeechEvent.EVENT_SPEECH_START:onStart();break;
            }
        }

        private void onStart() {
            listener.onStart();
        }

        private void onStop(){
            String s = "" ;
            for (SpeechResult speechResult : speechResults){
                s += speechResult.getStence() ;
            }
            listener.onResult(s);
        }
    }

    public static SpeechRecognizer listen(Context context, SpeechListener listener) {
        final SpeechRecognizer recognizer = SpeechRecognizer.createRecognizer(context, new InitListener() {
            @Override
            public void onInit(int i) {
                Log.e("TAG", i+"") ;
            }
        });
        Log.e("TAG", (recognizer==null)+"") ;
        recognizer.setParameter(SpeechConstant.DOMAIN, "iat");
        recognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        recognizer.setParameter(SpeechConstant.ACCENT, "mandarin");
        recognizer.startListening(new HaruRecognizerListener(listener, recognizer)) ;
        return recognizer ;
    }
}
