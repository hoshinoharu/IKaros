package com.reharu.ikaros.lingmar.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.reharu.ikaros.R;
import com.reharu.ikaros.lingmar.domain.UserComment;

import java.util.List;

/**
 * Created by Lingmar on 2017/3/22.
 */

public class UserCommentAdapter extends BaseAdapter {

    private Context context;
    private List<UserComment> userComments;

    public UserCommentAdapter(Context context, List<UserComment> userComments) {
        this.context = context;
        this.userComments = userComments;
    }

    @Override
    public int getCount() {
        return userComments.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(context, R.layout.hotel_user_comment, null);
        }

        TextView tv_userName = (TextView) view.findViewById(R.id.tv_userName);
        TextView tv_roomTypeName = (TextView) view.findViewById(R.id.tv_roomTypeName);
        TextView tv_comment_content = (TextView) view.findViewById(R.id.tv_comment_content);
        TextView tv_commentDateTime = (TextView) view.findViewById(R.id.tv_commentDateTime);

        UserComment userComment = userComments.get(i);
        tv_userName.setText(userComment.getUserName());
        tv_roomTypeName.setText(userComment.getRoomTypeName());
        tv_comment_content.setText(userComment.getContent());
        tv_commentDateTime.setText(userComment.getCommentDateTime());

        return view;
    }
}
