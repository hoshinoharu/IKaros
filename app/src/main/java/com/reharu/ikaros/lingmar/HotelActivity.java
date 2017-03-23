package com.reharu.ikaros.lingmar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.reharu.harubase.base.AutoInjecter;
import com.reharu.harubase.base.HaruActivity;
import com.reharu.harubase.tools.HLog;
import com.reharu.ikaros.R;
import com.reharu.ikaros.lingmar.adapter.RoomAdapter;
import com.reharu.ikaros.lingmar.adapter.UserCommentAdapter;
import com.reharu.ikaros.lingmar.domain.HotelInfo;
import com.reharu.ikaros.lingmar.domain.Room;
import com.reharu.ikaros.lingmar.domain.UserComment;
import com.reharu.ikaros.lingmar.utils.JSONTool;
import com.reharu.ikaros.lingmar.view.ListViewinNestScrollView;
import com.reharu.ikaros.lingmar.view.SettingHotelServiceIcon;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HotelActivity extends HaruActivity {

    @AutoInjecter.ViewInject(R.id.toolbar_hotel_title)
    private CollapsingToolbarLayout toolbar_hotel_title;

    @AutoInjecter.ViewInject(R.id.tv_hotel_location)
    private TextView tv_hotel_location;

    @AutoInjecter.ViewInject(R.id.tv_checkin_time)
    private TextView tv_checkin_time;

    @AutoInjecter.ViewInject(R.id.listview_room_list)
    private ListViewinNestScrollView listview_room_list;

    @AutoInjecter.ViewInject(R.id.shs_park)
    private SettingHotelServiceIcon shs_park;

    @AutoInjecter.ViewInject(R.id.shs_wifi)
    private SettingHotelServiceIcon shs_wifi;

    @AutoInjecter.ViewInject(R.id.shs_restaurant)
    private SettingHotelServiceIcon shs_restaurant;

    @AutoInjecter.ViewInject(R.id.shs_shower)
    private SettingHotelServiceIcon shs_shower;

    // 停车相关的图标
    // 住户的评论
    @AutoInjecter.ViewInject(R.id.listview_comment)
    private ListViewinNestScrollView listview_comment;

    private HotelInfo hotelInfo = new HotelInfo();
    private Pattern pattern = Pattern.compile("/hotel/(.*?)/#indate=");
    private final String roomsURL = "http://www.lingmar.cn:8080/getHotelInfo/hotelInfo.s?URL=http://m.elong.com/hotel/";
    private final String commentURL = "http://m.elong.com/hotel/api/otherdetail/?_rt=1489927854757&hotelid=";
    private RoomAdapter roomAdapter;
    private UserCommentAdapter userCommentAdapter;
    private List<UserComment> userCommentList;

    @Override
    public int getContentViewId() {
        return R.layout.activity_hotel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        initSwipeLayout();
        adapterColor();
        getPreExtra();
        initData();
    }

    private void initData() {
        // 进入界面时缓冲条开始旋转

        // 初始化List数据
        if (userCommentList == null) {
            userCommentList = new ArrayList<UserComment>();
        }

        // 初始化旅馆列表
        String queryRoomsURL = roomsURL + hotelInfo.getHotelID();
        new NewRoomAsyncTask().execute(queryRoomsURL);
        // 初始化住客评论列表
        String queryCommentURL = commentURL + hotelInfo.getHotelID();
        new NewUserCommentAsyncTask().execute(queryCommentURL);
    }

    /**
     * 初始化下拉刷新
     */
//    private void initSwipeLayout() {
//        swipe_room_layout.setColorSchemeResources(android.R.color.holo_purple, android.R.color.holo_blue_bright, android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);
//
//        swipe_room_layout.setRefreshing(true);
//        swipe_room_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        // 刷新信息
//
//                        swipe_room_layout.setRefreshing(false);
//                    }
//                }, 3000);
//            }
//        });
//    }

    /**
     * 初始化Hotel界面的 旅店名称、地址等数据
     */
    public void initHotelPage() {
        String hotelNmae = hotelInfo.getHotelName();
        String hotelLocal = hotelInfo.getHotelLocal();
        String hotelService = hotelInfo.getHotelService();

        toolbar_hotel_title.setTitle(hotelNmae);
        tv_hotel_location.setText(hotelLocal);
        Log.d("123", "hotelService: " + hotelService);

        if (!(hotelService.contains("24小时热水"))) {
            shs_shower.setVisibility(View.GONE);
        }
        if (!(hotelService.contains("免费wifi"))) {
            shs_wifi.setVisibility(View.GONE);
        }
        if (!(hotelService.contains("停车"))) {
            shs_park.setVisibility(View.GONE);
        }
        if (!(hotelService.contains("餐厅"))) {
            shs_restaurant.setVisibility(View.GONE);
        }
    }

    private void adapterColor() {
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    public void getPreExtra() {
        Intent intent = this.getIntent();
        hotelInfo.setHotelName(intent.getStringExtra("hotelName"));
        hotelInfo.setDetailPageUrl(intent.getStringExtra("detailPageUrl"));
        hotelInfo.setHotelID(getHotelID(hotelInfo.getDetailPageUrl()));

//        Log.d("123", "hotelID " + hotelInfo.getHotelID());
//        Log.d("123", "detailPageUrl " + hotelInfo.getDetailPageUrl());
//        Log.d("123", "hotelName" + hotelInfo.getHotelName());
    }

    /**
     * 点击查看更多按钮，跳转到评论的详细界面
     *
     * @param view
     */
    public void showMoreComment(View view) {
        Intent intent = new Intent(this, HotelCommentActivity.class);
        intent.putExtra("hotelID", hotelInfo.getHotelID());
        startActivity(intent);
        overridePendingTransition(R.animator.anim_hotel_in, R.animator.anim_hotel_out);
    }

    /**
     * 从链接中获取旅馆的ID
     *
     * @param msg
     * @return
     */
    public String getHotelID(String msg) {
        String result = "";
        Matcher matcher = pattern.matcher(msg);

        if (matcher.find()) {
            result = matcher.group(1);
        }
        Log.d("123", "result " + result);

        return result;
    }

    /**
     * 重新设置ListView的高度
     *
     * @param listView
     */
    private void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter(); //获得Adapter
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 初始化客房列表
     */
    class NewRoomAsyncTask extends AsyncTask<String, Void, HotelInfo> {

        @Override
        protected HotelInfo doInBackground(String... strings) {
            return JSONTool.getHotelInfoByLingmar(strings[0]);
        }

        @Override
        protected void onPostExecute(HotelInfo hotel) {
            super.onPostExecute(hotel);

            String local = hotel.getHotelLocal();
            String openTime = hotel.getHotelOpenTime();
            String phone = hotel.getHotelPhone();
            String service = hotel.getHotelService();
            List<Room> rooms = hotel.getRooms();

//            Log.d("123", hotel.toString());
            hotelInfo.setHotelLocal(local);
            hotelInfo.setHotelOpenTime(openTime);
            hotelInfo.setHotelPhone(phone);
            hotelInfo.setHotelService(service);
            hotelInfo.setRooms(rooms);
            // 更新页面信息
            initHotelPage();

            Log.d("123", "onPostExecute getRooms: " + hotelInfo.getRooms().toString());
            // 初始化客房列表
            if (roomAdapter == null) {
                roomAdapter = new RoomAdapter(getApplicationContext(), hotelInfo.getRooms());
                listview_room_list.setAdapter(roomAdapter);
                setListViewHeightBasedOnChildren(listview_room_list);
            } else {
                roomAdapter.notifyDataSetChanged();
            }

//            swipe_room_layout.setRefreshing(false);
        }
    }

    /**
     * 初始化用户评价列表
     */
    class NewUserCommentAsyncTask extends AsyncTask<String, Void, List<UserComment>> {

        @Override
        protected List<UserComment> doInBackground(String... strings) {
            return JSONTool.getUserComment(strings[0]);
        }

        @Override
        protected void onPostExecute(List<UserComment> userComments) {
            super.onPostExecute(userComments);

            userCommentList.clear();
            userCommentList.addAll(userComments);
            // Adapter展示用户评论
            if (userCommentAdapter == null) {
                userCommentAdapter = new UserCommentAdapter(getApplicationContext(), userCommentList);
                HLog.d("123", "userCommentList: " + userCommentList.size());
                listview_comment.setAdapter(userCommentAdapter);
                setListViewHeightBasedOnChildren(listview_comment);
            } else {
                userCommentAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, QueryHotelActivity.class);
        startActivity(intent);
        overridePendingTransition(R.animator.anim_back_hotel_in, R.animator.anim_back_hotel_out);
        finish();
    }
}
