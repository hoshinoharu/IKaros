package com.reharu.ikaros.haru.components;

import com.orange.res.SoundRes;
import com.reharu.ikaros.haru.activities.HCortanaActivity;
import com.reharu.ikaros.haru.cortana.Cortana;
import com.reharu.ikaros.haru.cortana.SoundMannager;
import com.reharu.ikaros.haru.sound.SpeechTool;

/**
 * Created by hoshino on 2017/3/24.
 */

public class SpeackVolumeChangeListener implements SpeechTool.SpeechListener, SpeakVolumeDialog.Listener {

    private HCortanaActivity context ;

    private SpeakVolumeDialog dialog ;

    private Cortana cortana ;

    private String speechContent ;

    private  boolean resp = true ;

    public SpeackVolumeChangeListener(HCortanaActivity context, SpeakVolumeDialog dialog, Cortana cortana) {
        this.context = context;
        this.dialog = dialog;
        this.cortana = cortana;
    }

    @Override
    public void onDialogShow() {
        SoundRes.playSound(SoundMannager.LISTENING_START);
//        SpeechTool.listen(context, new SpeechTool.SpeechListener() {
//            @Override
//            public void onVolumeChanged(int volumeChanged) {
//
//            }
//
//            @Override
//            public void onResult(String content) {
//
//            }
//
//            @Override
//            public void onFail(String error) {
//                SoundRes.playSound(SoundMannager.LISTENING_END);
//                cortana.reportError(error) ;
//            }
//
//            @Override
//            public void onStart() {
//                SoundRes.playSound(SoundMannager.LISTENING_START);
//            }
//        });
        SpeechTool.listen(context, this) ;
    }

    @Override
    public void onDialogDismiss() {
        SoundRes.playSound(SoundMannager.LISTENING_END);
        if (resp){
            context.talkToCortana(speechContent);
        }else {
            cortana.reportError(speechContent);
        }
    }

    @Override
    public void onVolumeChanged(int volumeChanged) {
        dialog.changVolume(volumeChanged);
    }

    @Override
    public void onResult(String content) {
        resp = true ;
        speechContent = content ;
        dialog.dismiss();

    }

    @Override
    public void onFail(String error) {
        resp = false ;
        speechContent = error ;
        dialog.dismiss();
    }

    @Override
    public void onStart() {

    }
}
