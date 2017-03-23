package com.reharu.ikaros.lingmar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.reharu.harubase.base.AutoInjecter;
import com.reharu.harubase.base.HaruActivity;
import com.reharu.harubase.tools.HLog;
import com.reharu.ikaros.R;
import com.reharu.ikaros.lingmar.adapter.HotelItemAdapter;
import com.reharu.ikaros.lingmar.adapter.PosPointAdapter;
import com.reharu.ikaros.lingmar.domain.Hotel;
import com.reharu.ikaros.lingmar.domain.PosPoint;
import com.reharu.ikaros.lingmar.utils.JSONTool;

import java.util.ArrayList;
import java.util.List;

public class QueryHotelActivity extends HaruActivity {

    @AutoInjecter.ViewInject(R.id.tv_city_titel)
    private TextView tv_city_titel;
    @AutoInjecter.ViewInject(R.id.listview_city)
    private ListView mListviewCity;
    @AutoInjecter.ViewInject(R.id.swipe_layout)
    private SwipeRefreshLayout swipeLayout;
    @AutoInjecter.ViewInject(R.id.drawerlayout)
    private DrawerLayout drawerlayout;

    // 侧滑栏中的控件
    @AutoInjecter.ViewInject(R.id.city_query_auto)
    private TextView queryAutoText;
    @AutoInjecter.ViewInject(R.id.et_queryCity)
    private EditText et_queryCity;
    @AutoInjecter.ViewInject(R.id.iv_queryCity)
    private ImageView iv_queryCity;
    @AutoInjecter.ViewInject(R.id.listview_AutoComplete)
    private ListView listview_AutoComplete;
    @AutoInjecter.ViewInject(R.id.drawer_left_layout)
    private View drawerLeft;

    private HotelItemAdapter hotelAdapter;
    private PosPointAdapter posPointAdapter;
    private List<Hotel> hotelList;
    private List<PosPoint> posPointList;
    // http://m.elong.com/hotel/api/list/?_rt=1489719603007&city=0101
    private String hotelURL = "http://m.elong.com/hotel/api/list/?_rt=1489719603007";
    private String posPointURL = "http://m.elong.com/hotel/api/gethotelsugcitys/?orientation=0&_rt=1489852209334&searchkey=";
    private String nowPage = "&pageindex=";
    private String cityId = "&city=";
    private String keyWords = "&keywords=";
    // 需要拼接具体的值
    private int pageIndex = 0;
    private String cityIdIndex = "0101";
    private String keyWordsIndex = "";

    @Override
    public int getContentViewId() {
        return R.layout.activity_query_hotel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adaptColor();
        initData();
        drawerListener();
    }

