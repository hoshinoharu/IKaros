package com.reharu.ikaros.lingmar.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.reharu.ikaros.R;
import com.reharu.ikaros.haru.activities.HCortanaActivity;

/**
 * Created by Lingmar on 2017/4/19.
 */

public class WebFragment extends Fragment {

    private WebView web_view;

    private View mView;
    private HCortanaActivity cortanaActivity;

    private String bookURL = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragm_web, null);
            cortanaActivity = (HCortanaActivity) getActivity();
            getPreExtra();
            initUI();
            initData();
        }
        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }

        return mView;
    }

    private void initUI() {
        web_view = (WebView) mView.findViewById(R.id.web_view);
    }

    private void initData() {
        try {
            web_view.loadUrl(bookURL);
            web_view.getSettings().setJavaScriptEnabled(true);
            web_view.setWebViewClient(new WebViewClient());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取上一个Fragment传递的数据
     */
    private void getPreExtra() {
        if (getArguments() != null) {
            bookURL = getArguments().getString("bookURL");
        }
    }
}
