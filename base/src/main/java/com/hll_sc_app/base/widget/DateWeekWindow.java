package com.hll_sc_app.base.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.format.DateUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.hll_sc_app.base.R;
import com.hll_sc_app.citymall.util.CalendarUtils;

import java.util.Calendar;
import java.util.Date;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

/**
 * <br>
 * <b>功能：</b>时间选择<br>
 * <b>作者：</b>chukun<br>
 * <b>日期：</b>2019/08/13<br>
 */
public class DateWeekWindow extends BaseShadowPopupWindow implements View.OnClickListener {
    private View mRootView;
    private WheelView  startWeekPicker, startYearPicker;
    private int year, week;
    private int startYear, yearNum;
    private OnDateSelectListener mSelectListener;

    public DateWeekWindow(Activity activity) {
        super(activity);
        mRootView = View.inflate(activity, R.layout.base_window_week, null);
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
        week = startCalendar.get(Calendar.WEEK_OF_YEAR) ;

        startWeekPicker = (WheelView) mRootView.findViewById(R.id.start_week_picker);
        startWeekPicker.setCyclic(true);
        startWeekPicker.setVisibleItems(5);
        startYearPicker = (WheelView) mRootView.findViewById(R.id.start_year_picker);
        startYearPicker.setCyclic(true);
        startYearPicker.setVisibleItems(5);
    }

    private void initStartPicker() {
        startYearPicker.setViewAdapter(new YearWheelAdapter(mActivity));
        startWeekPicker.setViewAdapter(new WeekWheelAdapter(mActivity));
        startYearPicker.setCurrentItem(year - startYear);
        startYearPicker.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                // no-op
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                year = startYearPicker.getCurrentItem() + startYear;
            }
        });

        startWeekPicker.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
               /* if (oldValue - newValue == 11) {
                    year++;
                }
                if (oldValue - newValue == -11) {
                    year--;
                }*/
                week = newValue + 1;
                if (week >= 52) {
                    week = 52;
                }
            }
        });
        startWeekPicker.setCurrentItem(week - 1);
        startYearPicker.setCurrentItem(year - 1);
    }

    public void setCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        setCalendar(calendar);
    }

    public void setCalendar(Calendar calendar) {
        year = calendar.get(Calendar.YEAR);
        week = calendar.get(Calendar.DAY_OF_WEEK);
        startWeekPicker.setCurrentItem(calendar.get(Calendar.WEEK_OF_YEAR));
        startYearPicker.setCurrentItem(calendar.get(Calendar.YEAR) - startYear);
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
        return CalendarUtils.toWeekCalendar(year,week).getTime();
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

    class WeekWheelAdapter extends AbstractWheelTextAdapter {
        WeekWheelAdapter(Context context) {
            super(context);
        }

        @Override
        protected CharSequence getItemText(int index) {
            return (index+1) + "周";
        }

        @Override
        public int getItemsCount() {
            return 52;
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
