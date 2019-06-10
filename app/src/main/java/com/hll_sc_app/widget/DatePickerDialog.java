package com.hll_sc_app.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.hll_sc_app.citymall.util.ToastUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/5/7
 */

public class DatePickerDialog extends BaseDialog implements OnWheelChangedListener {
    /**
     * 标题
     */
    @BindView(R.id.wdf_title)
    TextView mTitle;
    /**
     * 切换按钮
     */
    @BindView(R.id.wdf_toggle)
    TextView mToggle;
    /**
     * 切换提示
     */
    @BindView(R.id.wdf_tips)
    TextView mTips;
    /**
     * 开始日期
     */
    @BindView(R.id.wdf_begin_date)
    TextView mBeginDate;
    /**
     * 结束日期
     */
    @BindView(R.id.wdf_end_date)
    TextView mEndDate;
    /**
     * 结束组
     */
    @BindView(R.id.wdf_end_group)
    Group mEndGroup;
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

    @BindViews({R.id.wdf_year, R.id.wdf_month, R.id.wdf_day, R.id.wdf_hour})
    List<WheelView> mWheelViews;

    /**
     * 时间选择器的时间区间
     */
    private Calendar mBeginTime, mEndTime;

    private SelectCallback mCallback;

    /**
     * 年、月、日、时 适配器
     */
    private WheelAdapter mYearAdapter, mMonthAdapter, mDayAdapter, mHourAdapter;
    private ToggleStatus mToggleStatus;

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
                break;
        }
        updateText();
    }

    private void updateText() {
        if (mEndDate.isSelected()) {
            setSelectEnd(0);
        } else {
            setSelectBegin(0);
        }
    }

    public interface SelectCallback {
        default void select(Date time){}

        default void select(Date beginTime, Date endTime){}
    }

    private DatePickerDialog(@NonNull Activity context) {
        super(context, R.style.date_picker_dialog);
        mBeginTime = Calendar.getInstance();
        mEndTime = Calendar.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_date_picker, null);
        ButterKnife.bind(this, view);
        return view;
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
        mYearAdapter = new WheelAdapter(getContext());
        mYear.setViewAdapter(mYearAdapter);
        mMonthAdapter = new WheelAdapter(getContext());
        mMonth.setViewAdapter(mMonthAdapter);
        mDayAdapter = new WheelAdapter(getContext());
        mDay.setViewAdapter(mDayAdapter);
        mHourAdapter = new WheelAdapter(getContext());
        mHour.setViewAdapter(mHourAdapter);
    }

    private void setToggleStatus(ToggleStatus status) {
        mToggleStatus = status;
        if (status == ToggleStatus.NONE) {
            mToggle.setVisibility(View.GONE);
        } else {
            mToggle.setVisibility(View.VISIBLE);
            mToggle.setText(status.label);
            mTips.setText(status.desc);
        }
    }

    private void setShowEnd(boolean showEnd) {
        mEndDate.setSelected(false);
        mBeginDate.setSelected(showEnd);
        mEndGroup.setVisibility(showEnd ? View.VISIBLE : View.GONE);
    }

    private void setShowYear(boolean showYear) {
        mYear.setVisibility(showYear ? View.VISIBLE : View.GONE);
    }

    private void setShowMonth(boolean showMonth) {
        mMonth.setVisibility(showMonth ? View.VISIBLE : View.GONE);
    }

    private void setShowDay(boolean showDay) {
        mDay.setVisibility(showDay ? View.VISIBLE : View.GONE);
    }

    private void setShowHour(boolean showHour) {
        mHour.setVisibility(showHour ? View.VISIBLE : View.GONE);
    }

    private void setTitleText(String title) {
        mTitle.setText(title);
    }

    /**
     * @param selectedTime 当前选中的时间
     */
    private void setSelectedTime(Calendar selectedTime) {
        ButterKnife.apply(mWheelViews, (view, index) -> {
            view.removeChangingListener(this);
        });
        if (selectedTime.getTimeInMillis() < mBeginTime.getTimeInMillis()) {
            selectedTime.setTimeInMillis(mBeginTime.getTimeInMillis());
        } else if (selectedTime.getTimeInMillis() > mEndTime.getTimeInMillis()) {
            selectedTime.setTimeInMillis(mBeginTime.getTimeInMillis());
        }
        linkageYear();
        ButterKnife.apply(mWheelViews, (view, index) -> {
            view.addChangingListener(this);
        });
    }

    private Calendar getSelectedTime() {
        if (mEndDate.isSelected()) {
            return (Calendar) mEndDate.getTag();
        } else {
            return (Calendar) mBeginDate.getTag();
        }
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
        }
    }

    private void setTimeRange(long beginTime, long endTime) {
        if (beginTime > endTime) {
            throw new IllegalArgumentException("beginTime > endTime");
        }
        mBeginTime.setTimeInMillis(beginTime);
        mEndTime.setTimeInMillis(endTime);
    }

    private void setSelectBegin(long selectBegin) {
        Calendar calendar = (Calendar) mBeginDate.getTag();
        if (calendar == null) {
            calendar = Calendar.getInstance();
            mBeginDate.setTag(calendar);
        }
        if (selectBegin != 0) {
            calendar.setTimeInMillis(selectBegin);
        }
        mBeginDate.setText(CalendarUtils.format(calendar.getTime(), getFormatString()));
    }

    private void setSelectEnd(long selectEnd) {
        Calendar calendar = (Calendar) mEndDate.getTag();
        if (calendar == null) {
            calendar = Calendar.getInstance();
            mEndDate.setTag(calendar);
        }
        if (selectEnd != 0) {
            calendar.setTimeInMillis(selectEnd);
        }
        mEndDate.setText(CalendarUtils.format(calendar.getTime(), getFormatString()));
    }

    private String getFormatString() {
        StringBuilder sb = new StringBuilder();
        if (mYear.getVisibility() == View.VISIBLE) {
            sb.append("yyyy");
        }
        if (mMonth.getVisibility() == View.VISIBLE) {
            sb.append("-MM");
        }
        if (mDay.getVisibility() == View.VISIBLE) {
            sb.append("-dd");
        }
        if (mHour.getVisibility() == View.VISIBLE) {
            sb.append(" HH");
        }
        if (sb.toString().startsWith("-")) {
            sb.deleteCharAt(0);
        }
        return sb.toString().trim();
    }

    @OnClick({R.id.wdf_close, R.id.wdf_ok, R.id.wdf_toggle, R.id.wdf_begin_date, R.id.wdf_end_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wdf_ok:
                if (mCallback != null) {
                    Calendar start = (Calendar) mBeginDate.getTag();
                    if (mEndDate.getVisibility() == View.VISIBLE) { // 如果是选时间间隔
                        Date endTime = ((Calendar) mEndDate.getTag()).getTime();
                        Date date = CalendarUtils.getDateBefore(endTime, mToggleStatus.duration);
                        if (start.getTimeInMillis() > endTime.getTime()) {
                            ToastUtils.showShort(getContext(), "开始时间大于结束时间");
                            return;
                        }
                        if (date.getTime() > start.getTimeInMillis() && mToggleStatus.duration > 0) { // 确保时间间隔大于 90 天
                            ToastUtils.showShort(getContext(), "时间间隔大于90天");
                            // start.setTimeInMillis(date.getTime());
                            return;
                        }
                        mCallback.select(start.getTime(), endTime);
                    } else {
                        mCallback.select(start.getTime());
                    }
                }
            case R.id.wdf_close:
                dismiss();
                break;
            case R.id.wdf_toggle:
                if (mToggleStatus == ToggleStatus.DAY) {
                    setToggleStatus(ToggleStatus.MONTH);
                    setShowEnd(false);
                    setShowDay(false);
                } else if (mToggleStatus == ToggleStatus.MONTH) {
                    setToggleStatus(ToggleStatus.DAY);
                    setShowEnd(true);
                    setShowDay(true);
                }
                updateText();
                setSelectedTime(((Calendar) mBeginDate.getTag()));
                break;
            case R.id.wdf_begin_date:
                if (!mEndDate.isSelected()) { // 表示选中了起始时间，(tip: 在单时间选择模式下，起始时间默认是未选中状态的，see L208-210 )
                    return;
                }
                mBeginDate.setSelected(true);
                mEndDate.setSelected(false);
                setSelectedTime(((Calendar) view.getTag()));
                break;
            case R.id.wdf_end_date:
                if (mEndDate.isSelected()) {
                    return;
                }
                mBeginDate.setSelected(false);
                mEndDate.setSelected(true);
                setSelectedTime(((Calendar) view.getTag()));
                break;
        }
    }

    @Override
    public void show() {
        super.show();
        if (mEndDate.isSelected()) {
            setSelectedTime(((Calendar) mEndDate.getTag()));
        } else {
            setSelectedTime(((Calendar) mBeginDate.getTag()));
        }
    }

    class WheelAdapter extends AbstractWheelTextAdapter {
        /**
         * 默认的最小值
         */
        private static final int DEFAULT_MAX_VALUE = 9;

        /**
         * 默认的最大值
         */
        private static final int DEFAULT_MIN_VALUE = 0;

        // Values
        private int minValue;
        private int maxValue;

        // format
        private String format;

        WheelAdapter(Context context) {
            this(context, DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE);
        }

        /**
         * @param minValue 最小值
         * @param maxValue 最大值
         */
        WheelAdapter(Context context, int minValue, int maxValue) {
            this(context, minValue, maxValue, null);
        }

        /**
         * @param minValue 最小值
         * @param maxValue 最大值
         * @param format   格式
         */
        WheelAdapter(Context context, int minValue, int maxValue, String format) {
            super(context, R.layout.item_date_picker, R.id.idp_label);
            this.minValue = minValue;
            this.maxValue = maxValue;
            this.format = format;
        }

        @Override
        public CharSequence getItemText(int index) {
            if (index >= 0 && index < getItemsCount()) {
                int value = minValue + index;
                return format != null ? String.format(format, value) : Integer.toString(value);
            }
            return null;
        }

        @Override
        public int getItemsCount() {
            return maxValue - minValue + 1;
        }

        /**
         * @param minValue 最小值
         * @param maxValue 最大值
         */
        private void setRange(int minValue, int maxValue) {
            if (minValue > maxValue) {
                throw new IllegalArgumentException("minValue > maxValue");
            }
            if (this.minValue == minValue && this.maxValue == maxValue) {
                return;
            }
            this.minValue = minValue;
            this.maxValue = maxValue;
            notifyDataInvalidatedEvent();
        }

        int getMinValue() {
            return minValue;
        }
    }

    public static Builder newBuilder(Activity context) {
        return new Builder(context);
    }

    public static class Builder {
        private final Params mParams;

        Builder(Activity context) {
            mParams = new Params();
            mParams.context = context;
        }

        public Builder setToggleStatus(ToggleStatus status) {
            mParams.toggleStatus = status;
            return this;
        }

        public Builder setShowEnd(boolean showEnd) {
            mParams.showEnd = showEnd;
            return this;
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

        public Builder setBeginTime(long beginTime) {
            mParams.beginTime = beginTime;
            return this;
        }

        public Builder setEndTime(long endTime) {
            mParams.endTime = endTime;
            return this;
        }

        public Builder setSelectBeginTime(long selectBegin) {
            mParams.selectBegin = selectBegin;
            return this;
        }

        public Builder setSelectEndTime(long selectEnd) {
            mParams.selectEnd = selectEnd;
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

        public DatePickerDialog create() {
            DatePickerDialog dialog = new DatePickerDialog(mParams.context);
            mParams.apply(dialog);
            dialog.setCallback(mParams.callback);
            return dialog;
        }
    }

    public enum ToggleStatus {
        NONE,
        DAY("按日筛选", "*按日筛选仅支持90天以内", 90),
        MONTH("按月筛选", "", 0);
        private String label;
        private String desc;
        private int duration;

        ToggleStatus() {
        }

        ToggleStatus(String label, String desc, int duration) {
            this.label = label;
            this.desc = desc;
            this.duration = duration;
        }
    }

    static class Params {
        private Activity context;
        /**
         * 切换状态
         */
        private ToggleStatus toggleStatus;
        /**
         * 显示结束时间
         */
        private boolean showEnd = true;
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
        private boolean showHour;

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
         * 选中的开始时间
         */
        private long selectBegin;

        /**
         * 选中的结束时间
         */
        private long selectEnd;

        /**
         * 可取消
         */
        private boolean cancelable = true;

        /**
         * 回调
         */
        private SelectCallback callback;

        Params() {
            toggleStatus = ToggleStatus.NONE;
            selectBegin = selectEnd = System.currentTimeMillis();
        }

        void apply(DatePickerDialog dialog) {
            dialog.setCancelable(cancelable);
            dialog.setToggleStatus(toggleStatus);
            dialog.setShowEnd(showEnd);
            dialog.setShowYear(showYear);
            dialog.setShowMonth(showMonth);
            dialog.setShowDay(showDay);
            dialog.setShowHour(showHour);
            dialog.setTimeRange(beginTime, endTime);
            dialog.setSelectBegin(selectBegin);
            dialog.setSelectEnd(selectEnd);
            dialog.setTitleText(title);
        }
    }
}
