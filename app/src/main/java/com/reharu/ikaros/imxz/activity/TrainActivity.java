package com.reharu.ikaros.imxz.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.reharu.harubase.base.AutoInjecter;
import com.reharu.harubase.base.HaruActivity;
import com.reharu.harubase.base.HaruApp;
import com.reharu.harubase.tools.HLog;
import com.reharu.ikaros.R;
import com.reharu.ikaros.imxz.Utils.TrainInfo;
import com.reharu.ikaros.imxz.adapter.TrainAdapter;
import com.reharu.ikaros.imxz.entity.StationInfo;
import com.reharu.ikaros.imxz.fragment.Fragment_BuyBus;
import com.reharu.ikaros.imxz.fragment.Fragment_BuyPlain;
import com.reharu.ikaros.imxz.fragment.Fragment_BuyTrain;

import java.util.List;

/**
 * Created by Imxz on 2017/3/18.
 */

public class TrainActivity extends HaruActivity implements View.OnClickListener {
    @AutoInjecter.ViewInject(R.id.lv_train_infos)
    private static ListView mListView;
    @AutoInjecter.ViewInject(R.id.srl_refresh)
    private static SwipeRefreshLayout mRefresh;
    @AutoInjecter.ViewInject(R.id.fl_content)
    private FrameLayout fl_content;
    @AutoInjecter.ViewInject(R.id.btn_buyPlain)
    private Button btn_buyPlain;
    @AutoInjecter.ViewInject(R.id.btn_buyTrain)
    private Button btn_buyTrain;
    @AutoInjecter.ViewInject(R.id.btn_buyBus)
    private Button btn_buyBus;
    @AutoInjecter.ViewInject(R.id.drawer_layout)
    private static DrawerLayout drawer_layout;


    private static TrainAdapter mAdapter;
    private static FragmentManager mFmanager;


    private static String chooseDate;
    private static String startWay;
    private static String endWay;
    private static final Fragment[] fragms = {new Fragment_BuyPlain(), new Fragment_BuyTrain(), new Fragment_BuyBus()};


    @Override
    public int getContentViewId() {
        return R.layout.trainaty_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initDate();
        initAction();
    }

    private void initDate() {
        startActivity(new Intent(this, ChoosePlaceActivity.class));
        finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_buyPlain: {
                mFmanager.beginTransaction().replace(R.id.fl_content, fragms[0]).commit();
            }
            break;
            case R.id.btn_buyTrain: {
                mFmanager.beginTransaction().replace(R.id.fl_content, fragms[1]).commit();
            }
            break;
            case R.id.btn_buyBus: {
                mFmanager.beginTransaction().replace(R.id.fl_content, fragms[2]).commit();
            }
            break;
        }
    }

    private void initAction() {
        mFmanager = getSupportFragmentManager();
        mFmanager.beginTransaction().add(R.id.fl_content, fragms[1]).commit();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HLog.e("TAG", position + "");
            }
        });

        if (startWay != null && endWay != null && chooseDate != null) {
            reFreshStationInfo(chooseDate, startWay, endWay);
        }

        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (startWay != null && endWay != null && chooseDate != null) {
                    reFreshStationInfo(chooseDate, startWay, endWay);
                } else {
                    mRefresh.setRefreshing(false);
                }
            }
        });

        btn_buyPlain.setOnClickListener(this);
        btn_buyTrain.setOnClickListener(this);
        btn_buyBus.setOnClickListener(this);
        drawer_layout.openDrawer(Gravity.START);

    }


    public static void reFreshStationInfo(final String data, final String fromPlace, final String toPlace) {
        AsyncTask<Void, Void, List<StationInfo>> asyncTask = new AsyncTask<Void, Void, List<StationInfo>>() {

            @Override
            protected void onPreExecute() {
                mRefresh.setRefreshing(true);
            }

            @Override
            protected List<StationInfo> doInBackground(Void... params) {
                List<StationInfo> stationsInfos = TrainInfo.getStationsInfo(data, fromPlace, toPlace);
                chooseDate = data;
                startWay = fromPlace;
                endWay = toPlace;
                return stationsInfos;
            }

            @Override
            protected void onPostExecute(List<StationInfo> stationInfos) {
                if (stationInfos.size() == 0) {
                    Toast.makeText(HaruApp.context(), "未检索到车票.", Toast.LENGTH_SHORT).show();
                } else {
                    mAdapter = new TrainAdapter(stationInfos);
                    mListView.setAdapter(mAdapter);
                }

                mRefresh.setRefreshing(false);
            }
        }.execute();
    }

    public static void closeDrawer() {
        drawer_layout.closeDrawers();
    }


}
