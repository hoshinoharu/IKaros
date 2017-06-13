package com.reharu.ikaros.haru.components;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.reharu.harubase.base.AutoInjecter;
import com.reharu.harubase.base.HaruApp;
import com.reharu.harubase.base.HaruViewHolder;
import com.reharu.harubase.tools.FormatTool;
import com.reharu.ikaros.R;
import com.reharu.ikaros.haru.news.News;

import java.util.List;


/**
 * Created by hoshino on 2017/4/20.
 */

public class NewsAdaoter extends RecyclerView.Adapter<NewsAdaoter.ViewHolder> {

    private List<News> newsList ;

    private View headerView ;

    public NewsAdaoter(List<News> newsList) {
        this.newsList = newsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView;
        itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news, viewGroup, false);
        return  new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
            News news = newsList.get(i) ;
            Glide.with(HaruApp.context()).load(news.getImgurl()).into(viewHolder.newsImg) ;
            viewHolder.newsTitle.setText(news.getTitle());
            viewHolder.newsTime.setText(FormatTool.formatDate(news.getTime()));
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class ViewHolder extends HaruViewHolder {

        @AutoInjecter.ViewInject(R.id.newsImg)
        ImageView newsImg ;
        @AutoInjecter.ViewInject(R.id.newsTitle)
        TextView newsTitle ;
        @AutoInjecter.ViewInject(R.id.newsTime)
        TextView newsTime ;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
