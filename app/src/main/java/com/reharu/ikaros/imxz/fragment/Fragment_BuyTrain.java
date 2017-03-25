package com.reharu.ikaros.imxz.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.reharu.harubase.tools.HLog;
import com.reharu.ikaros.R;
import com.reharu.ikaros.haru.activities.HCortanaActivity;
import com.reharu.ikaros.imxz.listener.OnChooseCoP;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Imxz on 2017/3/21.
 */

public class Fragment_BuyTrain extends MainFragment implements OnChooseCoP {


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


    private HCortanaActivity cortanaActivity ;


    private void initViewByParent() {
        mDrawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragm_buytrain, null);
            initView();
            initViewAction();
            initData();
        }
        cortanaActivity = (HCortanaActivity) getActivity();
        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }
        initViewByParent();
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
                Fragment_Main.closeDrawer();
                Fragment_Main.reFreshStationInfo(trainDate, fromDate, toDate);
                HLog.e("TAG", "关闭");
            }
        });

        ll_getdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cortanaActivity.startFragment(Fragment_Main.fragms[3]);
            }
        });
        tv_startWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cortanaActivity.startFragment(Fragment_Main.fragms[4]);
                Fragment_ChoosePlace.fromTo = "from";
            }
        });
        tv_endWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cortanaActivity.startFragment(Fragment_Main.fragms[4]);
                Fragment_ChoosePlace.fromTo = "to";
            }
        });

    }


    @Override
    public void setChooseContent(Intent intent) {
        String flag = intent.getStringExtra("flag");
        if ("Calendar".equals(flag)) {
            trainDate = intent.getStringExtra("date");
            tv_chooseWeek.setText(intent.getStringExtra("week"));
            tv_chooseDate.setText((intent.getIntExtra("month", 0) + 1) + "月" + intent.getStringExtra("day") + "日");
        } else if ("Place".equals(flag)) {
            if (intent.getStringExtra("FromTo").equals("from")) {
                fromDate = intent.getStringExtra("Place");
                tv_startWay.setText(fromDate);
            } else if (intent.getStringExtra("FromTo").equals("to")) {
                toDate = intent.getStringExtra("Place");
                tv_endWay.setText(toDate);
            }
        }
    }


}
