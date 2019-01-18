package com.dhcc.calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dhcc.calendar.manager.CalendarManager;
import com.dhcc.calendar.manager.Day;
import com.dhcc.calendar.manager.Formatter;
import com.dhcc.calendar.manager.Month;
import com.dhcc.calendar.manager.ResizeManager;
import com.dhcc.calendar.manager.Week;
import com.dhcc.calendar.widget.SquareLinearLayout;
import com.dhcc.calendar.widget.WeekView;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Blaz Solar on 28/02/14.
 */
public class CollapseCalendarView extends LinearLayout implements View.OnClickListener {

    public static final String TAG = "CalendarView";

    @Nullable
    public CalendarManager mManager;

    @NonNull
    public TextView mTitleView;
    @NonNull
    public TextView tv_today;
    @NonNull
    public ImageButton mPrev;
    @NonNull
    public ImageButton mNext;
    @NonNull
    public LinearLayout mWeeksView;

    @NonNull
    public final LayoutInflater mInflater;
    @NonNull
    public final RecycleBin mRecycleBin = new RecycleBin();

    @Nullable
    public OnDateSelect mListener;

    @NonNull
    public TextView mSelectionText;
    @NonNull
    public LinearLayout mHeader;

    @NonNull
    public ResizeManager mResizeManager;



    public boolean initialized;

    public CollapseCalendarView(Context context) {
        this(context, null);
    }

