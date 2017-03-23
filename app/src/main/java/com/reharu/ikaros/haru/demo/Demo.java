package com.reharu.ikaros.haru.demo;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.reharu.harubase.base.AutoInjecter;
import com.reharu.harubase.base.HaruActivity;
import com.reharu.harubase.tools.HLog;
import com.reharu.harubase.tools.OKHttpTool;
import com.reharu.harubase.tools.Res;
import com.reharu.harubase.tools.ScreenTool;
import com.reharu.harubase.tools.ViewTool;
import com.reharu.ikaros.R;

import java.util.List;

import okhttp3.Call;

//继承HaruActivity抽象类
public class Demo extends HaruActivity {

    @AutoInjecter.ViewInject(R.id.toolbarImg)
    private ImageView toolbarImg;
    @AutoInjecter.ViewInject(R.id.toolbar)
    private Toolbar toolbar;
    private List<Info> infoList;


    //实现此方法
    @Override
    public int getContentViewId() {
        //返回布局ID
        return R.layout.activity_demo;
    }


    //统一编码格式
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //各个初始化模块分开
        initToolBar();
        initIntent();
        initListener();
        //显示图片有两个方法一个请求一个设置
        queryPic();
        queryInfoList();
    }


    private void api() {
        //获取系统默认工具条的高度
        ScreenTool.getActionBarSize();
        //获取系统状态栏高度
        ScreenTool.getStatusBarHeight();

        //获取屏幕高度
        int heightPixels = ScreenTool.getScreenSize().heightPixels;

        //获取屏幕宽度
        int widthPixes = ScreenTool.getScreenSize().widthPixels;

        //屏幕真实大小包括状态栏和虚拟按钮
        int heightPixels1 = ScreenTool.getRealScreenSize().heightPixels;

        int strId = 0;
        //获取资源字符串
        Res.string(strId);
        //其他
        /*Res.color();Res.dimen();*/
        //dp转px
        float dp = 2;
        ViewTool.dip2px(dp);

        //日志打印
        HLog.e("TAG", "test", new Info(), new Info());
        //异常打印
        HLog.ex("TAG", new Exception("test"));
        //关闭日志
        HLog.canShow = false;
    }

    private void queryInfoList() {
        //查询信息
        String url = "test";
        //保证返回的数据格式正确
        //传入url，传入HCallback的实现类泛型填写需要返回的类型即可
        OKHttpTool.sendOKHttpRequest(url, new OKHttpTool.HCallBack<List<Info>>() {

            @Override
            public void onResponse(Call call, List<Info> infos) {
                //设置数据
                infoList = infos;
                //此时回调显示数据
                showInfoList();
            }


            @Override
            public void onFail(Call call, Exception e) {
                errorToast("服务器错误");
            }
        });
    }


    //显示的数据函数可以多次调用
    private void showInfoList() {
        if (this.infoList == null || infoList.size() <= 0) {
            //统一调用父类toast，方便以后修改提示样式
            toast("无数据显示");
        }

        //显示数据
    }

    private void queryPic() {
        String url = "test";
        //请求图片
        Glide.with(this).load(url).into(this.toolbarImg);
    }

    private void initListener() {

    }

    private void initIntent() {
        //初始化Intent
    }

    private void initToolBar() {
        this.setSupportActionBar(this.toolbar);
        this.toolbar.setTitle("test");
    }

}
