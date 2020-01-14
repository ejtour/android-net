package com.hll_sc_app.widget.analysis;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hll_sc_app.R;
import com.hll_sc_app.base.dialog.BaseDialog;
import com.hll_sc_app.base.widget.DateWindow;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.utils.Constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/17
 */

public class AnalysisWeekDialog extends BaseDialog {
    @BindView(R.id.daw_year)
    WheelView mYear;
    @BindView(R.id.daw_week)
    WheelView mWeek;
    private Map<Integer, List<Date>> mMap;
    private WeekAdapter mWeekAdapter;
    private YearAdapter mYearAdapter;
    private DateWindow.OnDateSelectListener mListener;

    public AnalysisWeekDialog(@NonNull Activity context) {
        super(context, R.style.date_picker_dialog);
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
        initData();
    }

    private void initData() {
        mMap = new HashMap<>();
        Date last = CalendarUtils.getWeekDate(-1, 1);
        int year = CalendarUtils.getYear(last);
        for (int i = 0; i < 20; i++) {
            List<Date> dates = new ArrayList<>();
            mMap.put(year - i, dates);
            if (i == 0) {
                Calendar instance = Calendar.getInstance();
                instance.setTime(last);
                dates.add(instance.getTime());
                while (instance.get(Calendar.YEAR) == year) {
                    instance.add(Calendar.WEEK_OF_YEAR, -1);
                    dates.add(instance.getTime());
                }
            } else {
                Calendar instance = Calendar.getInstance();
                instance.set(Calendar.YEAR, year - i);
                instance.set(Calendar.WEEK_OF_YEAR, 54);
                instance.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                for (int j = 0; j < 53; j++) {
                    instance.add(Calendar.WEEK_OF_YEAR, -1);
                    dates.add(instance.getTime());
                }
            }
        }
        ArrayList<Integer> list = new ArrayList<>(mMap.keySet());
        mYearAdapter.setList(list);
        mYear.setCurrentItem(0);
        mWeekAdapter.setList(mMap.get(list.get(0)));
    }

    private void initWheelView() {
        mYearAdapter = new YearAdapter(getContext());
        mYear.setViewAdapter(mYearAdapter);
        mWeekAdapter = new WeekAdapter(getContext());
        mWeek.setViewAdapter(mWeekAdapter);
        mYear.addChangingListener((wheel, oldValue, newValue) -> {
            mWeekAdapter.setList(mMap.get(mYearAdapter.getDate(newValue)));
            mWeek.setCurrentItem(0, false);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_analysis_week, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.daw_close, R.id.daw_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.daw_close:
                dismiss();
                break;
            case R.id.daw_ok:
                dismiss();
                if (mListener != null)
                    mListener.dateSelect(mWeekAdapter.getDate(mWeek.getCurrentItem()));
                break;
        }
    }

    public void setOnDateSelectListener(DateWindow.OnDateSelectListener listener) {
        mListener = listener;
    }

    static class WeekAdapter extends AbstractWheelTextAdapter {
        private List<Date> mList = new ArrayList<>();

        WeekAdapter(Context context) {
            super(context, R.layout.item_date_picker, R.id.idp_label);
        }

        @Override
        protected CharSequence getItemText(int index) {
            Date date = mList.get(index);
            return String.format("%s - %s", CalendarUtils.format(date, Constants.SLASH_YYYY_MM_DD),
                    CalendarUtils.format(CalendarUtils.getDateAfter(date, 6), Constants.SLASH_YYYY_MM_DD));
        }

        @Override
        public int getItemsCount() {
            return mList.size();
        }

        void setList(List<Date> list) {
            mList = list;
            notifyDataInvalidatedEvent();
        }

        Date getDate(int position) {
            return mList.get(position);
        }
    }

    static class YearAdapter extends AbstractWheelTextAdapter {
        List<Integer> mList = new ArrayList<>();

        YearAdapter(Context context) {
            super(context, R.layout.item_date_picker, R.id.idp_label);
        }

        @Override
        protected CharSequence getItemText(int index) {
            int year = mList.get(index);
            return String.valueOf(year);
        }

        @Override
        public int getItemsCount() {
            return mList.size();
        }

        void setList(List<Integer> list) {
            Collections.sort(list, (o1, o2) -> -o1.compareTo(o2));
            mList = list;
            notifyDataInvalidatedEvent();
        }

        int getDate(int position) {
            return mList.get(position);
        }
    }
}
