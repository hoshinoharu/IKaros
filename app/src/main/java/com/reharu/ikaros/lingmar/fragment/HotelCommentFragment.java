package com.reharu.ikaros.lingmar.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.reharu.ikaros.R;
import com.reharu.ikaros.lingmar.adapter.UserCommentAdapter;
import com.reharu.ikaros.lingmar.domain.UserComment;
import com.reharu.ikaros.lingmar.utils.JSONTool;

import java.util.ArrayList;
import java.util.List;

public class HotelCommentFragment extends Fragment implements AbsListView.OnScrollListener {

    private ListView listview_hotel_comment;

    private final String commentURL = "http://m.elong.com/hotel/api/morereviewnew?_rt=1489993579169&commenttype=1&pagesize=20";
    private String nowPage = "&pageindex=";
    private String hotelId = "&hotelid=";
    private int nowPageIndex = 1;
    private String hotelIdIndex;
    private List<UserComment> userComments;
    private UserCommentAdapter userCommentAdapter;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.activity_hotel_comment, null);
        }

        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }
        initUI();
        initData();

        return mView;
    }

    private void initUI() {
        listview_hotel_comment = (ListView) mView.findViewById(R.id.listview_hotel_comment);
    }

    private void initData() {
        if (userComments == null) {
            userComments = new ArrayList<UserComment>();
        }

        // 获取上一个界面的数据
//        Intent intent = this.getIntent();
//        hotelIdIndex = intent.getStringExtra("hotelID");
        hotelIdIndex = getArguments().getString("hotelID");

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
//                errorToast("没有更多数据了");
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
                userCommentAdapter = new UserCommentAdapter(getActivity().getApplicationContext(), userComments);
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
                userCommentAdapter = new UserCommentAdapter(getActivity().getApplicationContext(), userComments);
                listview_hotel_comment.setAdapter(userCommentAdapter);
            } else {
                userCommentAdapter.notifyDataSetChanged();
            }
        }
    }

}
