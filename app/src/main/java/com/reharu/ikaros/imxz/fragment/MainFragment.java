package com.reharu.ikaros.imxz.fragment;

import android.app.Fragment;
import android.content.Context;

import com.reharu.ikaros.haru.activities.HCortanaActivity;

/**
 * Created by hoshino on 2017/3/25.
 */

public class MainFragment extends Fragment {

    protected HCortanaActivity cortanaActivity ;

    @Override
    public void onAttach(Context context) {
        cortanaActivity = (HCortanaActivity) getActivity();
        super.onAttach(context);
    }
}
