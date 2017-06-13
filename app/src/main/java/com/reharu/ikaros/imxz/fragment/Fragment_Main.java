package com.reharu.ikaros.imxz.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
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
import com.yalantis.taurus.PullToRefreshView;

import java.util.List;

/**
 * Created by Imxz on 2017/3/21.
 */

public class Fragment_Main extends MainFragment implements View.OnClickListener {

    private static ListView mListView;
    private static PullToRefreshView mPullToRefreshView;
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

    public static final String QUERY_URL = "queryUrl";

    private View mView;

    private int status;
    private static final int STATUS_PLANE = 0;
    private static final int STATUS_TRAIN = 1;
    private static final int STATUS_BUS = 2;

    private void initView() {
        mListView = (ListView) mView.findViewById(R.id.lv_train_infos);
        mPullToRefreshView = (PullToRefreshView) mView.findViewById(R.id.pull_to_refresh);
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
            initAction();
        }
        initBundle();
//        initAction();
//        ViewGroup parent = (ViewGroup) mView.getParent();
//        if (parent != null) {
//            parent.removeView(mView);
//        }
        return mView;
    }

    private void initBundle() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String url = bundle.getString(QUERY_URL, null);
            if (url != null) {
                refreshStationInfo(url);
            }
        }
    }


    private void initAction() {

        mFmanager = getActivity().getFragmentManager();
        mFmanager.beginTransaction().replace(R.id.fr_fl_content, fragms[1]).commit();
        status = STATUS_TRAIN;
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = "http://touch.train.qunar.com/trainList_Card.html?searchType=&bd_source=&sort=&needRecommondLess=1&date=NOWDATE&trainNum=NUMBER&startStation=STARTPLACE&startCity=STARTPLACE&searchDep=STARTPLACE&searchArr=ENDPLACE&endStation=ENDPLACE&endCity=ENDPLACE&seatType=%E7%A1%AC%E5%BA%A7";
                StationInfo station = (StationInfo) mAdapter.getItem(position);
                String endplace = station.getdStation();
                String startplace = station.getaStation();
                String trainNum = station.getTrainNumber();
                String date = chooseDate;
                url = url.replace("STARTPLACE", endplace).replace("ENDPLACE", startplace).replace("NOWDATE", date).replace("NUMBER",trainNum);
                HLog.e("TAG", url);

                Bundle bundle = new Bundle();
                bundle.putString("TrainURL", url);
                cortanaActivity.startFragment(Fragment_TrainPay.class, bundle);

            }
        });
        if (startWay != null && endWay != null && chooseDate != null) {
            reFreshStationInfo(chooseDate, startWay, endWay);
        }

        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (startWay != null && endWay != null && chooseDate != null) {
                            reFreshStationInfo(chooseDate, startWay, endWay);
                        } else {
                            mPullToRefreshView.setRefreshing(false);
                        }
                    }
                }, 2000);
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
                if (status != STATUS_PLANE) {
                    mFmanager.beginTransaction().replace(R.id.fr_fl_content, fragms[0]).commit();
                    status = STATUS_PLANE;
                }
            }
            break;
            case R.id.btn_buyTrain: {
                if (status != STATUS_TRAIN) {
                    mFmanager.beginTransaction().replace(R.id.fr_fl_content, fragms[1]).commit();
                    status = STATUS_TRAIN;
                }
            }
            break;
            case R.id.btn_buyBus: {
                if (status != STATUS_BUS) {
                    mFmanager.beginTransaction().replace(R.id.fr_fl_content, fragms[2]).commit();
                    status = STATUS_BUS;
                }
            }
            break;
        }
    }


    public static void reFreshStationInfo(final String data, final String fromPlace, final String toPlace) {
        new AsyncTask<Void, Void, List<StationInfo>>() {

            @Override
            protected void onPreExecute() {
                mPullToRefreshView.setRefreshing(true);
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
                showStationInfo(stationInfos);
            }
        }.execute();
    }

    //修改
    /*根据图灵返回的url查询*/
    public void refreshStationInfo(final String url) {
        new AsyncTask<Void, Void, List<StationInfo>>() {

            @Override
            protected void onPreExecute() {
                mPullToRefreshView.setRefreshing(true);
            }

            @Override
            protected List<StationInfo> doInBackground(Void... params) {
                //替换图灵url
                String realUrl = url.replaceFirst(".*\\?", "http://touch.train.qunar.com/api/train/trains2s?");
                realUrl = realUrl.replaceFirst("&sort=.*", "&bd_source=qunar&filterNewDepTimeRange=00%3A00-24%3A00&filterNewArrTimeRange=00%3A00-24%3A00");
                HLog.e("TAG", realUrl);
                List<StationInfo> stationsInfos = TrainInfo.getStationsInfo(realUrl);
                return stationsInfos;
            }

            @Override
            protected void onPostExecute(List<StationInfo> stationInfos) {
                showStationInfo(stationInfos);
            }
        }.execute();
    }


    public static void showStationInfo(List<StationInfo> stationInfos) {
        if (stationInfos == null || stationInfos.size() == 0) {
            Toast.makeText(HaruApp.context(), "未检索到车票.", Toast.LENGTH_SHORT).show();
            mAdapter = new TrainAdapter(stationInfos);
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter = new TrainAdapter(stationInfos);
            mListView.setAdapter(mAdapter);
        }

        mPullToRefreshView.setRefreshing(false);
    }
    //


    public static void closeDrawer() {
        drawer_layout.closeDrawers();
    }

}
