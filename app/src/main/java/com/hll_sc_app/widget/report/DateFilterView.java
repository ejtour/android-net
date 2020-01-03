package com.hll_sc_app.widget.report;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ImageViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.widget.DateWeekWindow;
import com.hll_sc_app.base.widget.DateWindow;
import com.hll_sc_app.base.widget.DateYearMonthWindow;
import com.hll_sc_app.base.widget.DateYearWindow;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.Tuple;
import com.hll_sc_app.widget.ContextOptionsWindow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/11
 */

public class DateFilterView extends LinearLayout implements BaseQuickAdapter.OnItemClickListener, PopupWindow.OnDismissListener {
    private static final int DATE = 1;
    private static final int WEEK = 2;
    private static final int MONTH = 3;
    private static final int YEAR = 4;
    @BindView(R.id.rdf_date)
    TextView mDate;
    @BindView(R.id.rdf_date_arrow)
    ImageView mDateArrow;
    @BindView(R.id.rdf_gather)
    TextView mGather;
    @BindView(R.id.rdf_gather_arrow)
    ImageView mGatherArrow;
    @BindView(R.id.rdf_gather_type)
    LinearLayout mGatherType;
    @BindView(R.id.rdf_time)
    TextView mTime;
    @BindView(R.id.rdf_time_arrow)
    ImageView mTimeArrow;
    @BindView(R.id.rdf_time_type)
    LinearLayout mTimeType;
    private ContextOptionsWindow mOptionsWindow;
    private DateWindow mDateWindow;
    private DateWeekWindow mWeekWindow;
    private DateYearMonthWindow mMonthWindow;
    private DateYearWindow mYearWindow;
    private Map<String, Tuple<Integer, Date>> mDateMap = new HashMap<>();
    private DateFilterCallback mCallback;
    private int mCurType = DATE;
    private SimpleDateFormat mSdf = new SimpleDateFormat("yyyy年-MM月", Locale.getDefault());

    public DateFilterView(Context context) {
        this(context, null);
    }

