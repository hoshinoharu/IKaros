package com.reharu.ikaros.imxz.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.reharu.harubase.tools.HLog;
import com.reharu.ikaros.R;
import com.reharu.ikaros.imxz.activity.CalendarActivity;
import com.reharu.ikaros.imxz.activity.ChoosePlaceActivity;
import com.reharu.ikaros.imxz.activity.TrainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Imxz on 2017/3/21.
 */

public class Fragment_BuyTrain extends Fragment {


    private final static int REQUEST_GET_CANLENDAR = 255;
    private final static int REQUEST_GET_PLACE_FROM = 333;
    private final static int REQUEST_GET_PLACE_TO = 111;
    private View mView;
    private DrawerLayout mDrawer;
    private LinearLayout ll_getdate;
    private Button btn_search;
    private TextView tv_chooseDate;
    private TextView tv_chooseWeek;
    private TextView tv_startWay;
    private TextView tv_endWay;

    private static String trainDate;
    private static String fromDate = "蚌埠";
    private static String toDate = "芜湖";

    private static final SimpleDateFormat simpleTrainDate = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");
    private static final SimpleDateFormat sdfWeek = new SimpleDateFormat("EEEE");

    private void initViewByParent() {
        mDrawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragm_buytrain, null);
            initViewByParent();
            initView();
            initViewAction();
            initData();
        }

        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }

        return mView;
    }

    private void initData() {
        Date date = new Date(System.currentTimeMillis());
        trainDate = simpleTrainDate.format(date);
        tv_chooseDate.setText(simpleDateFormat.format(date));
        tv_chooseWeek.setText(sdfWeek.format(date));
    }

    private void initView() {
        ll_getdate = (LinearLayout) mView.findViewById(R.id.ll_getdate);
        btn_search = (Button) mView.findViewById(R.id.btn_search);
        tv_chooseDate = (TextView) mView.findViewById(R.id.tv_chooseDate);
        tv_chooseWeek = (TextView) mView.findViewById(R.id.tv_chooseWeek);
        tv_startWay = (TextView) mView.findViewById(R.id.tv_startWay);
        tv_endWay = (TextView) mView.findViewById(R.id.tv_endWay);
    }

    private void initViewAction() {
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrainActivity.closeDrawer();
                TrainActivity.reFreshStationInfo(trainDate, fromDate, toDate);
                HLog.e("TAG", "关闭");
            }
        });

        ll_getdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), CalendarActivity.class), REQUEST_GET_CANLENDAR);
            }
        });
        tv_startWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), ChoosePlaceActivity.class), REQUEST_GET_PLACE_FROM);
            }
        });
        tv_endWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), ChoosePlaceActivity.class), REQUEST_GET_PLACE_TO);
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case CalendarActivity.CALENDAR_RESULT_CODE: {
                trainDate = data.getStringExtra("date");
                tv_chooseWeek.setText(data.getStringExtra("week"));
                tv_chooseDate.setText((data.getIntExtra("month", 0) + 1) + "月" + data.getStringExtra("day") + "日");
            }
            break;
            case ChoosePlaceActivity.PLACE_RESULT_CODE: {
                if (requestCode == REQUEST_GET_PLACE_FROM) {
                    fromDate = data.getStringExtra("Place");
                    tv_startWay.setText(fromDate);
                } else if (requestCode == REQUEST_GET_PLACE_TO) {
                    toDate = data.getStringExtra("Place");
                    tv_endWay.setText(toDate);
                }
            }
            break;
        }
    }
}
