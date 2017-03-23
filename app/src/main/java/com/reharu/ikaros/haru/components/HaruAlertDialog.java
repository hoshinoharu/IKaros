package com.reharu.ikaros.haru.components;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;

/**
 * Created by hoshino on 2017/3/21.
 */

public class HaruAlertDialog extends AlertDialog {

    private Activity owner ;

    private boolean dispatchTouchEventToOwner = true;

    public HaruAlertDialog(Activity activity){
        super(activity);
        this.owner = activity ;
    }

    public HaruAlertDialog(Activity activity, int themeResId){
        super(activity, themeResId);
        this.owner = activity ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCanceledOnTouchOutside(false);
    }

}
