package com.reharu.ikaros.haru.cortana.dto;

import android.view.View;

/**
 * Created by hoshino on 2017/3/22.
 */

public class OpIcon {
    public int imgId ;
    public String desc ;
    public final View.OnClickListener clickListener ;

    public OpIcon(int imgId, String desc, View.OnClickListener clickListener) {
        this.imgId = imgId;
        this.desc = desc;
        this.clickListener = clickListener;
    }
}
