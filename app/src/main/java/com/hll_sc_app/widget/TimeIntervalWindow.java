package com.hll_sc_app.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.widget.BaseShadowPopupWindow;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ToastUtils;

import java.text.DecimalFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

/**
 * 时间区间选择
 *
 * @author zhuyingsong
 * @date 2019/1/22
 */
public class TimeIntervalWindow extends BaseShadowPopupWindow {
    @BindView(R.id.txt_start)
    TextView mTxtStart;
    @BindView(R.id.txt_end)
    TextView mTxtEnd;
    @BindView(R.id.hour_picker)
    WheelView mHourPicker;
    @BindView(R.id.minute_picker)
    WheelView mMinutePicker;
    private View mRootView;
    private TimeSelectListener mListener;

    public TimeIntervalWindow(Activity activity) {
        super(activity);
        mRootView = View.inflate(activity, R.layout.window_date_interval_layout, null);
        ButterKnife.bind(this, mRootView);
        mRootView.setOnTouchListener(new MyTouchListener());
        this.setContentView(mRootView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setAnimationStyle(R.style.BasePopupAnimation);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));
        mTxtStart.setSelected(true);
        initStartPicker();
        initEndPicker();
    }

    public void setOnTimeSelectListener(TimeSelectListener listener) {
        this.mListener = listener;
    }

    private void initStartPicker() {
        mHourPicker.setCyclic(true);
        mHourPicker.setVisibleItems(5);
        mHourPicker.setViewAdapter(new HourWheelAdapter(mActivity));
        mHourPicker.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                // no-op
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                if (mTxtStart.isSelected()) {
                    mTxtStart.setText(getTime());
                } else {
                    mTxtEnd.setText(getTime());
                }
            }
        });
    }

    private void initEndPicker() {
        mMinutePicker.setCyclic(true);
        mMinutePicker.setVisibleItems(5);
        mMinutePicker.setViewAdapter(new MinuteWheelAdapter(mActivity));
        mMinutePicker.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                // no-op
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                if (mTxtStart.isSelected()) {
                    mTxtStart.setText(getTime());
                } else {
                    mTxtEnd.setText(getTime());
                }
            }
        });
    }

    public void initTime(String time) {
        if (!TextUtils.isEmpty(time)) {
            String[] str = time.split("-");
            if (str.length == 2) {
                mTxtStart.setText(str[0]);
                mTxtEnd.setText(str[1]);
                mTxtStart.setSelected(true);
                mTxtEnd.setSelected(false);
                mHourPicker.setCurrentItem(getHourIndex(str[0]));
                mMinutePicker.setCurrentItem(getMinuteIndex(str[0]));
            }
        }
    }

    private int getHourIndex(String time) {
        if (!TextUtils.isEmpty(time)) {
            String hour = time.split(":")[0];
            if (!TextUtils.isEmpty(hour)) {
                return CommonUtils.getInt(hour);
            }
        }
        return 0;
    }

    private int getMinuteIndex(String time) {
        if (!TextUtils.isEmpty(time)) {
            String minute = time.split(":")[1];
            if (!TextUtils.isEmpty(minute)) {
                return CommonUtils.getInt(minute);
            }
        }
        return 0;
    }

    public String getTime() {
        return format(mHourPicker.getCurrentItem()) + ":" + format(mMinutePicker.getCurrentItem());
    }

    private String format(int index) {
        return new DecimalFormat("00").format(index);
    }

    @OnClick({R.id.txt_cancel, R.id.txt_confirm, R.id.txt_start, R.id.txt_end})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_cancel:
                dismiss();
                break;
            case R.id.txt_confirm:
                if (mListener != null) {
                    Date dateStart = CalendarUtils.parse(mTxtStart.getText().toString(), "HH:mm");
                    Date dateEnd = CalendarUtils.parse(mTxtEnd.getText().toString(), "HH:mm");
                    if (dateEnd.compareTo(dateStart) >= 0) {
                        mListener.confirm(mTxtStart.getText() + "-" + mTxtEnd.getText());
                        dismiss();
                    } else {
                        ToastUtils.showShort(mActivity, "开业时间不能大于结业时间");
                    }
                } else {
                    dismiss();
                }
                break;
            case R.id.txt_start:
                mTxtStart.setSelected(true);
                mTxtEnd.setSelected(false);
                mHourPicker.setCurrentItem(getHourIndex(mTxtStart.getText().toString()), true);
                mMinutePicker.setCurrentItem(getMinuteIndex(mTxtStart.getText().toString()), true);
                break;
            case R.id.txt_end:
                mTxtStart.setSelected(false);
                mTxtEnd.setSelected(true);
                mHourPicker.setCurrentItem(getHourIndex(mTxtEnd.getText().toString()), true);
                mMinutePicker.setCurrentItem(getMinuteIndex(mTxtEnd.getText().toString()), true);
                break;
            default:
                break;
        }
    }

    public interface TimeSelectListener {
        /**
         * 确定
         *
         * @param time 开始时间、结束时间
         */
        void confirm(String time);
    }

    /**
     * 小时
     */
    private class HourWheelAdapter extends AbstractWheelTextAdapter {

        HourWheelAdapter(Context context) {
            super(context);
        }

        @Override
        protected CharSequence getItemText(int index) {
            return String.valueOf(index) + "h";
        }

        @Override
        public int getItemsCount() {
            return 24;
        }
    }

    /**
     * 分钟
     */
    private class MinuteWheelAdapter extends AbstractWheelTextAdapter {

        MinuteWheelAdapter(Context context) {
            super(context);
        }

        @Override
        protected CharSequence getItemText(int index) {
            return format(index) + "m";
        }

        @Override
        public int getItemsCount() {
            return 60;
        }
    }

    /**
     * 触摸监听器
     */
    private class MyTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int height = mRootView.findViewById(com.hll_sc_app.base.R.id.pop_layout).getTop();
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