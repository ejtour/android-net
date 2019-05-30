package com.hll_sc_app.base.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;

import com.hll_sc_app.base.R;

import java.util.Calendar;
import java.util.Date;

import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

/**
 * 日期选择只选择年份
 *
 * @author 朱英松
 * @date 2017/11/3
 */
public class DateYearWindow extends BaseShadowPopupWindow implements View.OnClickListener {
    private Activity mActivity;
    private int mYear;
    private int yearNum;
    private int mStartYear;
    private WheelView mStartYearPicker;
    private DateWindow.OnDateSelectListener mSelectListener;

    public DateYearWindow(Activity context) {
        super(context);
        this.mActivity = context;
        View rootView = View.inflate(context, R.layout.base_window_date_year, null);
        // 默认展示30年
        yearNum = 30;
        // 默认从1990年开始
        mStartYear = 2000;
        this.setContentView(rootView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setAnimationStyle(R.style.BasePopupAnimation);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable(0xb000000));
        initView(rootView);
        initStartPicker();
    }

    private void initView(View rootView) {
        Calendar startCalendar = Calendar.getInstance();
        mYear = startCalendar.get(Calendar.YEAR);
        mStartYearPicker = (WheelView) rootView.findViewById(R.id.start_year_picker);
        mStartYearPicker.setCyclic(true);
        rootView.findViewById(R.id.txt_cancel).setOnClickListener(this);
        rootView.findViewById(R.id.txt_confirm).setOnClickListener(this);
    }

    private void initStartPicker() {
        mStartYearPicker.setViewAdapter(new YearWheelAdapter(mActivity));
        mStartYearPicker.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                //no-op
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                mYear = mStartYearPicker.getCurrentItem() + mStartYear;
            }
        });
        mStartYearPicker.setCurrentItem(mYear - 1);
    }

    public void setCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        setCalendar(calendar);
    }

    public void setCalendar(Calendar calendar) {
        mYear = calendar.get(Calendar.YEAR);
        mStartYearPicker.setCurrentItem(calendar.get(Calendar.YEAR) - mStartYear);
    }

    public void setSelectListener(DateWindow.OnDateSelectListener selectListener) {
        this.mSelectListener = selectListener;
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

    public Date getSelectCalendar() {
        return toCalendar(mYear).getTime();
    }

    private Calendar toCalendar(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        return calendar;
    }

    class YearWheelAdapter extends AbstractWheelTextAdapter {

        YearWheelAdapter(Context context) {
            super(context);
        }

        @Override
        protected CharSequence getItemText(int index) {
            return mStartYear + index + "年";
        }

        @Override
        public int getItemsCount() {
            return yearNum;
        }
    }

}
