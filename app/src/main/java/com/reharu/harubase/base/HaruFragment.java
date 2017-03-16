package com.reharu.harubase.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by 星野悠 on 2017/3/3.
 */

public abstract class HaruFragment  extends Fragment implements AutoInjecter.AutoInjectable{

    protected View contentView ;
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

    protected void shortToast(String msg) {
        Toast.makeText(getActivity(), msg,Toast.LENGTH_SHORT).show();
    }
    protected void longToast(String msg) {
        Toast.makeText(getActivity(), msg,Toast.LENGTH_LONG).show();
    }
    protected void errorToast(String msg){
        Toast.makeText(getActivity(), msg,Toast.LENGTH_LONG).show();
    }

    protected void startOtherHaruAc(Class<? extends HaruActivity> haruAcClass){
        this.startActivity(new Intent(getActivity(), haruAcClass));
    }

    protected void startOtherHaruAcForResult(Class<? extends HaruActivity> haruAcClass, int requestCode){
        this.startActivityForResult(new Intent(getActivity(),haruAcClass), requestCode);
    }
}
