package com.reharu.ikaros.imxz.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gjiazhe.wavesidebar.WaveSideBar;
import com.reharu.harubase.base.AutoInjecter;
import com.reharu.harubase.base.HaruActivity;
import com.reharu.harubase.tools.HLog;
import com.reharu.ikaros.R;
import com.reharu.ikaros.imxz.Utils.TrainInfo;
import com.reharu.ikaros.imxz.adapter.PlaceAdapter;
import com.reharu.ikaros.imxz.entity.Place;

import java.util.List;

/**
 * Created by Imxz on 2017/3/23.
 */

public class ChoosePlaceActivity extends HaruActivity implements View.OnClickListener {

    public static final int PLACE_RESULT_CODE = 470034;


    @AutoInjecter.ViewInject(R.id.rv_contacts)
    private RecyclerView rv_contacts;

    @AutoInjecter.ViewInject(R.id.side_bar)
    private WaveSideBar side_bar;

    public static List<Place> placesData;


    @Override
    public int getContentViewId() {
        return R.layout.chooseplace_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initAction();
    }

    private void initAction() {
        side_bar.setOnSelectIndexItemListener(new WaveSideBar.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String index) {
                for (int i = 0; i < placesData.size(); i++) {
                    if (placesData.get(i).getTitle().equals(index)) {
                        ((LinearLayoutManager) rv_contacts.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                        return;
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
        rv_contacts.setLayoutManager(new LinearLayoutManager(this));
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
                    rv_contacts.setAdapter(new PlaceAdapter(placesData, R.layout.item_place, ChoosePlaceActivity.this));
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
        intent.putExtra("Place", tvName);
        setResult(PLACE_RESULT_CODE, intent);
        finish();
    }
}