    public DateFilterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DateFilterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setBaselineAligned(false);
        View view = View.inflate(context, R.layout.view_report_date_filter, this);
        ButterKnife.bind(this, view);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DateFilterView);
        int theme = array.getInt(R.styleable.DateFilterView_dfv_theme, 1);
        array.recycle();
        if (theme == 0) {
            if (getBackground() == null) {
                setBackgroundColor(Color.WHITE);
            }
            int color = ContextCompat.getColor(context, R.color.color_666666);
            mDate.setTextColor(color);
            mDate.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_date_filter, 0, 0, 0);
            ImageViewCompat.setImageTintList(mDateArrow, ContextCompat.getColorStateList(context, R.color.color_666666));
            mGather.setTextColor(color);
            mGather.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_date_statistic_666, 0, 0, 0);
            ImageViewCompat.setImageTintList(mGatherArrow, ContextCompat.getColorStateList(context, R.color.color_666666));
            mTime.setTextColor(color);
            mTime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_date, 0, 0, 0);
            ImageViewCompat.setImageTintList(mTimeArrow, ContextCompat.getColorStateList(context, R.color.color_666666));
        }
        mGather.setTag(OptionType.OPTION_STATISTIC_DATE);
        updateTimeString(DATE, new Date());
        mTimeType.setClickable(false);
    }

    public void setDateFilterCallback(DateFilterCallback callback) {
        mCallback = callback;
    }

    @OnClick({R.id.rdf_date_type, R.id.rdf_gather_type})
    public void onmDateTypeClicked(View view) {
        if (mOptionsWindow == null) {
            mOptionsWindow = new ContextOptionsWindow((Activity) getContext()).setListener(this);
        }
        List<OptionsBean> list = new ArrayList<>();
        if (view.getId() == R.id.rdf_date_type) {
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_CURRENT_DATE));
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_REPORT_YES_DATE));
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_CURRENT_WEEK));
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_REPORT_PRE_WEEK));
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_CURRENT_MONTH));
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_REPORT_PRE_MONTH));
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_REPORT_CUSTOMER_DEFINE));
            mOptionsWindow.setOnDismissListener(() -> {
                mDateArrow.setRotation(0);
            });
            mDateArrow.setRotation(180);
        } else {
            list.add(new OptionsBean(R.drawable.ic_date_statistic, OptionType.OPTION_STATISTIC_DATE));
            list.add(new OptionsBean(R.drawable.ic_date_statistic, OptionType.OPTION_STATISTIC_WEEK));
            list.add(new OptionsBean(R.drawable.ic_date_statistic, OptionType.OPTION_STATISTIC_MONTH));
            list.add(new OptionsBean(R.drawable.ic_date_statistic, OptionType.OPTION_STATISTIC_YEAR));
            mOptionsWindow.setOnDismissListener(() -> {
                mGatherArrow.setRotation(0);
            });
            mGatherArrow.setRotation(180);
        }
        mOptionsWindow.refreshList(list);
        mOptionsWindow.showAsDropDownFix(view, Gravity.LEFT);
    }

    @OnClick(R.id.rdf_time_type)
    public void selectTime() {
        if (mCurType == DATE) {
            if (mDateWindow == null) {
                mDateWindow = new DateWindow((Activity) getContext());
                mDateWindow.setSelectListener(this::dateSelect);
                mDateWindow.setOnDismissListener(this);
            }
            mDateWindow.showAtLocation(this, Gravity.BOTTOM, 0, 0);
        } else if (mCurType == WEEK) {
            if (mWeekWindow == null) {
                mWeekWindow = new DateWeekWindow((Activity) getContext());
                mWeekWindow.setSelectListener(this::dateSelect);
                mWeekWindow.setOnDismissListener(this);
            }
            mWeekWindow.showAtLocation(this, Gravity.BOTTOM, 0, 0);
        } else if (mCurType == MONTH) {
            if (mMonthWindow == null) {
                mMonthWindow = new DateYearMonthWindow((Activity) getContext());
                mMonthWindow.setSelectListener(this::dateSelect);
                mMonthWindow.setOnDismissListener(this);
            }
            mMonthWindow.showAtLocation(this, Gravity.BOTTOM, 0, 0);
        } else if (mCurType == YEAR) {
            if (mYearWindow == null) {
                mYearWindow = new DateYearWindow((Activity) getContext());
                mYearWindow.setSelectListener(this::dateSelect);
                mYearWindow.setOnDismissListener(this);
            }
            mYearWindow.showAtLocation(this, Gravity.BOTTOM, 0, 0);
        }
        mDateArrow.setRotation(180);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        // 选项监听
        OptionsBean optionsBean = (OptionsBean) adapter.getItem(position);
        if (optionsBean == null || mCallback == null) {
            return;
        }
        mOptionsWindow.dismiss();
        if (adapter.getItemCount() == 4) { // 通过选项数判断属于哪个监听
            mGather.setText(optionsBean.getLabel());
            mCallback.onTimeTypeChanged(position + 1);
            mCurType = position + 1;
            mGather.setTag(optionsBean.getLabel());
            handleGatherOption(optionsBean.getLabel());
        } else {
            mDate.setText(optionsBean.getLabel());
            mCallback.onTimeFlagChanged(position);
            if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_REPORT_CUSTOMER_DEFINE)) {
                mCallback.onTimeTypeChanged(mCurType);
                mGatherType.setVisibility(View.VISIBLE);
                mTimeType.setClickable(true);
                mTimeArrow.setVisibility(View.VISIBLE);
                handleGatherOption(mGather.getTag().toString());
            } else {
                mCallback.onTimeTypeChanged(0);
                mGatherType.setVisibility(View.GONE);
                mTimeType.setClickable(false);
                mTimeArrow.setVisibility(View.GONE);
                handleDateOption(optionsBean.getLabel());
            }
        }
    }

    private void handleDateOption(String optionType) {
        Tuple<Integer, Date> tuple = getDefaultDateByType(optionType);
        Date date = tuple.getKey2();
        mCallback.onDateChanged(CalendarUtils.toLocalDate(date));
        updateTimeString(tuple.getKey1(), tuple.getKey2());
    }

    private Tuple<Integer, Date> getDefaultDateByType(String optionType) {
        Date date = new Date();
        int type = DATE;
        switch (optionType) {
            case OptionType.OPTION_CURRENT_DATE:
            case OptionType.OPTION_STATISTIC_DATE:
                type = DATE;
                break;
            case OptionType.OPTION_REPORT_YES_DATE:
                date = CalendarUtils.getDateBefore(date, 1);
                type = DATE;
                break;
            case OptionType.OPTION_CURRENT_WEEK:
            case OptionType.OPTION_STATISTIC_WEEK:
                date = CalendarUtils.getWeekDate(0, 1);
                type = WEEK;
                break;
            case OptionType.OPTION_REPORT_PRE_WEEK:
                date = CalendarUtils.getWeekDate(-1, 1);
                type = WEEK;
                break;
            case OptionType.OPTION_CURRENT_MONTH:
            case OptionType.OPTION_STATISTIC_MONTH:
                date = CalendarUtils.getFirstDateInMonth(date);
                type = MONTH;
                break;
            case OptionType.OPTION_REPORT_PRE_MONTH:
                date = CalendarUtils.getFirstDateInMonth(date);
                date = CalendarUtils.getDateBeforeMonth(date, 1);
                type = MONTH;
                break;
            case OptionType.OPTION_STATISTIC_YEAR:
                type = YEAR;
                break;
        }
        Tuple<Integer, Date> tuple = new Tuple<>();
        tuple.setKey1(type);
        tuple.setKey2(date);
        return tuple;
    }

    private void handleGatherOption(String optionType) {
        if (mDateMap.get(optionType) == null) {
            mDateMap.put(optionType, getDefaultDateByType(optionType));
        }
        Tuple<Integer, Date> tuple = mDateMap.get(optionType);
        mCallback.onDateChanged(TextUtils.equals(optionType, OptionType.OPTION_STATISTIC_YEAR) ?
                String.valueOf(CalendarUtils.getYear(tuple.getKey2())) :
                CalendarUtils.toLocalDate(tuple.getKey2()));
        updateTimeString(tuple.getKey1(), tuple.getKey2());
    }

    private void updateTimeString(int type, Date date) {
        String result = "";
        switch (type) {
            case DATE:
                result = CalendarUtils.format(date, Constants.SLASH_YYYY_MM_DD);
                break;
            case WEEK:
                result = String.format("%s - %s", CalendarUtils.format(date, Constants.SLASH_YYYY_MM_DD),
                        CalendarUtils.format(CalendarUtils.getWeekDate(date, 0, 7), Constants.SLASH_YYYY_MM_DD));
                break;
            case MONTH:
                if (mTimeType.isClickable()) {
                    result = mSdf.format(date);
                } else {
                    result = String.format("%s - %s", CalendarUtils.format(date, Constants.SLASH_YYYY_MM_DD),
                            CalendarUtils.format(CalendarUtils.getLastDateInMonth(date), Constants.SLASH_YYYY_MM_DD));
                }
                break;
            case YEAR:
                result = String.format("%s年", CalendarUtils.getYear(date));
                break;
        }
        mTime.setText(result);
    }

    @Override
    public void onDismiss() {
        mDateArrow.setRotation(0);
    }

    public void dateSelect(Date date) {
        Tuple<Integer, Date> tuple = new Tuple<>();
        tuple.setKey1(mCurType);
        tuple.setKey2(date);
        mDateMap.put(mGather.getTag().toString(), tuple);
        handleGatherOption(mGather.getTag().toString());
    }

    public interface DateFilterCallback {
        void onTimeTypeChanged(int type);

        void onTimeFlagChanged(int flag);

        void onDateChanged(String date);
    }
}
