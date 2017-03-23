package com.reharu.ikaros.lingmar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;

import com.reharu.harubase.base.AutoInjecter;
import com.reharu.harubase.base.HaruActivity;
import com.reharu.ikaros.R;
import com.reharu.ikaros.lingmar.adapter.UserCommentAdapter;
import com.reharu.ikaros.lingmar.domain.UserComment;
import com.reharu.ikaros.lingmar.utils.JSONTool;

import java.util.ArrayList;
import java.util.List;

public class HotelCommentActivity extends HaruActivity implements AbsListView.OnScrollListener {

    @AutoInjecter.ViewInject(R.id.listview_hotel_comment)
    private ListView listview_hotel_comment;

    private final String commentURL = "http://m.elong.com/hotel/api/morereviewnew?_rt=1489993579169&commenttype=1&pagesize=20";
    private String nowPage = "&pageindex=";
    private String hotelId = "&hotelid=";
    private int nowPageIndex = 1;
    private String hotelIdIndex;
    private List<UserComment> userComments;
    private UserCommentAdapter userCommentAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_hotel_comment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    private void initData() {
        if (userComments == null) {
            userComments = new ArrayList<UserComment>();
        }

        Intent intent = this.getIntent();
        hotelIdIndex = intent.getStringExtra("hotelID");

        listview_hotel_comment.setOnScrollListener(this);
        String queryCommentURL = commentURL + hotelId + hotelIdIndex + nowPage + nowPageIndex;
        new NewHotelComment().execute(queryCommentURL);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        if (AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL == i
                && listview_hotel_comment.getLastVisiblePosition() == userComments.size() - 1) {
            try {
                // 加载更多评论信息
                nowPageIndex++;
                String queryCommentURL = commentURL + hotelId + hotelIdIndex + nowPage + nowPageIndex;
                new UpDataHotelComment().execute(queryCommentURL);
            } catch (Exception e) {
                errorToast("没有更多数据了");
            }
        }

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }

    class NewHotelComment extends AsyncTask<String, Void, List<UserComment>> {

        @Override
        protected List<UserComment> doInBackground(String... strings) {
            return JSONTool.getUserComment(strings[0]);
        }

        @Override
        protected void onPostExecute(List<UserComment> userCommentList) {
            super.onPostExecute(userComments);

            Log.d("123", "userCommentList: " + userCommentList);
            userComments.clear();
            userComments.addAll(userCommentList);
            if (userCommentAdapter == null) {
                userCommentAdapter = new UserCommentAdapter(getApplicationContext(), userComments);
                listview_hotel_comment.setAdapter(userCommentAdapter);
            } else {
                userCommentAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 滑动更新评论列表
     */
    class UpDataHotelComment extends AsyncTask<String, Void, List<UserComment>> {

        @Override
        protected List<UserComment> doInBackground(String... strings) {
            return JSONTool.getUserComment(strings[0]);
        }

        @Override
        protected void onPostExecute(List<UserComment> userCommentList) {
            super.onPostExecute(userComments);

            userComments.addAll(userCommentList);
            if (userCommentAdapter == null) {
                userCommentAdapter = new UserCommentAdapter(getApplicationContext(), userComments);
                listview_hotel_comment.setAdapter(userCommentAdapter);
            } else {
                userCommentAdapter.notifyDataSetChanged();
            }
        }
    }

}
