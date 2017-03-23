package com.reharu.ikaros.haru.cortana.dto;

/**
 * Created by hoshino on 2017/3/22.
 */

public class OpIcon {
    public int imgId ;
    public String desc ;

    public OpIcon(int imgId, String desc) {
        this.imgId = imgId;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "OpIcon{" +
                "imgId=" + imgId +
                ", desc='" + desc + '\'' +
                '}';
    }
}