    public CollapseCalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.calendarViewStyle);
    }

    public CollapseCalendarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mInflater = LayoutInflater.from(context);

        mResizeManager = new ResizeManager(this);

        inflate(context, R.layout.calendar_layout, this);

        setOrientation(VERTICAL);
    }

    public void init(@NonNull CalendarManager manager) {
        if (manager != null) {

            mManager = manager;

            populateLayout();

            if (mListener != null) {
                LocalDate date = LocalDate.now();
                mListener.onDateSelected(mManager.getSelectedDay(), false);
                mListener.onMonthChange(getFirst(), getLast());
            }

        }
    }

    public LocalDate getFirst() {
        LocalDate fromDate = mManager.getUnits().getTo();
        return fromDate.withDayOfMonth(1).withDayOfWeek(1);
    }

    public LocalDate getLast() {
        LocalDate toDate = mManager.getUnits().getTo();
        return toDate.withDayOfMonth(toDate.getDayOfMonth()).withDayOfWeek(DateTimeConstants.SUNDAY);
    }

    @Nullable
    public CalendarManager getManager() {
        return mManager;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "On click");
        if (mManager != null) {
            int id = v.getId();
            if (id == R.id.prev) {
                if (mManager.prev()) {
                    mListener.onMonthChange(getFirst(), getLast());
                    populateLayout();
                }
            } else if (id == R.id.next) {
                Log.d(TAG, "next");
                if (mManager.next()) {
                    Log.d(TAG, "populate");
                    mListener.onMonthChange(getFirst(), getLast());
                    populateLayout();
                }
            } else if (id == R.id.tv_today) {
                LocalDate date = LocalDate.now();
                if (mManager.selectDay(date)) {
                    if (mListener != null) {
                        mListener.onDateSelected(date, true);
                    }
                }

                mManager.today();
                mListener.onMonthChange(getFirst(), getLast());
                populateLayout();
            }

        }
    }

    @SuppressLint("WrongCall")
    @Override
    protected void dispatchDraw(@NonNull Canvas canvas) {
        mResizeManager.onDraw();

        super.dispatchDraw(canvas);
    }

    @Nullable
    public CalendarManager.State getState() {
        if (mManager != null) {
            return mManager.getState();
        } else {
            return null;
        }
    }

    public void setListener(@Nullable OnDateSelect listener) {
        mListener = listener;
    }


    /**
     * @deprecated This will be removed
     */
    public void setTitle(@Nullable String text) {
        if (text == null || "".equals(text)) {
            mHeader.setVisibility(View.VISIBLE);
            mSelectionText.setVisibility(View.GONE);
        } else {
            mHeader.setVisibility(View.GONE);
            mSelectionText.setVisibility(View.VISIBLE);
            mSelectionText.setText(text);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mResizeManager.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        super.onTouchEvent(event);

        return mResizeManager.onTouchEvent(event);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mTitleView = (TextView) findViewById(R.id.title);
        mPrev = (ImageButton) findViewById(R.id.prev);
        mNext = (ImageButton) findViewById(R.id.next);
        tv_today = (TextView) findViewById(R.id.tv_today);
        mWeeksView = (LinearLayout) findViewById(R.id.weeks);

        mHeader = (LinearLayout) findViewById(R.id.header);
        mSelectionText = (TextView) findViewById(R.id.selection_title);

        mPrev.setOnClickListener(this);
        mNext.setOnClickListener(this);
        tv_today.setOnClickListener(this);

        populateLayout();
    }

    public void populateDays() {

        if (!initialized) {
            CalendarManager manager = getManager();

            if (manager != null) {
                Formatter formatter = manager.getFormatter();

                LinearLayout layout = (LinearLayout) findViewById(R.id.days);

                LocalDate date = LocalDate.now().withDayOfWeek(DateTimeConstants.MONDAY);
                for (int i = 0; i < 7; i++) {
                    TextView textView = (TextView) layout.getChildAt(i);
                    textView.setText(formatter.getDayName(date));
                    date = date.plusDays(1);
                }

                initialized = true;
            }
        }

    }

    public void populateLayout() {

        if (mManager != null) {

            populateDays();

            mPrev.setEnabled(mManager.hasPrev());
            mNext.setEnabled(mManager.hasNext());

            mTitleView.setText(mManager.getHeaderText());

            if (mManager.getState() == CalendarManager.State.MONTH) {
                populateMonthLayout((Month) mManager.getUnits());
            } else {
                populateWeekLayout((Week) mManager.getUnits());
            }
        }

    }

    public void populateMonthLayout(Month month) {

        List<Week> weeks = month.getWeeks();
        int cnt = weeks.size();
        for (int i = 0; i < cnt; i++) {
            WeekView weekView = getWeekView(i);
            populateWeekLayout(weeks.get(i), weekView);
        }

        int childCnt = mWeeksView.getChildCount();
        if (cnt < childCnt) {
            for (int i = cnt; i < childCnt; i++) {
                cacheView(i);
            }
        }

    }

    public void populateWeekLayout(Week week) {
        WeekView weekView = getWeekView(0);
        populateWeekLayout(week, weekView);

        int cnt = mWeeksView.getChildCount();
        if (cnt > 1) {
            for (int i = cnt - 1; i > 0; i--) {
                cacheView(i);
            }
        }
    }

    public void populateWeekLayout(@NonNull Week week, @NonNull WeekView weekView) {
        Log.d("","parent populateWeekLayout" );
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
            dayView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    LocalDate date = day.getDate();
                    if (mManager.selectDay(date)) {
                        populateLayout();
                        if (mListener != null) {
                            mListener.onDateSelected(date, false);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    public LinearLayout getWeeksView() {
        return mWeeksView;
    }

    @NonNull
    public WeekView getWeekView(int index) {
        int cnt = mWeeksView.getChildCount();

        if (cnt < index + 1) {
            for (int i = cnt; i < index + 1; i++) {
                View view = getView();
                mWeeksView.addView(view);
            }
        }

        return (WeekView) mWeeksView.getChildAt(index);
    }

    public View getView() {
        View view = mRecycleBin.recycleView();
        if (view == null) {
            view = mInflater.inflate(R.layout.week_layout, this, false);
        } else {
            view.setVisibility(View.VISIBLE);
        }
        return view;
    }

    public void cacheView(int index) {
        View view = mWeeksView.getChildAt(index);
        if (view != null) {
            mWeeksView.removeViewAt(index);
            mRecycleBin.addView(view);
        }
    }

    public LocalDate getSelectedDate() {
        return mManager.getSelectedDay();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        mResizeManager.recycle();
    }

    public class RecycleBin {

        public final Queue<View> mViews = new LinkedList<View>();

        @Nullable
        public View recycleView() {
            return mViews.poll();
        }

        public void addView(@NonNull View view) {
            mViews.add(view);
        }

    }

    public interface OnDateSelect {
        public void onDateSelected(LocalDate date, Boolean isToday);

        public void onMonthChange(LocalDate fromDate, LocalDate toDate);
    }
}
