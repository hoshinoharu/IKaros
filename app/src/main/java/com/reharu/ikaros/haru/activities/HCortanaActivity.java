package com.reharu.ikaros.haru.activities;

import android.animation.Animator;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.GridLayoutAnimationController;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;

import com.orange.engine.camera.ZoomCamera;
import com.orange.engine.device.Device;
import com.orange.engine.options.PixelPerfectEngineOptions;
import com.orange.engine.options.PixelPerfectMode;
import com.orange.engine.options.ScreenOrientation;
import com.orange.input.touch.TouchEvent;
import com.orange.opengl.font.BitmapFont;
import com.orange.res.RegionRes;
import com.orange.res.SoundRes;
import com.orange.ui.activity.GameActivity;
import com.orange.ui.launcher.GameLauncher;
import com.reharu.harubase.base.AutoInjecter;
import com.reharu.harubase.tools.ActivityTool;
import com.reharu.harubase.tools.AnimeTool;
import com.reharu.harubase.tools.Constant;
import com.reharu.harubase.tools.HLog;
import com.reharu.harubase.tools.OKHttpTool;
import com.reharu.harubase.tools.ScreenTool;
import com.reharu.harubase.tools.StreamTool;
import com.reharu.ikaros.R;
import com.reharu.ikaros.haru.components.HaruCoordinatorLayout;
import com.reharu.ikaros.haru.components.OpIconAdapter;
import com.reharu.ikaros.haru.components.SpeackVolumeChangeListener;
import com.reharu.ikaros.haru.components.SpeakVolumeDialog;
import com.reharu.ikaros.haru.cortana.Cortana;
import com.reharu.ikaros.haru.cortana.CortanaAnimResManager;
import com.reharu.ikaros.haru.cortana.SoundMannager;
import com.reharu.ikaros.haru.cortana.adapter.ChartRecordaAdapter;
import com.reharu.ikaros.haru.cortana.behavior.Feeling;
import com.reharu.ikaros.haru.cortana.dto.ChartRecord;
import com.reharu.ikaros.haru.cortana.dto.OpIcon;
import com.reharu.ikaros.haru.cortana.dto.SpeackContent;
import com.reharu.ikaros.haru.cortana.listener.CortanaListener;
import com.reharu.ikaros.haru.cortana.scene.CortanaScene;
import com.reharu.ikaros.haru.cortana.sprite.WaveSprite;
import com.reharu.ikaros.haru.sound.SpeechTool;
import com.reharu.ikaros.haru.tools.GsonTool;
import com.reharu.ikaros.haru.tools.Location;
import com.reharu.ikaros.haru.tools.LocationGetter;
import com.reharu.ikaros.haru.weather.service.WeatherService;
import com.reharu.ikaros.haru.weather.vo.Weather;
import com.reharu.ikaros.imxz.fragment.Fragment_Main;
import com.reharu.ikaros.lingmar.fragment.QueryHotelFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class HCortanaActivity extends GameActivity implements AutoInjecter.AutoInjectable, OpIconAdapter.OnErrorListener {
    /*数据相关*/
    private AnimationDrawable cortanaAppera_0;
    private AnimationDrawable cortanaNormalBlink;
    private static BitmapFont bitmapFont;
    private CortanaScene cortanaScene;
    private Cortana cortana;
    private ChartRecordaAdapter recordAdapter ;

    private Location mainlocation ;
    private Weather locWeather ;
    private int initTimes = 0 ;
    private int allInitTimes = 2 ;

    //出场动画
    private Animator apperaAnim ;

    private List<View> allIcons ;

    private Animation layoutAnimation;

    /*视图相关*/
    private HaruCoordinatorLayout cortanaInterface;

    @AutoInjecter.ViewInject(R.id.chartLayout) private CoordinatorLayout chartLayout;
    @AutoInjecter.ViewInject(R.id.operateLayout) private CoordinatorLayout operateLayout;
    @AutoInjecter.ViewInject(R.id.chartRecordList) private ListView chartRecordList ;
    @AutoInjecter.ViewInject(R.id.inputText) private EditText inputText ;
    @AutoInjecter.ViewInject(R.id.sendBtn) private View sendBtn ;
    @AutoInjecter.ViewInject(R.id.voiceBtn) private View voiceBtn ;
    @AutoInjecter.ViewInject(R.id.recordListContainer) private ViewGroup recordListContainer ;

    /*显示的图标*/
    @AutoInjecter.ViewInject(R.id.topGrid) private GridView topGrid ;
    @AutoInjecter.ViewInject(R.id.leftGrid) private GridView leftGrid ;
    @AutoInjecter.ViewInject(R.id.rightGrid) private GridView rightGrid ;


    private SpeakVolumeDialog speakVolumeDialog ;
    private SpeackVolumeChangeListener speackVolumeChangeListener;

    @Override
    public GameLauncher CreateGameLauncher() {
        HLog.e("TAG", "onCreateLauncher");
        return new GameLauncher() {
            @Override
            protected PixelPerfectEngineOptions onCreatePixelPerfectEngineOptions() {
                PixelPerfectEngineOptions pixelPerfectEngineOptions = new PixelPerfectEngineOptions(ZoomCamera.class);
                pixelPerfectEngineOptions
                        .setScreenOrientation(ScreenOrientation.PORTRAIT_FIXED); // 设置竖屏
                pixelPerfectEngineOptions
                        .setPixelPerfectMode(PixelPerfectMode.CHANGE_HEIGHT);// 适配模式,这里设置为“保持宽度不变，改变高”
                pixelPerfectEngineOptions.setDesiredSize(ScreenTool.getScreenSize().widthPixels);// 参考尺寸
                return pixelPerfectEngineOptions;
            }


            @Override
            protected void onLoadResources() {
                HLog.e("TAG", "loadStart");
                /*加载动画纹理*/
                RegionRes.loadTexturesFromAssets(CortanaAnimResManager.APPEAR_0_XML);
                RegionRes.loadTexturesFromAssets(CortanaAnimResManager.NORMAL_BLINK_XML);
                RegionRes.loadTexturesFromAssets(CortanaAnimResManager.HAPPY_XML);
                RegionRes.loadTexturesFromAssets(CortanaAnimResManager.LOVE_XML);
                RegionRes.loadTexturesFromAssets(CortanaAnimResManager.LOVE_XML);
                RegionRes.loadTexturesFromAssets(CortanaAnimResManager.JUMP_XML);
                RegionRes.loadTexturesFromAssets(CortanaAnimResManager.ROTATE_XML);
                RegionRes.loadTexturesFromAssets(CortanaAnimResManager.DOUBT_XML);
                RegionRes.loadTexturesFromAssets(CortanaAnimResManager.BLINK_XML);
                RegionRes.loadTexturesFromAssets(WaveSprite.WAVE_XML);
                SoundRes.loadSoundFromAssets(SoundMannager.LISTENING_START, "sound/cortana_start.mp3");
                SoundRes.loadSoundFromAssets(SoundMannager.LISTENING_END, "sound/cortana_end.mp3");
            }

            //加载字体
            protected void loadFont() {
                bitmapFont = new BitmapFont(this.getEngine().getTextureManager(), Device.getDevice().getFileManage(), "font/haruFont.fnt");
                bitmapFont.load();
            }

            @Override
            protected void onLoadComplete() {
                HLog.e("TAG", "loadComplete");
                cortanaScene = (CortanaScene) this.startScene(CortanaScene.class);
                cortanaScene.setOnCreatedListener(new CortanaScene.OnCreatedListener() {
                    @Override
                    public void onCreated(CortanaScene scene) {
                        Constant.MainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                onCreatedCortanaInterface();
                                onCreateOperateInterface();
                            }


                        });
                    }
                });
            }

            @Override
            protected boolean onPrepareFinish() {
                HLog.e("TAG", "onPrepareFinish");
                return super.onPrepareFinish();
            }
        };
    }



    private void initAnimator() {
        layoutAnimation = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        layoutAnimation.setDuration(500);
        layoutAnimation.setStartOffset(500);
        layoutAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        AnimeTool.showAnime(chartLayout,true).start();
    }

    private void initIcon() {
        int topMaxIcon = 4 ;
        List<OpIcon> iconList = Arrays.asList(
                    new OpIcon(R.drawable.ic_alarm, "准点提醒"),
                    new OpIcon(R.drawable.ic_hotel, "酒店住宿", QueryHotelFragment.class),
                    new OpIcon(R.drawable.ic_plane, "机票车票", Fragment_Main.fragms[5]),
                    new OpIcon(R.drawable.ic_loc, "地理位置"),
                    new OpIcon(R.drawable.ic_weather, "天气预报"),
                    new OpIcon(R.drawable.ic_shopping, "购物剁手")
                );
        List<OpIcon> topList  =iconList.subList(0, topMaxIcon) ;
        if(iconList.size() > 4){
            int surplus = iconList.size() -topMaxIcon ;
            int leftIcon = surplus/2 + surplus%2==0?0:1 ;
            int right = surplus - leftIcon ;
            List<OpIcon> leftList = iconList.subList(topMaxIcon, topMaxIcon+leftIcon) ;
            List<OpIcon> rightList = iconList.subList(topMaxIcon+leftIcon, iconList.size()) ;
            if(rightList.size() > 0){
                OpIconAdapter rightAdapter = new OpIconAdapter(rightList, this, this);
                rightGrid.setAdapter(rightAdapter);
            }
            leftGrid.setAdapter(new OpIconAdapter(leftList, this, this));
        }
        topGrid.setAdapter(new OpIconAdapter(topList, this, this));
        topGrid.setLayoutAnimation(new GridLayoutAnimationController(layoutAnimation, 0, 0));
        leftGrid.setLayoutAnimation(new GridLayoutAnimationController(layoutAnimation, 0, 0));
        rightGrid.setLayoutAnimation(new GridLayoutAnimationController(layoutAnimation, 0, 0));
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LocationGetter.init(this);
        SpeechTool.init(this, getString(R.string.appid));

        queryLoc();

        inflateUI();
        //初始化出场动画
        initAnimator();

        initIcon();

        initLayoutAnimation();

    }

    public void startFragment(Class<? extends Fragment> fragCls){
        this.startFragment(fragCls, null);
    }

    public void startFragment(Class<? extends Fragment> fragCls, Bundle bundle){
        Fragment fragment = null;
        try {
            fragment = fragCls.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.startFragment(fragment, bundle);

    }

    public void startFragment(Fragment fragment){
        this.startFragment(fragment, null);
    }

    public void startFragment(Fragment fragment, Bundle bundle){
        if(fragment != null){
            if(bundle != null){
                fragment.setArguments(bundle);
            }
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.add(R.id.fragmentContent, fragment);
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
            fragmentTransaction.commit();
        }
    }

    private void initLayoutAnimation() {
        ((ViewGroup)sendBtn.getParent()).setLayoutAnimation(new LayoutAnimationController(layoutAnimation));
        recordListContainer.setLayoutAnimation(new LayoutAnimationController(layoutAnimation));
    }

    private void inflateUI() {
        ViewGroup rootView = ActivityTool.getRootView(HCortanaActivity.this);
        //加载UI交互界面
        cortanaInterface = (HaruCoordinatorLayout) LayoutInflater.from(HCortanaActivity.this).inflate(getCorinterfaceLayoutId(), rootView, false);
        rootView.addView(cortanaInterface);
        cortanaInterface.setBackgroundColor(Color.BLACK);

        AutoInjecter.autoInjectAllField(HCortanaActivity.this);


        operateLayout.getLayoutParams().height = ScreenTool.getScreenSize().heightPixels/2 ;
        chartLayout.getLayoutParams().height = ScreenTool.getScreenSize().heightPixels / 2;


        int halfWidth = ScreenTool.getScreenSize().widthPixels/2 ;
        leftGrid.getLayoutParams().width = halfWidth ;
        rightGrid.getLayoutParams().width = halfWidth ;


        speakVolumeDialog = new SpeakVolumeDialog(this);
    }
    private void onCreateOperateInterface() {
        ActivityTool.getRootView(HCortanaActivity.this).bringChildToFront(cortanaInterface);
        cortanaInterface.setBackgroundColor(Color.parseColor("#00000000"));
        showWelcomeMessage();
    }

    private void queryLoc(){
        LocationGetter.getLoc(new LocationGetter.ResponseListener(){
            @Override
            public void onResponse(Location location) {
                mainlocation = location ;
                if(location.isSuccess){
                    queryWeather();
                }else {
                    showWelcomeMessage();
                }
            }
        });
    }

    public void queryWeather(){
        WeatherService.get().queryNow(mainlocation.cityName, new OKHttpTool.HCallBack<Weather>() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try{
                    String json = StreamTool.getString(response.body().byteStream());
                    HLog.e("TAG", json);
                    response.close();
                    onResponse(call, GsonTool.getGson().fromJson(json, Weather.class));
                }catch (Exception e){
                    onFail(call, e);
                }
            }

            @Override
            public void onResponse(Call call, Weather weather) {
                locWeather = weather ;
                showWelcomeMessage();
            }

            @Override
            public void onFail(Call call, Exception e) {
                HLog.ex("TAG", e);
                showWelcomeMessage();
            }
        });

    }
    protected void onCreatedCortanaInterface() {
        cortana = cortanaScene.getCortana();
        initView();
        //初始化监听
        initListener();
    }




    private void initView() {
        recordAdapter = new ChartRecordaAdapter(chartRecordList, new ArrayList<ChartRecord>()) ;
        chartRecordList.setAdapter(recordAdapter);
    }

    private void initListener() {

        cortana.addCortanaListener(new CortanaListener.Adapter() {
            @Override
            public void onSpeack(Cortana cortana, final String content) {
                Constant.MainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        recordAdapter.addRecord(new ChartRecord(content, ChartRecord.CORTANA));
                    }
                }) ;
            }
        });
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verifyInput()){
                    talkToCortana(inputText.getText());
                }
            }
        });
        cortanaInterface.setOnDispatchTouchEventListener(new HaruCoordinatorLayout.OnDispatchTouchEventListener() {
            @Override
            public void onDispatchTouchEvent(MotionEvent event) {
                TouchEvent event1 = TouchEvent.obtain(event.getRawX(), event.getRawY(), event.getAction(), 0) ;
                cortanaScene.onTouch(event1, event.getRawX(), event.getRawY());
                event1.recycle();
            }
        });
        voiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakVolumeDialog.show();
            }
        });


        speackVolumeChangeListener = new SpeackVolumeChangeListener(this, speakVolumeDialog, cortana);
        speakVolumeDialog.setListener(speackVolumeChangeListener);
    }


    public static BitmapFont getBitmapFont() {
        return bitmapFont;
    }

    @Override
    public View findInjectViewById(int resId) {
        return cortanaInterface.findViewById(resId);
    }

    public int getCorinterfaceLayoutId() {
        return R.layout.activity_hcortana;
    }


    public void talkToCortana(CharSequence msg){
        inputText.setText("");
        recordAdapter.addRecord(new ChartRecord(msg, ChartRecord.MASTER));
        cortana.getMessage(msg);
    }

    public boolean verifyInput(){
        CharSequence charSequence = inputText.getText() ;
        if(charSequence.equals("")){
            return false ;
        }else {
            return true ;
        }
    }

    private void showWelcomeMessage(){
        //判断是否调用两次，调用两次表示小娜已经加载完毕天气也已经查询到
        initTimes++ ;
        if(initTimes >= allInitTimes){
            try{
                if(locWeather != null){
                    cortana.speak(new SpeackContent(Feeling.HAPPY, "主人，现在"+(mainlocation==null?"":mainlocation.cityName)+"的天气是：\n"+locWeather.spInfo()));
                }else {
                    cortana.speak(new SpeackContent(Feeling.SAD,"服务器出现问题了"));
                }
            }catch (Exception e){
                HLog.ex("TAG", e);
            }
        }
    }


    @Override
    public void onError() {
        if(cortana != null) {
            cortana.reportError("该功能暂未实现，真是抱歉呢");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(getFragmentManager().getBackStackEntryCount() >= 1) {
                getFragmentManager().popBackStack() ;
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
