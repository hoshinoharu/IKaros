package com.reharu.ikaros.lingmar.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.reharu.harubase.tools.HLog;
import com.reharu.ikaros.R;
import com.reharu.ikaros.haru.activities.HCortanaActivity;

public class ShopInfoFragment extends Fragment {

    private WebView webView;
    private final String GOODS_INFO_URL = "https://item.taobao.com/item.htm?&id=";

    private View mView;
    private HCortanaActivity cortanaActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.activity_shop_info, null);
            cortanaActivity = (HCortanaActivity) getActivity();
        }

        ViewGroup parent = (ViewGroup) mView.getParent();
        HLog.e("TAG", "parent",parent);
        if (parent != null) {
            parent.removeView(mView);
        }

        initUI();
        initData();

        return mView;
    }

    private void initUI() {
        webView = (WebView) mView.findViewById(R.id.web_view);
    }

    private void initData() {
        // 获取Intent中的URL
//        Intent intent = this.getIntent();
//        String goodsId = intent.getStringExtra("goodsId");
        String goodsId = getArguments().getString("goodsId");
        final String queryGoodsInfoURL = GOODS_INFO_URL + goodsId;

        webView.loadUrl(queryGoodsInfoURL);
//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                Log.d("123", "shouldOverrideUrlLoading");
//                return true;
//            }
//        });
    }
}
