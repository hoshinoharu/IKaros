package com.reharu.ikaros.lingmar.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.reharu.harubase.tools.HLog;
import com.reharu.ikaros.R;
import com.reharu.ikaros.haru.activities.HCortanaActivity;

public class ShopInfoFragment extends Fragment {

    private WebView webView;
    private final String GOODS_INFO_URL = "https://item.taobao.com/item.htm?&id=";
    private String goodsId;

    private View mView;
    private HCortanaActivity cortanaActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragm_web, null);
            cortanaActivity = (HCortanaActivity) getActivity();
            initUI();
            initData();
        }

        ViewGroup parent = (ViewGroup) mView.getParent();
        HLog.e("TAG", "parent", parent);
        if (parent != null) {
            parent.removeView(mView);
        }

        return mView;
    }

    private void initUI() {
        webView = (WebView) mView.findViewById(R.id.web_view);
    }

    private void initData() {
        if (getArguments() != null) {
            goodsId = getArguments().getString("goodsId");
        }
        final String queryGoodsInfoURL = GOODS_INFO_URL + goodsId;
        HLog.d("123", "queryGoodsInfoURL: " + queryGoodsInfoURL);

        try {
            webView.loadUrl(queryGoodsInfoURL);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
