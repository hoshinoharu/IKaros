package com.reharu.ikaros.haru.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;

import com.orange.ui.activity.GameActivity;
import com.reharu.harubase.tools.HLog;

/**
 * Created by hoshino on 2017/4/20.
 */

public abstract class HaruGameActivity extends GameActivity {

    private static final int ADD = 0;
    private static final int REPLACE = 1;
    private static final int REMOVE = 2;
    private boolean canPopfrag = true ;

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(canPopfrag){
            if(keyCode == KeyEvent.KEYCODE_BACK){
                if(popFragmentBackStack()){
                    HLog.e("TAG","popSth");
                    return true ;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean popFragmentBackStack(){
        return getFragmentManager().popBackStackImmediate() ;
    }

    public void setCanPopfrag(boolean canPopfrag) {
        this.canPopfrag = canPopfrag;
    }
}
