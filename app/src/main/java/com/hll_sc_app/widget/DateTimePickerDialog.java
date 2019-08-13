package com.hll_sc_app.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.dialog.BaseDialog;
import com.hll_sc_app.citymall.util.CalendarUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;

/**
 * 日期时间选择器
 */
public class DateTimePickerDialog extends BaseDialog implements OnWheelChangedListener {
    /* 标题
     */
    @BindView(R.id.wdf_title)
    TextView mTitle;

    /**
     * 选择日期文字
     */
    @BindView(R.id.txt_date_select)
    TextView mTxtDateTitle;
    /**
     * 选择时间文字
     */
    @BindView(R.id.txt_time_select)
    TextView mTxtTimeTitle;
    /**
     * 日期组
     */
    @BindView(R.id.wiker_group_date)
    Group mDateGroup;
    /**
     * 时间组
     */
    @BindView(R.id.wiker_group_time)
    Group mTimeGroup;

    /**
     * 年
     */
    @BindView(R.id.wdf_year)
    WheelView mYear;
    /**
     * 月
     */
    @BindView(R.id.wdf_month)
    WheelView mMonth;
    /**
     * 日
     */
    @BindView(R.id.wdf_day)
    WheelView mDay;
    /**
     * 时
     */
    @BindView(R.id.wdf_hour)
    WheelView mHour;

    /**
     * 时
     */
    @BindView(R.id.wdf_minute)
    WheelView mMinute;

    @BindView(R.id.view_date_divider)
    View mSelectBorder;

    @BindViews({R.id.wdf_year, R.id.wdf_month, R.id.wdf_day, R.id.wdf_hour})
    List<WheelView> mWheelViews;
    /**
     * 时间选择器的时间区间
     */
    private Calendar mBeginTime, mEndTime;
    private DatePickerDialog.WheelAdapter mYearAdapter, mMonthAdapter, mDayAdapter, mHourAdapter, mMinuteAdapter;
    private SelectCallback mCallback;

    public DateTimePickerDialog(@NonNull Activity context) {
        super(context, R.style.date_picker_dialog);
        mBeginTime = Calendar.getInstance();
        mEndTime = Calendar.getInstance();

    }

