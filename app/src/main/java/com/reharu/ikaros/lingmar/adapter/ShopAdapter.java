package com.reharu.ikaros.lingmar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.reharu.harubase.tools.HLog;
import com.reharu.ikaros.R;
import com.reharu.ikaros.lingmar.domain.Goods;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Lingmar on 2017/3/26.
 */

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private List<Goods> goodsList;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    /**
     * 点击事件的接口
     */
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Goods data);
    }

    /**
     * 暴露一个点击事件供Activity使用
     *
     * @param listener
     */
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public ShopAdapter(Context context, List<Goods> goodsList) {
        this.context = context;
        this.goodsList = goodsList;
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(view, (Goods) view.getTag());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_card, parent, false);
        ViewHolder vh = new ViewHolder(view);
        // 将创建的View注册点击事件
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageView goodsImg = holder.iv_goods_img;
        TextView goodsTitel = holder.tv_goods_title;
        TextView goodsContent = holder.tv_goods_content;
        TextView goodsZanTotal = holder.tv_goods_zanTotal;
        TextView page = holder.tv_page;

        Goods goods = goodsList.get(position);
        HLog.e("TAG", goodsList.toArray());
        HLog.e("TAG", position);
        // 框架加载图片
        Picasso.with(context).load(goods.getGoodsPic()).into(goodsImg);
        goodsTitel.setText(goods.getGoodsTitle());
        goodsContent.setText(goods.getGoodsContent());
        goodsZanTotal.setText(goods.getZanTotal());
        page.setText(goods.getPage()+"/"+getItemCount());

        // 保存在itemView的Tag中，点击时进行获取
        holder.itemView.setTag(goodsList.get(position));
    }

    @Override
    public int getItemCount() {
        return goodsList == null ? 0 : goodsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_goods_img;
        private TextView tv_goods_title;
        private TextView tv_goods_content;
        private TextView tv_goods_zanTotal;
        private TextView tv_page;
        private OnRecyclerViewItemClickListener mOnItemClickListener = null;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_goods_img = (ImageView) itemView.findViewById(R.id.iv_goods_img);
            tv_goods_title = (TextView) itemView.findViewById(R.id.tv_goods_title);
            tv_goods_content = (TextView) itemView.findViewById(R.id.tv_goods_content);
            tv_goods_zanTotal = (TextView) itemView.findViewById(R.id.tv_goods_zanTotal);
            tv_page = (TextView) itemView.findViewById(R.id.tv_page);
        }
    }


}
