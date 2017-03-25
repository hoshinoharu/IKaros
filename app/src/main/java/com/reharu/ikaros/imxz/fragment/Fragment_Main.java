package com.reharu.ikaros.imxz.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.reharu.harubase.base.HaruApp;
import com.reharu.harubase.tools.HLog;
import com.reharu.ikaros.R;
import com.reharu.ikaros.imxz.Utils.TrainInfo;
import com.reharu.ikaros.imxz.adapter.TrainAdapter;
import com.reharu.ikaros.imxz.entity.StationInfo;

import java.util.List;

/**
 * Created by Imxz on 2017/3/21.
 */

public class Fragment_Main extends Fragment implements View.OnClickListener {

    private static ListView mListView;
    private static SwipeRefreshLayout mRefresh;
    private Button btn_buyPlain;
    private Button btn_buyTrain;
    private Button btn_buyBus;
    private static DrawerLayout drawer_layout;

    private static TrainAdapter mAdapter;
    private static FragmentManager mFmanager;

    private static String chooseDate;
    private static String startWay;
    private static String endWay;
    public static final Fragment[] fragms = {new Fragment_BuyPlain(), new Fragment_BuyTrain(), new Fragment_BuyBus(), new Fragment_Calendar(), new Fragment_ChoosePlace(), new Fragment_Main()};

    private View mView;

    private void initView() {
        mListView = (ListView) mView.findViewById(R.id.lv_train_infos);
        mRefresh = (SwipeRefreshLayout) mView.findViewById(R.id.srl_refresh);
        btn_buyPlain = (Button) mView.findViewById(R.id.btn_buyPlain);
        btn_buyTrain = (Button) mView.findViewById(R.id.btn_buyTrain);
        btn_buyBus = (Button) mView.findViewById(R.id.btn_buyBus);
        drawer_layout = (DrawerLayout) mView.findViewById(R.id.drawer_layout);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.trainaty_layout, null);
            initView();
        }
        initAction();
        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }
        return mView;
    }


    private void initAction() {
        mFmanager = getActivity().getFragmentManager();
        mFmanager.beginTransaction().replace(R.id.fr_fl_content, fragms[1]).commit();
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_buyPlain: {
                mFmanager.beginTransaction().replace(R.id.fr_fl_content, fragms[0]).commit();
            }
            break;
            case R.id.btn_buyTrain: {
                mFmanager.beginTransaction().replace(R.id.fr_fl_content, fragms[1]).commit();
            }
            break;
            case R.id.btn_buyBus: {
                mFmanager.beginTransaction().replace(R.id.fr_fl_content, fragms[2]).commit();
            }
            break;
        }
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
                if (stationInfos == null || stationInfos.size() == 0) {
                    Toast.makeText(HaruApp.context(), "未检索到车票.", Toast.LENGTH_SHORT).show();
                    mAdapter = new TrainAdapter(stationInfos);
                    mListView.setAdapter(mAdapter);
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
