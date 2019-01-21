package com.imedical.mobiledoctor.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dhcc.calendar.CollapseCalendarView;
import com.dhcc.calendar.R;
import com.dhcc.calendar.manager.Day;
import com.dhcc.calendar.manager.Week;
import com.dhcc.calendar.widget.SquareLinearLayout;
import com.dhcc.calendar.widget.WeekView;
import com.imedical.mobiledoctor.entity.dateorder.OrderDate;
import com.imedical.mobiledoctor.entity.dateorder.ScheduleState;
import com.imedical.mobiledoctor.util.DateUtil;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Blaz Solar on 28/02/14.
 */
public class OrderRegCalendarView extends CollapseCalendarView {
    public OrderRegCalendarView(Context context) {
        super(context);
    }

    public OrderRegCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderRegCalendarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private Activity activity;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    private List<ScheduleState> mListData;
    @NonNull
    private List<LocalDate> mDateList = new ArrayList<LocalDate>();

    private String currentDay="";
    public void populateWeekLayout(@NonNull Week week, @NonNull WeekView weekView) {
        Log.d("","child populateWeekLayout" );
        List<Day> days = week.getDays();
        for (int i = 0; i < 7; i++) {
            final Day day = days.get(i);
            SquareLinearLayout dayView = (SquareLinearLayout) weekView.getChildAt(i);
            TextView tv_date = (TextView) dayView.findViewById(R.id.tv_date);
            tv_date.setText(day.getText());
            dayView.setSelected(day.isSelected());
            dayView.setBackgroundResource(R.drawable.bg_btn_calendar);
            if (day.getDate().equals(LocalDate.now()) && day.isSelected()) {
                if (mListener != null) {
                    mListener.onDateSelected(day.getDate(), false);
                }
            }

            LinearLayout view_extend = (LinearLayout) dayView.findViewById(R.id.view_extend);
            view_extend.removeAllViews();
            if (mListData != null && mListData.size() > 0
                    && mDateList != null && mDateList.size() > 0 &&
                    mDateList.indexOf(day.getDate()) != -1) {
                ScheduleState scheduleState = mListData.get(mDateList.indexOf(day.getDate()));
                if("1".equals(scheduleState.scheduleState)){
                    View view = activity.getLayoutInflater().inflate(com.imedical.mobiledoctor.R.layout.item_date_order_calendar, null);
                    ImageView iv_status=view.findViewById(com.imedical.mobiledoctor.R.id.iv_status);
                    if(day.getDate().toString().equals(currentDay)){
                        iv_status.setImageResource(com.imedical.mobiledoctor.R.drawable.img_shape_white_dot);
                    }else{
                        if(DateUtil.isBeforeToday(day.getDate().toString())){
                            iv_status.setImageResource(com.imedical.mobiledoctor.R.drawable.img_shape_gray_dot);
                        }else{
                            iv_status.setImageResource(com.imedical.mobiledoctor.R.drawable.img_shape_blue_dot);
                        }
                    }
                    view_extend.addView(view);
                }

            }
            dayView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    LocalDate date = day.getDate();
                    if (mManager.selectDay(date)) {
                        currentDay=date.toString();
                        populateLayout();
                        if (mListener != null) {
                            mListener.onDateSelected(date, false);
                        }
                    }
                }
            });
        }
    }

    public void refreshView() {
        getManager().setDisplayDay(mManager.getUnits().getTo());
        getManager().refreshView();
        mListData = (List<ScheduleState>) getManager().getExtraData().get("extraData");
        mDateList.clear();
        if (mListData != null && mListData.size() > 0) {
            Log.d("msg","mListData长度:"+mListData.size() );
            for (ScheduleState orderItem : mListData) {
                LocalDate date = LocalDate.parse(orderItem.scheduleDate);
                mDateList.add(date);
            }
        }
        populateLayout();
    }

}
