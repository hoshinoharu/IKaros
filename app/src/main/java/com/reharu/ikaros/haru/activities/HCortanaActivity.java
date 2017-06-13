package com.reharu.ikaros.haru.activities;

import android.animation.Animator;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.why168.LoopViewPagerLayout;
import com.github.why168.listener.OnLoadImageViewListener;
import com.github.why168.modle.BannerInfo;
import com.github.why168.modle.IndicatorLocation;
import com.github.why168.modle.LoopStyle;
import com.orange.engine.camera.ZoomCamera;
import com.orange.engine.device.Device;
import com.orange.engine.options.PixelPerfectEngineOptions;
import com.orange.engine.options.PixelPerfectMode;
import com.orange.engine.options.ScreenOrientation;
import com.orange.input.touch.TouchEvent;
import com.orange.opengl.font.BitmapFont;
import com.orange.res.RegionRes;
import com.orange.res.SoundRes;
import com.orange.ui.launcher.GameLauncher;
import com.reharu.harubase.base.AutoInjecter;
import com.reharu.harubase.base.HaruApp;
import com.reharu.harubase.tools.ActivityTool;
import com.reharu.harubase.tools.AnimeTool;
import com.reharu.harubase.tools.Constant;
import com.reharu.harubase.tools.HLog;
import com.reharu.harubase.tools.OKHttpTool;
import com.reharu.harubase.tools.ScreenTool;
import com.reharu.harubase.tools.StreamTool;
import com.reharu.harubase.tools.ViewTool;
import com.reharu.ikaros.R;
import com.reharu.ikaros.haru.components.BGARNewsAdapter;
import com.reharu.ikaros.haru.components.GridSpacingItemDecoration;
import com.reharu.ikaros.haru.components.HaruCoordinatorLayout;
import com.reharu.ikaros.haru.components.OpIconAdapter;
import com.reharu.ikaros.haru.components.SpeackVolumeChangeListener;
import com.reharu.ikaros.haru.components.SpeakVolumeDialog;
import com.reharu.ikaros.haru.cortana.Cortana;
import com.reharu.ikaros.haru.cortana.CortanaAnimResManager;
import com.reharu.ikaros.haru.cortana.CortanaFeelingController;
import com.reharu.ikaros.haru.cortana.OrderDispatcher;
import com.reharu.ikaros.haru.cortana.SoundMannager;
import com.reharu.ikaros.haru.cortana.TulingRespHandler;
import com.reharu.ikaros.haru.cortana.adapter.ChartRecordaAdapter;
import com.reharu.ikaros.haru.cortana.behavior.Feeling;
import com.reharu.ikaros.haru.cortana.dto.ChartRecord;
import com.reharu.ikaros.haru.cortana.dto.OpIcon;
import com.reharu.ikaros.haru.cortana.dto.SpeackContent;
import com.reharu.ikaros.haru.cortana.listener.CortanaListener;
import com.reharu.ikaros.haru.cortana.scene.CortanaScene;
import com.reharu.ikaros.haru.cortana.sprite.WaveSprite;
import com.reharu.ikaros.haru.fragment.Splash;
import com.reharu.ikaros.haru.news.News;
import com.reharu.ikaros.haru.news.NewsResult;
import com.reharu.ikaros.haru.news.service.NewsService;
import com.reharu.ikaros.haru.sound.SpeechTool;
import com.reharu.ikaros.haru.tools.GsonTool;
import com.reharu.ikaros.haru.tools.Location;
import com.reharu.ikaros.haru.tools.LocationGetter;
import com.reharu.ikaros.haru.weather.service.WeatherService;
import com.reharu.ikaros.haru.weather.vo.Weather;
import com.reharu.ikaros.imxz.fragment.Fragment_Main;
import com.reharu.ikaros.lingmar.fragment.QueryHotelFragment;
import com.reharu.ikaros.lingmar.fragment.ShopFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGAStickinessRefreshViewHolder;
import okhttp3.Call;
import okhttp3.Response;

