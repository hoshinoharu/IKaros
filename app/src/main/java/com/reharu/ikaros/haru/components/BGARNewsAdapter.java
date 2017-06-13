package com.reharu.ikaros.haru.components;

import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.reharu.harubase.base.HaruApp;
import com.reharu.harubase.tools.FormatTool;
import com.reharu.ikaros.R;
import com.reharu.ikaros.haru.news.News;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by hoshino on 2017/4/20.
 */

public class BGARNewsAdapter extends BGARecyclerViewAdapter<News> {

    public BGARNewsAdapter(RecyclerView recyclerView, List<News> newsList) {
        super(recyclerView, R.layout.item_news);
        setData(newsList);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, News model) {
        Glide.with(HaruApp.context()).load(model.getImgurl()).into(helper.getImageView(R.id.newsImg)) ;
        helper.setText(R.id.newsTitle, model.getTitle());
        helper.setText(R.id.newsTime, FormatTool.formatDate(model.getTime()));
    }
}