    public static DateTimePickerDialog.Builder newBuilder(Activity context) {
        return new DateTimePickerDialog.Builder(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_date_time_picker, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void show() {
        super.show();
        ButterKnife.apply(mWheelViews, (view, index) -> {
            view.removeChangingListener(this);
        });
        linkageYear();
        ButterKnife.apply(mWheelViews, (view, index) -> {
            view.addChangingListener(this);
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            window.setWindowAnimations(R.style.BasePopupAnimation);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.gravity = Gravity.BOTTOM;
            window.setAttributes(attributes);
        }
        initWheelView();
    }

    private void initWheelView() {
        mYearAdapter = new DatePickerDialog.WheelAdapter(getContext());
        mYear.setViewAdapter(mYearAdapter);
        mMonthAdapter = new DatePickerDialog.WheelAdapter(getContext());
        mMonth.setViewAdapter(mMonthAdapter);
        mDayAdapter = new DatePickerDialog.WheelAdapter(getContext());
        mDay.setViewAdapter(mDayAdapter);
        mHourAdapter = new DatePickerDialog.WheelAdapter(getContext());
        mHour.setViewAdapter(mHourAdapter);
        mMinuteAdapter = new DatePickerDialog.WheelAdapter(getContext());
        mMinute.setViewAdapter(mMinuteAdapter);
    }

    /**
     * 联动年份
     */
    private void linkageYear() {
        if (mYear.getVisibility() == View.VISIBLE) { // 如果显示年份
            Calendar select = getSelectedTime();
            if (select != null) {
                mYearAdapter.setRange(mBeginTime.get(Calendar.YEAR), mEndTime.get(Calendar.YEAR));
                mYear.setCurrentItem(select.get(Calendar.YEAR) - mYearAdapter.getMinValue());
            }
        }
        linkageMonth();
    }

    private Calendar getSelectedTime() {
        return (Calendar) mTitle.getTag();
    }

    /**
     * @param selectedTime 当前选中的时间
     */
    private void setSelectedTime(long selectedTime) {
        if (selectedTime < mBeginTime.getTimeInMillis() || selectedTime > mEndTime.getTimeInMillis()) {
            selectedTime = mBeginTime.getTimeInMillis();
        }
        saveSelectTimeTag(selectedTime);
    }

    private void saveSelectTimeTag(long selectedTime) {
        Calendar calendar = (Calendar) mTitle.getTag();
        if (calendar == null) {
            calendar = Calendar.getInstance();
            mTitle.setTag(calendar);
        }
        if (selectedTime != 0) {
            calendar.setTimeInMillis(selectedTime);
        }
    }

    /**
     * 联动月份
     */
    private void linkageMonth() {
        if (mMonth.getVisibility() == View.VISIBLE) { // 如果显示月份
            if (mYear.getVisibility() == View.VISIBLE) {// 如果显示年份
                if (mYear.getCurrentItem() == mYearAdapter.getItemsCount() - 1) { // 如果是最后一年
                    mMonthAdapter.setRange(1, mEndTime.get(Calendar.MONTH) + 1);
                } else {
                    mMonthAdapter.setRange(1, 12);
                }
            } else { // 年份不可见时
                mMonthAdapter.setRange(mBeginTime.get(Calendar.MONTH) + 1, mEndTime.get(Calendar.MONTH) + 1);
            }
            Calendar select = getSelectedTime();
            if (select != null) {
                mMonth.setCurrentItem(Math.min(
                        select.get(Calendar.MONTH) + 1 - mMonthAdapter.getMinValue(),
                        mMonthAdapter.getItemsCount() - 1));
            }
        }
        linkageDay();
    }

    /**
     * 联动天
     */
    private void linkageDay() {
        if (mDay.getVisibility() == View.VISIBLE) { // 如果显示天
            if (mMonth.getVisibility() == View.VISIBLE) { // 如果显示月份
                if ((mYear.getVisibility() == View.GONE ||
                        mYear.getCurrentItem() == mYearAdapter.getItemsCount() - 1) && // 如果最后一年
                        mMonth.getCurrentItem() == mMonthAdapter.getItemsCount() - 1) { // 如果是最后一月
                    mDayAdapter.setRange(1, mEndTime.get(Calendar.DATE));
                } else {
                    mDayAdapter.setRange(1, CalendarUtils.getEndDay(
                            mYearAdapter.getMinValue() + mYear.getCurrentItem(),  // 年份
                            mMonthAdapter.getMinValue() + mMonth.getCurrentItem() // 月份
                    ));
                }
            } else {
                mDayAdapter.setRange(mBeginTime.get(Calendar.DATE), mEndTime.get(Calendar.DATE));
            }
            Calendar select = getSelectedTime();
            if (select != null) {
                mDay.setCurrentItem(Math.min(
                        select.get(Calendar.DATE) - mDayAdapter.getMinValue(),
                        mDayAdapter.getItemsCount() - 1));
            }
        }
        linkageHour();
    }

    /**
     * 联动小时
     */
    private void linkageHour() {
        if (mHour.getVisibility() == View.VISIBLE) { // 如果显示小时
            if (mDay.getVisibility() == View.VISIBLE) { // 如果显示天
                if ((mYear.getVisibility() == View.GONE || mYear.getCurrentItem() == mYearAdapter.getItemsCount() - 1) && // 如果最后一年
                        (mMonth.getVisibility() == View.GONE || mMonth.getCurrentItem() == mMonthAdapter.getItemsCount() - 1) && // 如果最后一月
                        mDay.getCurrentItem() == mDayAdapter.getItemsCount() - 1) { // 如果是最后一天
                    mHourAdapter.setRange(0, mEndTime.get(Calendar.HOUR_OF_DAY));
                } else {
                    mHourAdapter.setRange(0, 23);
                }
            } else {
                mHourAdapter.setRange(mBeginTime.get(Calendar.DATE), mEndTime.get(Calendar.DATE));
            }
            Calendar select = getSelectedTime();
            if (select != null) {
                mHour.setCurrentItem(Math.min(
                        select.get(Calendar.HOUR_OF_DAY) - mHourAdapter.getMinValue(),
                        mHourAdapter.getItemsCount() - 1));
            }
            linageMinute();
        }
    }

    /**
     * 联动分钟
     */
    private void linageMinute() {
        if (mMinute.getVisibility() == View.VISIBLE) {//如果显示分钟
            if (mHour.getVisibility() == View.VISIBLE) {//如果显示小时
                if ((mYear.getVisibility() == View.GONE || mYear.getCurrentItem() == mYearAdapter.getItemsCount() - 1) && // 如果最后一年
                        (mMonth.getVisibility() == View.GONE || mMonth.getCurrentItem() == mMonthAdapter.getItemsCount() - 1) &&// 如果最后一月
                        (mDay.getVisibility() == View.GONE || mDay.getCurrentItem() == mDayAdapter.getItemsCount() - 1) && // 如果是最后一天
                        mHour.getCurrentItem() == mHourAdapter.getItemsCount() - 1) {// 如果是最后一小时
                    mMinuteAdapter.setRange(0, mEndTime.get(Calendar.HOUR_OF_DAY));
                } else {
                    mMinuteAdapter.setRange(0, 59);
                }
            } else {
                mMinuteAdapter.setRange(mBeginTime.get(Calendar.DATE), mEndTime.get(Calendar.DATE));
            }
            Calendar select = getSelectedTime();
            if (select != null) {
                mMinute.setCurrentItem(Math.min(
                        select.get(Calendar.MINUTE) - mMinuteAdapter.getMinValue(),
                        mMinuteAdapter.getItemsCount() - 1));
            }
        }

    }

    private void setCallback(SelectCallback callback) {
        mCallback = callback;
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        Calendar calendar = getSelectedTime();
        switch (wheel.getId()) {
            case R.id.wdf_year:
                calendar.set(Calendar.YEAR, mYearAdapter.getMinValue() + newValue);
                linkageMonth();
                break;
            case R.id.wdf_month:
                calendar.set(Calendar.MONTH, mMonthAdapter.getMinValue() + newValue - 1);
                linkageDay();
                break;
            case R.id.wdf_day:
                calendar.set(Calendar.DATE, mDayAdapter.getMinValue() + newValue);
                linkageHour();
                break;
            case R.id.wdf_hour:
                calendar.set(Calendar.HOUR_OF_DAY, mHourAdapter.getMinValue() + newValue);
                linageMinute();
                break;
            case R.id.wdf_minute:
                calendar.set(Calendar.MINUTE, mMinuteAdapter.getMinValue() + newValue);
                break;
            default:
                break;
        }
        updateText();
    }

    private void setShowYear(boolean showYear) {
        mYear.setVisibility(showYear ? View.VISIBLE : View.GONE);
    }

    private void setShowMonth(boolean showMonth) {
        mMonth.setVisibility(showMonth ? View.VISIBLE : View.GONE);
    }

    private void setShowHour(boolean showHour) {
        mHour.setVisibility(showHour ? View.VISIBLE : View.GONE);
    }

    private void setShowMinute(boolean showMinute) {
        mMinute.setVisibility(showMinute ? View.VISIBLE : View.GONE);
    }

    private void setTitleText(String title) {
        mTitle.setText(title);
    }

    private void setTimeRange(long beginTime, long endTime) {
        if (beginTime > endTime) {
            throw new IllegalArgumentException("beginTime > endTime");
        }
        mBeginTime.setTimeInMillis(beginTime);
        mEndTime.setTimeInMillis(endTime);
    }

    @OnClick({R.id.wdf_close, R.id.wdf_ok, R.id.txt_date_select, R.id.txt_time_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wdf_ok:
                if (mCallback != null) {
                    Calendar time = (Calendar) mTitle.getTag();
                    mCallback.select(time.getTime());
                }
            case R.id.wdf_close:
                dismiss();
                break;

            case R.id.txt_date_select:
                if (!mTxtTimeTitle.isSelected()) {
                    return;
                }
                mTxtDateTitle.setSelected(true);
                mTxtTimeTitle.setSelected(false);
                setSelected();
                break;
            case R.id.txt_time_select:
                if (mTxtTimeTitle.isSelected()) {
                    return;
                }
                mTxtDateTitle.setSelected(false);
                mTxtTimeTitle.setSelected(true);
                setSelected();
                break;
            default:
                break;
        }
    }

    private void setSelected() {
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mSelectBorder.getLayoutParams();
        if (mTxtDateTitle.isSelected()) {
            mDateGroup.setVisibility(View.VISIBLE);
            mTimeGroup.setVisibility(View.GONE);
            linkageYear();
            params.startToStart = R.id.txt_date_select;
            params.endToEnd = R.id.txt_date_select;
        } else {
            mDateGroup.setVisibility(View.GONE);
            mTimeGroup.setVisibility(View.VISIBLE);
            linkageHour();
            params.startToStart = R.id.txt_time_select;
            params.endToEnd = R.id.txt_time_select;
        }

        mSelectBorder.setLayoutParams(params);

    }

    private void setShowDay(boolean showDay) {
        mDay.setVisibility(showDay ? View.VISIBLE : View.GONE);
    }

    private void updateText() {
        saveSelectTimeTag(0);
    }


    public interface SelectCallback {
        default void select(Date time) {
        }

    }

    static class Params {
        private Activity context;
        /**
         * 显示年份
         */
        private boolean showYear = true;
        /**
         * 显示月份
         */
        private boolean showMonth = true;
        /**
         * 显示天
         */
        private boolean showDay = true;
        /**
         * 显示小时
         */
        private boolean showHour = true;
        /**
         * 显示分钟
         */
        private boolean showMinute = true;

        /**
         * 弹窗标题
         */
        private String title = "选择时间";

        /**
         * 选择器开始时间
         */
        private long beginTime;
        /**
         * 选择器结束时间
         */
        private long endTime;
        /**
         * 选中的时间
         */
        private long selectTime;

        /**
         * 可取消
         */
        private boolean cancelable = true;

        /**
         * 回调
         */
        private SelectCallback callback;

        Params() {
            selectTime = System.currentTimeMillis();
        }

        void apply(DateTimePickerDialog dialog) {
            dialog.setCancelable(cancelable);
            dialog.setShowYear(showYear);
            dialog.setShowMonth(showMonth);
            dialog.setShowDay(showDay);
            dialog.setShowHour(showHour);
            dialog.setShowMinute(showMinute);
            dialog.setTimeRange(beginTime, endTime);
            dialog.setSelectedTime(selectTime);
            dialog.setTitleText(title);
        }
    }

    public static class Builder {
        private final Params mParams;

        Builder(Activity context) {
            mParams = new Params();
            mParams.context = context;
        }

        public Builder setShowYear(boolean showYear) {
            mParams.showYear = showYear;
            return this;
        }

        public Builder setShowMonth(boolean showMonth) {
            mParams.showMonth = showMonth;
            return this;
        }

        public Builder setShowDay(boolean showDay) {
            mParams.showDay = showDay;
            return this;
        }

        public Builder setShowHour(boolean showHour) {
            mParams.showHour = showHour;
            return this;
        }

        public Builder setShowMinute(boolean showMinute) {
            mParams.showMinute = showMinute;
            return this;
        }


        public Builder setBeginTime(long beginTime) {
            mParams.beginTime = beginTime;
            return this;
        }

        public Builder setEndTime(long endTime) {
            mParams.endTime = endTime;
            return this;
        }

        public Builder setSelectTime(long selectTime) {
            mParams.selectTime = selectTime;
            return this;
        }

        public Builder setTitle(String title) {
            mParams.title = title;
            return this;
        }

        public Builder setCallback(SelectCallback callback) {
            mParams.callback = callback;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            mParams.cancelable = cancelable;
            return this;
        }

        public DateTimePickerDialog create() {
            DateTimePickerDialog dialog = new DateTimePickerDialog(mParams.context);
            mParams.apply(dialog);
            dialog.setCallback(mParams.callback);
            return dialog;
        }
    }


}