public class HCortanaActivity extends HaruGameActivity implements AutoInjecter.AutoInjectable, OpIconAdapter.OnErrorListener {
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

    private ArrayList<BannerInfo> bannerInfoList ;
    private List<News> newsList = new ArrayList<>();
    private BGARNewsAdapter newsAdaoter;

    //出场动画
    private Animator apperaAnim ;

    private List<View> allIcons ;

    private Animation layoutAnimation;

    /*视图相关*/
    private HaruCoordinatorLayout cortanaInterface;

    @AutoInjecter.ViewInject(R.id.chartLayout) private CoordinatorLayout chartLayout;
    @AutoInjecter.ViewInject(R.id.chartRecordList) private ListView chartRecordList ;
    @AutoInjecter.ViewInject(R.id.inputText) private EditText inputText ;
    @AutoInjecter.ViewInject(R.id.sendBtn) private View sendBtn ;
    @AutoInjecter.ViewInject(R.id.voiceBtn) private View voiceBtn ;
    @AutoInjecter.ViewInject(R.id.recordListContainer) private ViewGroup recordListContainer ;
    @AutoInjecter.ViewInject(R.id.bannerLayout) private LoopViewPagerLayout bannerLayout ;
    @AutoInjecter.ViewInject(R.id.bannerTitle) private TextView bannerTitle;
    @AutoInjecter.ViewInject(R.id.newsList) private RecyclerView newsListView;
    @AutoInjecter.ViewInject(R.id.opLayout) private ViewGroup opLayout ;
    @AutoInjecter.ViewInject(R.id.bgarLayout) private BGARefreshLayout bgarLayout ;
    @AutoInjecter.ViewInject(R.id.fragmentContent) private View fragmentContent ;
    @AutoInjecter.ViewInject(R.id.chartBtn) private FloatingActionButton chartBtn ;
    /*显示的图标*/
    @AutoInjecter.ViewInject(R.id.icContainer) private RecyclerView icContainer ;


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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LocationGetter.init(this);
        SpeechTool.init(this, getString(R.string.appid));

        inflateUI();

        showSplash();

        queryBanners();

