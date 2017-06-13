package com.reharu.ikaros.lingmar.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.reharu.harubase.tools.HLog;
import com.reharu.ikaros.R;
import com.reharu.ikaros.haru.activities.HCortanaActivity;
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

public class HotelFragment extends Fragment implements View.OnClickListener {


    private CollapsingToolbarLayout toolbar_hotel_title;
    private TextView tv_hotel_location;
    private TextView tv_checkin_time;
    private ListViewinNestScrollView listview_room_list;
    private SettingHotelServiceIcon shs_park;
    private SettingHotelServiceIcon shs_wifi;
    private SettingHotelServiceIcon shs_restaurant;
    private SettingHotelServiceIcon shs_shower;
    private ListViewinNestScrollView listview_comment;
    private LinearLayout linelayout_show_morecomment;
    // 停车相关的图标
    // 住户的评论

    private HotelInfo hotelInfo = new HotelInfo();
    private Pattern pattern = Pattern.compile("/hotel/(.*?)/#indate=");
    private final String roomsURL = "http://www.lingmar.cn:8080/getHotelInfo/hotelInfo.s?URL=http://m.elong.com/hotel/";
    private final String commentURL = "http://m.elong.com/hotel/api/otherdetail/?_rt=1489927854757&hotelid=";
    private RoomAdapter roomAdapter;
    private UserCommentAdapter userCommentAdapter;
    private List<UserComment> userCommentList;

    private View mView;

    private HCortanaActivity cortanaActivity ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.activity_hotel, null);
            cortanaActivity = (HCortanaActivity) getActivity();
        }

        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }
//        initSwipeLayout();
        initUI();
        adapterColor();
        getPreExtra();
        initData();
        return mView;
    }

    private void initUI() {
        toolbar_hotel_title = (CollapsingToolbarLayout) mView.findViewById(R.id.toolbar_hotel_title);
        tv_hotel_location = (TextView) mView.findViewById(R.id.tv_hotel_location);
        tv_checkin_time = (TextView) mView.findViewById(R.id.tv_checkin_time);
        listview_room_list = (ListViewinNestScrollView) mView.findViewById(R.id.listview_room_list);
        shs_park = (SettingHotelServiceIcon) mView.findViewById(R.id.shs_park);
        shs_wifi = (SettingHotelServiceIcon) mView.findViewById(R.id.shs_wifi);
        shs_restaurant = (SettingHotelServiceIcon) mView.findViewById(R.id.shs_restaurant);
        shs_shower = (SettingHotelServiceIcon) mView.findViewById(R.id.shs_shower);
        listview_comment = (ListViewinNestScrollView) mView.findViewById(R.id.listview_comment);
        linelayout_show_morecomment = (LinearLayout) mView.findViewById(R.id.linelayout_show_morecomment);
    }

    private void initData() {
        // 进入界面时缓冲条开始旋转

        // 初始化List数据
        if (userCommentList == null) {
            userCommentList = new ArrayList<UserComment>();
            initLVListener();
        }

        // 初始化旅馆列表
        String queryRoomsURL = roomsURL + hotelInfo.getHotelID();
        new NewRoomAsyncTask().execute(queryRoomsURL);
        // 初始化住客评论列表
        String queryCommentURL = commentURL + hotelInfo.getHotelID();
        new NewUserCommentAsyncTask().execute(queryCommentURL);

        linelayout_show_morecomment.setOnClickListener(this);
    }

    /**
     * 初始化宾馆ListView的更新
     */
    private void initLVListener() {
        listview_room_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Room room = hotelInfo.getRooms().get(i);
                // 跳转到相应的bookURL页面
                Bundle bundle = new Bundle();
                bundle.putString("bookURL", room.getBookUrl());
                cortanaActivity.startFragment(WebFragment.class, bundle);

            }
        });
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
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    public void getPreExtra() {
//        Intent intent = getActivity().getIntent();
//        hotelInfo.setHotelName(intent.getStringExtra("hotelName"));
//        hotelInfo.setDetailPageUrl(intent.getStringExtra("detailPageUrl"));
            if(getArguments() != null){
                hotelInfo.setHotelName(getArguments().getString("hotelName"));
                hotelInfo.setDetailPageUrl(getArguments().getString("detailPageUrl"));
                hotelInfo.setHotelID(getHotelID(hotelInfo.getDetailPageUrl()));
            }

//        Log.d("123", "hotelID " + hotelInfo.getHotelID());
//        Log.d("123", "detailPageUrl " + hotelInfo.getDetailPageUrl());
//        Log.d("123", "hotelName" + hotelInfo.getHotelName());
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
     * 点击查看更多按钮，跳转到评论的详细界面
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linelayout_show_morecomment:
                if (hotelInfo.getRooms() != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("hotelID", hotelInfo.getHotelID());
                    cortanaActivity.startFragment(HotelCommentFragment.class, bundle);
                }
                break;
        }
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
            Activity activity = getActivity() ;
            // 初始化客房列表
            if(activity != null){
                if (roomAdapter == null) {
                    roomAdapter = new RoomAdapter(activity.getApplicationContext(), hotelInfo.getRooms());
                    listview_room_list.setAdapter(roomAdapter);
                    setListViewHeightBasedOnChildren(listview_room_list);
                } else {
                    roomAdapter.notifyDataSetChanged();
                }
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
            Activity activity = getActivity() ;
            // Adapter展示用户评论
            if(activity != null){
                if (userCommentAdapter == null) {
                    userCommentAdapter = new UserCommentAdapter(activity.getApplicationContext(), userCommentList);
                    HLog.d("123", "userCommentList: " + userCommentList.size());
                    listview_comment.setAdapter(userCommentAdapter);
                    setListViewHeightBasedOnChildren(listview_comment);
                } else {
                    userCommentAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
