package com.hll_sc_app.base.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.hll_sc_app.base.R;
import com.hll_sc_app.citymall.util.CalendarUtils;

import java.util.Calendar;
import java.util.Date;

import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

/**
 * <br>
 * <b>功能：</b>时间选择<br>
 * <b>作者：</b>HuYongcheng<br>
 * <b>日期：</b>2016/10/31<br>
 */
public class DateWindow extends BaseShadowPopupWindow implements View.OnClickListener {
    private View mRootView;
    private WheelView startMonthPicker, startDayPicker, startYearPicker;
    private int year, month, day;
    private int startYear, yearNum;
    private OnDateSelectListener mSelectListener;

    public DateWindow(Activity activity) {
        super(activity);
        mRootView = View.inflate(activity, R.layout.base_window_date, null);
        this.setContentView(mRootView);
        mRootView.setOnTouchListener(new MyTouchListener());
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setAnimationStyle(R.style.BasePopupAnimation);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        ColorDrawable dw = new ColorDrawable(0x0000000);
        this.setBackgroundDrawable(dw);
        startYear = 2000;
        yearNum = 100;

        initView();
        initStartPicker();
    }

    private void initView() {
        mRootView.findViewById(R.id.txt_cancel).setOnClickListener(this);
        mRootView.findViewById(R.id.txt_confirm).setOnClickListener(this);

        Calendar startCalendar = Calendar.getInstance();
        year = startCalendar.get(Calendar.YEAR);
        month = startCalendar.get(Calendar.MONTH) + 1;
        day = startCalendar.get(Calendar.DAY_OF_MONTH);

        startMonthPicker = mRootView.findViewById(R.id.start_month_picker);
        startMonthPicker.setCyclic(true);
        startMonthPicker.setVisibleItems(5);
        startDayPicker = mRootView.findViewById(R.id.start_day_picker);
        startDayPicker.setCyclic(true);
        startMonthPicker.setVisibleItems(5);
        startYearPicker = mRootView.findViewById(R.id.start_year_picker);
        startYearPicker.setCyclic(true);
        startYearPicker.setVisibleItems(5);
    }

    private void initStartPicker() {
        startMonthPicker.setViewAdapter(new MonthWheelAdapter(mActivity));
        startMonthPicker.setCurrentItem(month - 1);

        startDayPicker.setViewAdapter(new DayWheelAdapter(mActivity));
        startDayPicker.setCurrentItem(day - 1);

        startYearPicker.setViewAdapter(new YearWheelAdapter(mActivity));
        startYearPicker.setCurrentItem(year - startYear);
        startMonthPicker.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                // no-op
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                month = startMonthPicker.getCurrentItem() + 1;
                updateDayWheel();
            }
        });
        startDayPicker.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                // no-op
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                day = startDayPicker.getCurrentItem() + 1;
            }
        });
        startYearPicker.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                // no-op
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                year = startYearPicker.getCurrentItem() + startYear;
                updateDayWheel();
            }
        });
    }

    private void updateDayWheel() {
        day = Math.min(day, CalendarUtils.getEndDay(year, month));
        startDayPicker.setViewAdapter(new DayWheelAdapter(mActivity));
        startDayPicker.setCurrentItem(day - 1);
    }

    public void setCalendar(Date date) {
        if (date == null) return;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        setCalendar(calendar);
    }

    public void setCalendar(Calendar calendar) {
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        startYearPicker.setCurrentItem(year - startYear);
        startMonthPicker.setCurrentItem(month - 1);
        startDayPicker.setCurrentItem(day - 1);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.txt_confirm) {
            if (mSelectListener != null) {
                mSelectListener.dateSelect(getSelectCalendar());
            }
        }
        dismiss();
    }

    private Date getSelectCalendar() {
        return CalendarUtils.toCalendar(year, month, day).getTime();
    }

    public void setSelectListener(OnDateSelectListener selectListener) {
        this.mSelectListener = selectListener;
    }

    public interface OnDateSelectListener {
        /**
         * 日期确定选中
         *
         * @param date 日期
         */
        void dateSelect(Date date);
    }

    class MonthWheelAdapter extends AbstractWheelTextAdapter {
        MonthWheelAdapter(Context context) {
            super(context);
        }

        @Override
        protected CharSequence getItemText(int index) {
            return (index + 1) + "月";
        }

        @Override
        public int getItemsCount() {
            return 12;
        }
    }

    class DayWheelAdapter extends AbstractWheelTextAdapter {
        DayWheelAdapter(Context context) {
            super(context);
        }

        @Override
        protected CharSequence getItemText(int index) {
            return (index + 1) + "日";
        }

        @Override
        public int getItemsCount() {
            return CalendarUtils.getEndDay(year, month);
        }
    }

    class YearWheelAdapter extends AbstractWheelTextAdapter {
        YearWheelAdapter(Context context) {
            super(context);
        }

        @Override
        protected CharSequence getItemText(int index) {
            return startYear + index + "年";
        }

        @Override
        public int getItemsCount() {
            return yearNum;
        }
    }

    /**
     * 触摸监听器
     */
    private class MyTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int height = mRootView.findViewById(R.id.pop_layout).getTop();
            int y = (int) event.getY();
            if (event.getAction() == MotionEvent.ACTION_UP) {
                v.performClick();
                if (y < height) {
                    dismiss();
                }
            }
            return true;
        }
    }
}