        initNewsList();
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
            HLog.e("TAG", fragment.isHidden());
            fragment.setUserVisibleHint(true);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.add(R.id.fragmentContent, fragment);
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
            fragmentTransaction.commit();
        }
    }

    public void initNewsList(){
        bgarLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                refreshLayout.endRefreshing();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                HLog.e("TAG", "loadMore");
                queryNews();
                return true;
            }
        });
        BGAStickinessRefreshViewHolder stickinessRefreshViewHolder = new BGAStickinessRefreshViewHolder(HaruApp.context(), true);
        stickinessRefreshViewHolder.setStickinessColor(R.color.colorPrimary);
        stickinessRefreshViewHolder.setRotateImage(R.drawable.bga_refresh_stickiness);
        bgarLayout.setRefreshViewHolder(stickinessRefreshViewHolder);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        newsListView.getLayoutParams().height=ScreenTool.getScreenSize().heightPixels ;
        newsListView.setLayoutManager(layoutManager);
        newsAdaoter = new BGARNewsAdapter(newsListView,newsList);
        newsListView.setAdapter(newsAdaoter.getHeaderAndFooterAdapter());
        ViewTool.removeView(opLayout);
        newsAdaoter.addHeaderView(opLayout);
        queryNews();
    }

    private void queryNews(){
        int pageSize = 10 ;
        int page = newsList.size()/pageSize+1;
        HLog.e("TAG", "load");
        NewsService.queryNews(News.EDU, page, pageSize, new OKHttpTool.HCallBack<NewsResult>() {
            @Override
            public void onResponse(Call call, NewsResult newsResult) {
                bgarLayout.endLoadingMore();
                newsAdaoter.addMoreData(newsResult.list);
            }

            @Override
            public void onFail(Call call, Exception e) {
                onError(getString(R.string.server_error));
            }
        });
    }

    private void queryBanners(){
        NewsService.queryNews(News.TRAVEL, 1, 4, new OKHttpTool.HCallBack<NewsResult>() {


            @Override
            public void onResponse(Call call, NewsResult newsResult) {
                bannerInfoList = new ArrayList<>() ;
                for(News news : newsResult.list){
                    bannerInfoList.add(new BannerInfo<News>(news, news.getTitle())) ;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showBanner();
                    }
                });
            }

            @Override
            public void onFail(Call call, Exception e) {
                HLog.ex("TAG", e);
               onError(getString(R.string.server_error));
            }
        });
    }

    public  void showBanner(){
        if(bannerInfoList == null){
            return;
        }
        this.bannerLayout.getLayoutParams().height = ScreenTool.getScreenSize().widthPixels/5*3;
        this.bannerLayout.setLoop_ms(2000);
        this.bannerLayout.setLoop_duration(1000);
        this.bannerLayout.setLoop_style(LoopStyle.Depth);
        this.bannerLayout.setIndicatorLocation(IndicatorLocation.Center);
        this.bannerLayout.initializeData(this);
        this.bannerLayout.setOnLoadImageViewListener(new OnLoadImageViewListener() {
            @Override
            public ImageView createImageView(Context context) {
                ImageView imageView = new ImageView(context) ;
                imageView.setLayoutParams(new LoopViewPagerLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                return imageView;
            }

            @Override
            public void onLoadImageView(ImageView imageView, Object parameter) {
                News news = (News) parameter;
                Glide.with(imageView.getContext()).load(news.getImgurl()).into(imageView);
            }
        });
        this.bannerLayout.getLoopViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                int index = i%bannerInfoList.size();
                changeBannerTitle(bannerInfoList.get(index).title);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        this.bannerLayout.setLoopData(bannerInfoList);
        this.bannerLayout.startLoop();
    }

    private void changeBannerTitle(String title) {
        bannerTitle.setText(title);
    }

    private boolean showChartLayout = false ;
    private void initAnimator() {
        layoutAnimation = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        layoutAnimation.setDuration(500);
        layoutAnimation.setStartOffset(500);
        layoutAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
//        AnimeTool.showAnime(chartLayout,true).start();
        chartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toogleShowChart();
            }
        });
    }

    private void toogleShowChart() {
        if(showChartLayout){
            AnimeTool.dismissAnime(chartLayout).start();
            showChartLayout = false ;
        }else {
            AnimeTool.showAnime(chartLayout, true).start();
            showChartLayout = true ;
        }
    }
    private void showChart(){
        if(chartLayout.getVisibility()==View.VISIBLE){
            return;
        }
        AnimeTool.showAnime(chartLayout, true).start();
        showChartLayout = true ;
    }

    private void initIcon() {
        List<OpIcon> iconList = Arrays.asList(
                new OpIcon(R.drawable.ic_alarm, "准点提醒", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onError();
                    }
                }, R.drawable.bgb2w),
                new OpIcon(R.drawable.ic_hotel, "酒店住宿", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startFragment(QueryHotelFragment.class);
                    }
                }, R.drawable.bgp2r),
                new OpIcon(R.drawable.ic_plane, "机票车票", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startFragment(Fragment_Main.fragms[5]);
                    }
                }, R.drawable.bgg2w),
                new OpIcon(R.drawable.ic_loc, "地理位置", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        queryLoc();
                    }
                }, R.drawable.bgp2w),
                new OpIcon(R.drawable.ic_weather, "天气预报", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        preQueryWeather();
                    }
                }, R.drawable.bgr2w),
                new OpIcon(R.drawable.ic_shopping, "购物剁手", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startFragment(ShopFragment.class);
                    }
                }, R.drawable.bgy2w)
        );
        final int colCount = 4 ;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        icContainer.setLayoutManager(gridLayoutManager);
        int spanCount = 4;//跟布局里面的spanCount属性是一致的
        int spacing = 2;//每一个矩形的间距
        boolean includeEdge = true;//如果设置成false那边缘地带就没有间距s
        //设置每个item间距
        icContainer.addItemDecoration(new GridSpacingItemDecoration(spanCount,spacing,includeEdge));
        icContainer.setAdapter(new OpIconAdapter(iconList, this, this));
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
        AutoInjecter.autoInjectAllField(HCortanaActivity.this);


        int halfWidth = ScreenTool.getScreenSize().widthPixels/2 ;

        speakVolumeDialog = new SpeakVolumeDialog(this);
    }
    private void onCreateOperateInterface() {
        ActivityTool.getRootView(HCortanaActivity.this).bringChildToFront(cortanaInterface);
    }

    private void queryLoc(){
        if(cortana == null){
            return;
        }
        if(mainlocation == null) {
            LocationGetter.getLoc(new LocationGetter.ResponseListener() {
                @Override
                public void onResponse(Location location) {
                    mainlocation = location;
                    if (location.isSuccess) {
                        HLog.e("TAG", location);
                        cortana.speak(new SpeackContent(Feeling.HAPPY, "现在主人的位置是:" + location.getLoc()));
                    } else {
                        cortana.reportError(getString(R.string.server_error));
                    }
                }
            });
        }else {
            cortana.speak(new SpeackContent(Feeling.HAPPY, "现在主人的位置是:" + mainlocation.getLoc()));
        }
    }

    public void preQueryWeather(){
        if(cortana == null){
            return;
        }
        if(mainlocation == null){
            LocationGetter.getLoc(new LocationGetter.ResponseListener() {
                @Override
                public void onResponse(Location location) {
                    mainlocation = location ;
                    if(location.isSuccess){
                        queryWeather();
                    }else {
                        cortana.reportError(getString(R.string.server_error));
                    }
                }
            });
        }else {
            queryWeather();
        }
    }

    private void queryWeather(){
        if(mainlocation == null){
            return;
        }
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
                cortana.speak(new SpeackContent(Feeling.HAPPY, "主人现在的天气是:\n"+weather.spInfo()));
            }

            @Override
            public void onFail(Call call, Exception e) {
                HLog.ex("TAG", e);
                cortana.reportError(getString(R.string.server_error));
            }
        });
    }

    protected void onCreatedCortanaInterface() {
        cortana = cortanaScene.getCortana();
        dismissSplash();
        initController();
        initView();
        //初始化监听
        initListener();
    }

    private void initController() {
        //初始化
        cortana.addCortanaListener(new CortanaFeelingController(cortana.getAnimResManager()));
        cortana.addCortanaListener(new TulingRespHandler(cortana, this));
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
                        showChart();
                        recordAdapter.addRecord(new ChartRecord(content, ChartRecord.CORTANA));
                    }
                }) ;
            }
        });

        cortana.addCortanaListener(new OrderDispatcher(this), 0);

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


    public void talkToCortana(final CharSequence msg){
        Constant.MainHandler.post(new Runnable() {
            @Override
            public void run() {
                inputText.setText("");
                recordAdapter.addRecord(new ChartRecord(msg, ChartRecord.MASTER));
                cortana.getMessage(msg);
            }
        }) ;
    }

    public boolean verifyInput(){
        CharSequence charSequence = inputText.getText() ;
        if(charSequence.equals("")){
            return false ;
        }else {
            return true ;
        }
    }

    private void showWelcomeMessa(){
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
        onError("该功能暂未实现，真是抱歉呢");
    }

    public void onError(String msg){
        if(cortana != null){
            cortana.reportError(msg);
        }
    }

    public void showSplash(){
        startFragment(Splash.class);
        setCanPopfrag(false);
        chartBtn.setVisibility(View.GONE);
    }

    public void dismissSplash(){
        popFragmentBackStack() ;
        setCanPopfrag(true);
        chartBtn.setVisibility(View.VISIBLE);
    }
}
