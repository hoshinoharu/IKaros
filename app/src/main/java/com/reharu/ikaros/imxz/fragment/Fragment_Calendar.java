package com.reharu.ikaros.imxz.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.reharu.ikaros.R;
import com.reharu.ikaros.imxz.listener.OnChooseCoP;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Imxz on 2017/3/22.
 */

public class Fragment_Calendar extends MainFragment implements OnDateSelectedListener {

    public static final int CALENDAR_RESULT_CODE = 42454;

    private MaterialCalendarView calendarView;

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat sdfWeek = new SimpleDateFormat("EEEE");

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.calendar_layout, null);
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

    private void initView() {
        calendarView = (MaterialCalendarView) mView.findViewById(R.id.calendarView);
    }

    private void initAction() {
        calendarView.setOnDateChangedListener(this);
    }

    private void initData() {
        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.WEDNESDAY)
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .setMinimumDate(new Date(System.currentTimeMillis()))
                .setMaximumDate(new Date(System.currentTimeMillis() + 5184000000l))
                .commit();
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        Intent mIntent = new Intent();
        mIntent.putExtra("flag", "Calendar");
        mIntent.putExtra("date", simpleDateFormat.format(date.getDate()));
        mIntent.putExtra("week", sdfWeek.format(date.getDate()));
        mIntent.putExtra("month", date.getMonth());
        mIntent.putExtra("day", date.getDay() + "");
        ((OnChooseCoP) Fragment_Main.fragms[1]).setChooseContent(mIntent);
        finish();
    }


}
