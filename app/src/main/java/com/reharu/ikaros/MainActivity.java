package com.reharu.ikaros;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.reharu.harubase.base.AutoInjecter;
import com.reharu.harubase.base.HaruActivity;

public class MainActivity extends HaruActivity {

    @AutoInjecter.ViewInject(R.id.container)  private RelativeLayout container ;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("TAG", "T");
        initCortana();
    }

    private void initCortana() {
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.alpha = 0.2f ;
        attributes.flags= WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                               | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        attributes.width = 40 ;
        attributes.height = 40 ;
        attributes.type = WindowManager.LayoutParams.TYPE_PHONE ;
        attributes.format= PixelFormat.RGBA_8888;
    }

}
