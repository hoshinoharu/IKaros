package com.reharu.ikaros.imxz.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.reharu.ikaros.haru.activities.HCortanaActivity;

/**
 * Created by hoshino on 2017/3/25.
 */

public class MainFragment extends Fragment {

    protected HCortanaActivity cortanaActivity ;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        cortanaActivity = (HCortanaActivity) getActivity();
        super.onCreate(savedInstanceState);
    }

    protected void finish(){
        if(this.isVisible()) {
            cortanaActivity.popFragmentBackStack();
        }
    }
}
