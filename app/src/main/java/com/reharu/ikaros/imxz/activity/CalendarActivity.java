package com.reharu.ikaros.imxz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.reharu.harubase.base.AutoInjecter;
import com.reharu.harubase.base.HaruActivity;
import com.reharu.ikaros.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Imxz on 2017/3/22.
 */

public class CalendarActivity extends HaruActivity implements OnDateSelectedListener {

    public static final int CALENDAR_RESULT_CODE = 42454;

    @AutoInjecter.ViewInject(R.id.calendarView)
    private MaterialCalendarView calendarView;

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat sdfWeek = new SimpleDateFormat("EEEE");
    private Intent mIntent;

    @Override
    public int getContentViewId() {
        return R.layout.calendar_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initAction();
        mIntent = getIntent();
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
        mIntent.putExtra("date", simpleDateFormat.format(date.getDate()));
        mIntent.putExtra("week", sdfWeek.format(date.getDate()));
        mIntent.putExtra("month", date.getMonth());
        mIntent.putExtra("day", date.getDay() + "");
        setResult(1, mIntent);
        finish();
    }


}
