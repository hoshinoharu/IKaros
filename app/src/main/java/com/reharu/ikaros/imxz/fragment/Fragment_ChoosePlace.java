package com.reharu.ikaros.imxz.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gjiazhe.wavesidebar.WaveSideBar;
import com.reharu.harubase.base.AutoInjecter;
import com.reharu.harubase.tools.HLog;
import com.reharu.ikaros.R;
import com.reharu.ikaros.imxz.Utils.TrainInfo;
import com.reharu.ikaros.imxz.adapter.PlaceAdapter;
import com.reharu.ikaros.imxz.entity.Place;
import com.reharu.ikaros.imxz.listener.OnChooseCoP;

import java.util.List;

/**
 * Created by Imxz on 2017/3/23.
 */

public class Fragment_ChoosePlace extends MainFragment implements View.OnClickListener {

    public static final int PLACE_RESULT_CODE = 470034;

    private boolean isCanChoose = false;

    @AutoInjecter.ViewInject(R.id.rv_contacts)
    private RecyclerView rv_contacts;

    @AutoInjecter.ViewInject(R.id.side_bar)
    private WaveSideBar side_bar;

    public static List<Place> placesData;
    private View mView;
    public static String fromTo = "from";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.chooseplace_layout, null);
            initView();
            initData();
            initAction();
        }
        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }

        return mView;
    }


    private void initAction() {
        side_bar.setOnSelectIndexItemListener(new WaveSideBar.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String index) {
                if (isCanChoose) {
                    for (int i = 0; i < placesData.size(); i++) {
                        if (placesData.get(i).getTitle().equals(index)) {
                            ((LinearLayoutManager) rv_contacts.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                            return;
                        }
                    }
                }
            }
        });

        rv_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HLog.e("TAG", v.toString());
            }
        });
    }

    private void initView() {
        rv_contacts = (RecyclerView) mView.findViewById(R.id.rv_contacts);
        side_bar = (WaveSideBar) mView.findViewById(R.id.side_bar);
        rv_contacts.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initData() {
        AsyncTask<Void, Void, List<Place>> execute = new AsyncTask<Void, Void, List<Place>>() {
            @Override
            protected List<Place> doInBackground(Void... params) {
                return TrainInfo.getPlacesInfo();
            }

            @Override
            protected void onPostExecute(List<Place> places) {
                placesData = places;
                if (placesData != null && placesData.size() != 0) {
                    rv_contacts.setAdapter(new PlaceAdapter(placesData, R.layout.item_place, Fragment_ChoosePlace.this));
                    isCanChoose = true;
                }
                HLog.e("TAG", "数据加载完成");
            }
        }.execute();
        side_bar.setIndexItems(Place.getIndex());
    }


    @Override
    public void onClick(View v) {
        String tvName = ((TextView) v.findViewById(R.id.tv_placename)).getText().toString();
        Intent intent = new Intent();
        intent.putExtra("flag", "Place");
        intent.putExtra("Place", tvName);
        intent.putExtra("FromTo", fromTo);
        ((OnChooseCoP) Fragment_Main.fragms[1]).setChooseContent(intent);
        cortanaActivity.startFragment(Fragment_Main.fragms[5]);
    }
}
