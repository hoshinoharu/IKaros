package com.reharu.harubase.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hoshino on 2017/3/25.
 */

public abstract class HaruFragment extends Fragment implements AutoInjecter.AutoInjectable{

    protected HaruActivity owner ;
    protected View contentView ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        owner = (HaruActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.contentView = inflater.inflate(getContentViewId(), container, false) ;
        AutoInjecter.autoInjectAllField(this);
        afterInject(savedInstanceState);
        return this.contentView;
    }

    @Override
    public View findInjectViewById(int resId) {
        return this.contentView.findViewById(resId);
    }

    public abstract int getContentViewId() ;

    public void afterInject(@Nullable Bundle savedInstanceState){

    }

}
