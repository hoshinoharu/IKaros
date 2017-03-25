package com.reharu.ikaros.haru.cortana.dto;

import android.app.Fragment;

/**
 * Created by hoshino on 2017/3/22.
 */

public class OpIcon {
    public int imgId ;
    public String desc ;

    public Class<? extends Fragment> fragCls;
    public Fragment frag;

    public static final int CLASS = 0 ;
    public static final int OBJECT = 1 ;
    public static final int NONE= 3 ;

    private int jumpMode;

    public OpIcon(int imgId, String desc, Class<? extends Fragment> fragCls) {
        this.imgId = imgId;
        this.desc = desc;
        this.fragCls = fragCls;
        jumpMode = CLASS ;
    }

    public OpIcon(int imgId, String desc, Fragment frag) {
        this.imgId = imgId;
        this.desc = desc;
        this.frag = frag;
        jumpMode = OBJECT ;
    }

    public OpIcon(int imgId, String desc) {
        this.imgId = imgId;
        this.desc = desc;
        jumpMode = NONE ;
    }


    public int getJumpMode() {
        return jumpMode;
    }

    @Override
    public String toString() {
        return "OpIcon{" +
                "imgId=" + imgId +
                ", desc='" + desc + '\'' +
                ", fragCls=" + fragCls +
                ", frag=" + frag +
                ", jumpMode=" + jumpMode +
                '}';
    }
}
