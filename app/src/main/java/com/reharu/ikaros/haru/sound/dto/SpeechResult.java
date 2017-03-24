package com.reharu.ikaros.haru.sound.dto;

/**
 * Created by hoshino on 2017/3/24.
 */

public class SpeechResult {
    public Integer sn;
    public Boolean ls;
    public Integer bg;
    public Integer ed;
    public Ws[] ws;

    public String getStence() {
        String result = "";
        if (ws != null) {
            for (Ws w : ws) {
                result += w.getStence();
            }
        }
        return result;
    }
}