    private void adaptColor() {
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    private void initData() {
        initList();
        initHotel();
        initSwipeLayout();
        initDrawerLayout();

        // 向上滑动监听（是否要继续加载更多数据）
        mListviewCity.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
//                Log.d("123", "OnScrollListener " + i);
//                Log.d("123", "滑动 " + mListviewCity.getLastVisiblePosition());
//                Log.d("123", "hotelList.size(): " + hotelList.size());
                if (AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL == i
                        && mListviewCity.getLastVisiblePosition() == hotelList.size() - 1) {
                    try {
                        // 加载更多旅馆信息
                        Log.d("123", "updataHotel");
                        updataHotel();
                    } catch (Exception e) {
                        errorToast("没有更多数据了");
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
    }

    private void initList() {
        if (hotelList == null) {
            hotelList = new ArrayList<Hotel>();
        }
        if (posPointList == null) {
            posPointList = new ArrayList<PosPoint>();
        }
    }

    /**
     * 初始化左边滑动界面
     */
    private void initDrawerLayout() {
        et_queryCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String queryURL = posPointURL + charSequence;
                new PosPointAsyncTask().execute(queryURL);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * 初始化下拉刷新
     */
    private void initSwipeLayout() {
        swipeLayout.setColorSchemeResources(android.R.color.holo_purple, android.R.color.holo_blue_bright, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeLayout.setRefreshing(true);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 刷新信息

                        swipeLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
    }

    /**
     * 第一次进入页面初始化界面列表（默认北京）
     * 共享参数记录上一次选择的城市进行初始化
     */
    private void initHotel() {
        // 完成宾馆信息的查询
        Log.d("123", "查询");

        String queryURL = hotelURL + (nowPage + pageIndex) + (cityId + cityIdIndex) + (keyWords + keyWordsIndex);
        Log.d("123", "queryURL: " + queryURL);
        new NewHotelAsyncTask().execute(queryURL);
    }

    /**
     * 加载更多Hotel列表
     */
    private void updataHotel() {
        pageIndex++;

        String queryURL = hotelURL + (nowPage + pageIndex) + (cityId + cityIdIndex) + (keyWords + keyWordsIndex);
        new UpdataHotelAsyncTask().execute(queryURL);
    }

    public void cityChange(View view) {
        drawerlayout.openDrawer(drawerLeft);
    }

    /**
     * 侧滑中各种监听的集合
     */
    private void drawerListener() {
        listview_AutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 选择item后侧滑消失
                drawerlayout.closeDrawer(drawerLeft);

                PosPoint posPoint = posPointList.get(i);
                // 显示新地区旅馆时，页面重新变为0，并更新cityName和ID
                pageIndex = 0;
                cityIdIndex = posPoint.getCityId();
                keyWordsIndex = posPoint.getKeyWord();
                String queryURL = hotelURL + (nowPage + pageIndex) + (cityId + cityIdIndex) + (keyWords + keyWordsIndex);
                // 更新消息头
//                HLog.e("123", "posPoint.getKeyWord(): " + posPoint.getKeyWord().toString());
                String keyWord = posPoint.getKeyWord();
                String cityName = posPoint.getCityName();
                // 若keyWord为空则设置cityName
                if (TextUtils.isEmpty(keyWord)) {
                    tv_city_titel.setText(cityName);
                } else {
                    tv_city_titel.setText(keyWord);
                }

                swipeLayout.setRefreshing(true);
                new NewHotelAsyncTask().execute(queryURL);
            }
        });

        mListviewCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), HotelActivity.class);
                Hotel hotel = hotelList.get(i);
                intent.putExtra("hotelName", hotel.getHotelName());
                intent.putExtra("detailPageUrl", hotel.getDetailPageUrl());
                startActivity(intent);
                finish();
                overridePendingTransition(R.animator.anim_hotel_in, R.animator.anim_hotel_out);
            }
        });
    }

    /**
     * 异步加载旅馆列表数据
     */
    class UpdataHotelAsyncTask extends AsyncTask<String, Void, List<Hotel>> {

        @Override
        protected List<Hotel> doInBackground(String... strings) {
            return JSONTool.getHotelInfo(strings[0]);
        }

        @Override
        protected void onPostExecute(List<Hotel> hotels) {
            super.onPostExecute(hotels);

            // 使用同一对象
            hotelList.addAll(hotels);
            // 展示宾馆item
            if (hotelAdapter == null) {
                hotelAdapter = new HotelItemAdapter(getApplicationContext(), hotelList);
                mListviewCity.setAdapter(hotelAdapter);
            } else {
                hotelAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 异步加载新的旅馆列表数据
     */
    class NewHotelAsyncTask extends AsyncTask<String, Void, List<Hotel>> {

        @Override
        protected List<Hotel> doInBackground(String... strings) {
            return JSONTool.getHotelInfo(strings[0]);
        }

        @Override
        protected void onPostExecute(List<Hotel> hotels) {
            super.onPostExecute(hotels);

            swipeLayout.setRefreshing(false);
            // 使用同一对象
            hotelList.clear();
            hotelList.addAll(hotels);
//            HLog.d("123", "NewHotelAsyncTask hotels: " + hotels);
            HLog.d("123", "NewHotelAsyncTask hotelList: " + hotelList);
            // 展示宾馆item
            if (hotelAdapter == null) {
                hotelAdapter = new HotelItemAdapter(getApplicationContext(), hotelList);
                mListviewCity.setAdapter(hotelAdapter);
            } else {
                hotelAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 异步加载提示内容
     */
    class PosPointAsyncTask extends AsyncTask<String, Void, List<PosPoint>> {

        @Override
        protected List<PosPoint> doInBackground(String... strings) {
            return JSONTool.getPosPointInfo(strings[0]);
        }

        @Override
        protected void onPostExecute(List<PosPoint> posPoints) {
            super.onPostExecute(posPoints);

            posPointList.clear();
            posPointList.addAll(posPoints);
//            HLog.d("123", "posPointList: " + posPointList.toString());
            if (posPointAdapter == null) {
                PosPointAdapter posPointAdapter = new PosPointAdapter(getApplicationContext(), posPoints);
                listview_AutoComplete.setAdapter(posPointAdapter);
            } else {
                posPointAdapter.notifyDataSetChanged();
            }
        }
    }
}
