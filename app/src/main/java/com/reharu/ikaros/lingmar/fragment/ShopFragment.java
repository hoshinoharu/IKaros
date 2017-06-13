package com.reharu.ikaros.lingmar.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reharu.harubase.tools.HLog;
import com.reharu.ikaros.R;
import com.reharu.ikaros.haru.activities.HCortanaActivity;
import com.reharu.ikaros.lingmar.adapter.ShopAdapter;
import com.reharu.ikaros.lingmar.domain.Goods;
import com.reharu.ikaros.lingmar.utils.CardConfig;
import com.reharu.ikaros.lingmar.utils.JSONTool;
import com.reharu.ikaros.lingmar.utils.OverLayCardLayoutManager;
import com.reharu.ikaros.lingmar.utils.ShopCallback;

import java.util.ArrayList;
import java.util.List;

public class ShopFragment extends Fragment {

    private final String GOODS_URL = "https://tce.taobao.com/api/mget.htm?&tce_sid=1164535&pageSize=10&startIndex=";
    private final String GOODS_INFO_URL = "https://item.taobao.com/item.htm?&id=";
    private int index = 1;
    private RecyclerView mRv;
    private List<Goods> goodsList;
    private ShopAdapter shopAdapter;
    private SwipeRefreshLayout swipeLayout;

    private View mView;
    private HCortanaActivity cortanaActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.activity_shop, null);
            cortanaActivity = (HCortanaActivity) getActivity();
        }

        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }

        initUI();
        initData();
        initSwipeLayout();
        return mView;
    }

    private void initUI() {
        mRv = (RecyclerView) mView.findViewById(R.id.recycler_view);
        swipeLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_layout);
    }

    private void initData() {
        mRv.setLayoutManager(new OverLayCardLayoutManager());
        if (goodsList == null) {
            goodsList = new ArrayList<Goods>();
        }

        String queryURL = GOODS_URL + index;
        new NewGoodsAsyncTask().execute(queryURL);

        // 初始化Adapter
        shopAdapter = new ShopAdapter(getActivity().getApplicationContext(), goodsList);
        shopAdapter.setOnItemClickListener(new ShopAdapter.OnRecyclerViewItemClickListener() {

            @Override
            public void onItemClick(View view, Goods data) {
//                Log.d("123", "data: " + data.toString());
                // 跳转到商品的详细界面
//                Intent intent = new Intent(getActivity().getApplicationContext(), ShopInfoFragment.class);
//                intent.putExtra("goodsId", data.getGoodsId());
//                startActivity(intent);
                Bundle bundle = new Bundle();
                bundle.putString("goodsId", data.getGoodsId());
                cortanaActivity.startFragment(ShopInfoFragment.class, bundle);
                HLog.d("123", "点击图片");
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
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        swipeLayout.setRefreshing(true);
                        // 刷新信息
                        index++;
                        String queryGoodsURL = GOODS_URL + index;
                        new UpdataGoodsAsyncTask().execute(queryGoodsURL);
                    }
                });
            }
        });
    }

    /**
     * 异步加载图片信息
     */
    class NewGoodsAsyncTask extends AsyncTask<String, Void, List<Goods>> {

        @Override
        protected List<Goods> doInBackground(String... strings) {
            return JSONTool.getGoods(strings[0]);
        }

        @Override
        protected void onPostExecute(List<Goods> goodses) {
            super.onPostExecute(goodses);

            goodsList.addAll(goodses);
            mRv.setAdapter(shopAdapter);

            //初始化配置
            CardConfig.initConfig(getActivity().getApplicationContext());
            ItemTouchHelper.Callback callback = new ShopCallback(mRv, shopAdapter, goodsList);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
            itemTouchHelper.attachToRecyclerView(mRv);

            swipeLayout.setRefreshing(false);
        }
    }

    class UpdataGoodsAsyncTask extends AsyncTask<String, Void, List<Goods>> {

        @Override
        protected List<Goods> doInBackground(String... strings) {
            return JSONTool.getGoods(strings[0]);
        }

        @Override
        protected void onPostExecute(List<Goods> goodses) {
            super.onPostExecute(goodses);

            goodsList.clear();
            goodsList.addAll(goodses);
            Log.d("123", "goodses: " + goodses.size());
            if (shopAdapter == null) {
                shopAdapter = new ShopAdapter(getActivity().getApplicationContext(), goodsList);
                mRv.setAdapter(shopAdapter);

                //初始化配置
                CardConfig.initConfig(getActivity().getApplicationContext());
                ItemTouchHelper.Callback callback = new ShopCallback(mRv, shopAdapter, goodsList);
                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
                itemTouchHelper.attachToRecyclerView(mRv);
            } else {
                shopAdapter.notifyDataSetChanged();
            }
            swipeLayout.setRefreshing(false);
        }
    }

}
