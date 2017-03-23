package com.reharu.ikaros.imxz.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reharu.ikaros.R;

/**
 * Created by Imxz on 2017/3/21.
 */

public class Fragment_BuyPlain extends Fragment {
    private View mView;
    private DrawerLayout mDrawer;


    private void initViewByParent() {
        mDrawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragm_buyplain, null);
            initViewByParent();
        }

        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }

        return mView;
    }
}
