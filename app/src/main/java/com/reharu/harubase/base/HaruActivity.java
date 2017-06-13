package com.reharu.harubase.base;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.reharu.harubase.tools.HLog;

/**
 * Created by 星野悠 on 2017/2/23.
 */

public abstract class HaruActivity extends AppCompatActivity implements AutoInjecter.AutoInjectable {

    private static final int ADD = 0;
    private static final int REPLACE = 1;
    private static final int REMOVE = 2;

    @Override
    public View findInjectViewById(int resId) {
        return this.findViewById(resId);
    }

    public abstract int getContentViewId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(this.getContentViewId());
        AutoInjecter.autoInjectAllField(this);
    }

    protected void shortToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void longToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    protected void errorToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    protected void successToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    protected void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void dialog(String msg) {
        new AlertDialog.Builder(this).setMessage(msg).create().show();
    }

    protected void errorDialog(String msg) {
        new AlertDialog.Builder(this).setMessage(msg).create().show();
    }

    protected void successDialog(String msg) {
        new AlertDialog.Builder(this).setMessage(msg).create().show();
    }

    protected void startOtherHaruAc(Class<? extends HaruActivity> haruAcClass) {
        this.startActivity(new Intent(this, haruAcClass));
    }

    protected void startOtherHaruAcForResult(Class<? extends HaruActivity> haruAcClass, int requestCode) {
        this.startActivityForResult(new Intent(this, haruAcClass), requestCode);
    }


    public void addFragment(Fragment fragment) {
        this.addFragment(-1, fragment, null);
    }
    public void addFragment(Fragment fragment, Bundle bundle) {
        this.addFragment(-1, fragment, bundle);
    }
    public void addFragment(int id, Fragment fragment, Bundle bundle) {
        handleFragmentTransaction(ADD,getFragmentManager().beginTransaction(), id, fragment, bundle);
    }
    public void addFragment(Class<? extends Fragment> fragCls){
        addFragment(-1, fragCls, null);
    }
    public void addFragment(Class<? extends Fragment> fragCls, Bundle bundle){
        addFragment(-1, fragCls, bundle);
    }
    public void addFragment(int id, Class<? extends Fragment> fragCls, Bundle bundle){
        try {
            Fragment fragment = fragCls.newInstance();
            addFragment(id, fragment, bundle);
        } catch (Exception e) {
            HLog.ex("TAG", e);
        }
    }

    private void handleFragmentTransaction(int model, FragmentTransaction fragmentTransaction, int id, Fragment fragment, Bundle bundle) {
        if(bundle != null){
            fragment.setArguments(bundle);
        }

        int fragmentContentId = id > 0? id : getFragmentContentId() ;
        if(fragmentContentId <= 0){
            throw new RuntimeException("没有指定fragmentId");
        }
        switch (model){
            case ADD:
                fragmentTransaction.add(fragmentContentId, fragment) ;
                break;
            case REPLACE:
                fragmentTransaction.replace(fragmentContentId, fragment);
                break;
        }
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }
    protected int getFragmentContentId() {
        return -1;
    }
}

