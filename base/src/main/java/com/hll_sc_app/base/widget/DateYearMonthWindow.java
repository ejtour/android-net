package com.hll_sc_app.base.widget;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.hll_sc_app.base.R;
import com.hll_sc_app.citymall.util.CalendarUtils;

import java.util.Calendar;
import java.util.Date;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

/**
 * autour: twq
 * create: 2017/8/15 10:28
 * desc:年月的时间选择器window
 */
public class DateYearMonthWindow extends PopupWindow implements View.OnClickListener {
    private Activity activity;
    private WheelView startYearPicker;
    private WheelView startMonthPicker;
    private int year, month, day;
    private int startYear, yearNum;
    private DateWindow.OnDateSelectListener mSelectListener;

    public DateYearMonthWindow(Activity context) {
        this.activity = context;
        View rootView = View.inflate(context, R.layout.base_window_date_year_month, null);
        // 默认从1990年开始
        startYear = 2000;
        // 默认展示30年
        yearNum = 30;
        initWindow(rootView);
        initView(rootView);
        initStartPicker();
    }

    private void initWindow(View rootView) {
        this.setContentView(rootView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setAnimationStyle(R.style.BasePopupAnimation);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        ColorDrawable dw = new ColorDrawable(0xb000000);
        this.setBackgroundDrawable(dw);
    }

    private void initView(View rootView) {
        rootView.findViewById(R.id.txt_cancel).setOnClickListener(this);
        rootView.findViewById(R.id.txt_confirm).setOnClickListener(this);
        Calendar startCalendar = Calendar.getInstance();
        year = startCalendar.get(Calendar.YEAR);
        month = startCalendar.get(Calendar.MONTH) + 1;
        day = startCalendar.get(Calendar.DAY_OF_MONTH);
        startYearPicker = (WheelView) rootView.findViewById(R.id.start_year_picker);
        startMonthPicker = (WheelView) rootView.findViewById(R.id.start_month_picker);
        startYearPicker.setCyclic(true);
        startMonthPicker.setCyclic(true);
    }

    private void initStartPicker() {
        startYearPicker.setViewAdapter(new YearWheelAdapter(activity));
        startMonthPicker.setViewAdapter(new MonthWheelAdapter(activity));
        startYearPicker.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
            }
        });

        startMonthPicker.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
               /* if (oldValue - newValue == 11) {
                    year++;
                }
                if (oldValue - newValue == -11) {
                    year--;
                }*/
                month = newValue + 1;
                int maxD = CalendarUtils.getEndDay(year, month);
                if (day > maxD) {
                    day = maxD;
                }
            }
        });
        startMonthPicker.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
            }
        });

        startYearPicker.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                year = startYearPicker.getCurrentItem() + startYear;
            }
        });

        startMonthPicker.setCurrentItem(month - 1);
        startYearPicker.setCurrentItem(year - 1);
    }

    public void setCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        setCalendar(calendar);
    }

    public void setCalendar(Calendar calendar) {
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        startMonthPicker.setCurrentItem(calendar.get(Calendar.MONTH));
        startYearPicker.setCurrentItem(calendar.get(Calendar.YEAR) - startYear);
    }

    public void setSelectListener(DateWindow.OnDateSelectListener selectListener) {
        this.mSelectListener = selectListener;
    }

    /**
     * 配置起始年份
     * 注意！！！该方法在 setCalendar 方法前调用   后期待优化
     *
     * @param startYear 起始年
     * @param yearCount 个数  最终年 = startYear + yearCount
     */
    public void initYear(int startYear, int yearCount) {
        int currentYear = CalendarUtils.getYear();
        if (currentYear < startYear || currentYear > (yearCount + startYear)) {//如果当前年不在配置范围内，回复默认设置
            return;
        }
        this.startYear = startYear;
        this.yearNum = yearCount;
        initStartPicker();
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        ValueAnimator animator = ValueAnimator.ofFloat(1.0f, 0.6f).setDuration(250);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float cVal = (Float) valueAnimator.getAnimatedValue();
                setWindowAlpha(cVal);
            }
        });
        animator.start();
        super.showAtLocation(parent, gravity, x, y);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        ValueAnimator animator = ValueAnimator.ofFloat(0.6f, 1.0f).setDuration(250);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float cVal = (Float) valueAnimator.getAnimatedValue();
                setWindowAlpha(cVal);
            }
        });
        animator.start();
    }

    private void setWindowAlpha(float alpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = alpha;
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity.getWindow().setAttributes(lp);
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
        return CalendarUtils.toCalendar(year, month, day).getTime();
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
}
