package com.reharu.ikaros.haru.sound.dto;

/**
 * Created by hoshino on 2017/3/24.
 */

public class Ws {
    public Integer bg ;
    public Cw[] cw ;
    public String getStence(){
        if(cw!= null){
            if(cw.length > 0){
                return cw[0].w;
            }
        }
        return  "" ;
    }
}
