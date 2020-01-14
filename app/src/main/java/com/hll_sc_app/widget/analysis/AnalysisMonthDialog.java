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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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

public class AnalysisMonthDialog extends BaseDialog {
    @BindView(R.id.dam_year)
    WheelView mYear;
    @BindView(R.id.dam_month)
    WheelView mMonth;
    private YearAdapter mYearAdapter;
    private MonthAdapter mMonthAdapter;
    private Map<Integer, Integer> mMap;
    private DateWindow.OnDateSelectListener mListener;

    public AnalysisMonthDialog(@NonNull Activity context) {
        super(context, R.style.date_picker_dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_analysis_month, null);
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
        initData();
    }

    private void initData() {
        mMap = new HashMap<>();
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MONTH, -1);
        int year = instance.get(Calendar.YEAR);
        int month = instance.get(Calendar.MONTH) + 1;
        for (int i = 0; i < 20; i++) {
            mMap.put(year - i, i == 0 ? month : 12);
        }
        mYearAdapter.setList(new ArrayList<>(mMap.keySet()));
        mYear.setCurrentItem(0);
        mMonthAdapter.setMaxMonth(month);
    }

    private void initWheelView() {
        mYearAdapter = new YearAdapter(getContext());
        mYear.setViewAdapter(mYearAdapter);
        mMonthAdapter = new MonthAdapter(getContext());
        mMonth.setViewAdapter(mMonthAdapter);
        mYear.addChangingListener((wheel, oldValue, newValue) -> {
            mMonthAdapter.setMaxMonth(mMap.get(mYearAdapter.getDate(newValue)));
            mMonth.setCurrentItem(0, false);
        });
    }

    @OnClick({R.id.dam_close, R.id.dam_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dam_close:
                dismiss();
                break;
            case R.id.dam_ok:
                dismiss();
                if (mListener != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, mYearAdapter.getDate(mYear.getCurrentItem()));
                    calendar.set(Calendar.MONTH, mMonthAdapter.getMonth(mMonth.getCurrentItem()));
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                    mListener.dateSelect(calendar.getTime());
                }
                break;
        }
    }

    public void setOnDateSelectListener(DateWindow.OnDateSelectListener listener) {
        mListener = listener;
    }

    private static class MonthAdapter extends AbstractWheelTextAdapter {
        private int mMonth;

        MonthAdapter(Context context) {
            super(context, R.layout.item_date_picker, R.id.idp_label);
        }

        @Override
        protected CharSequence getItemText(int index) {
            return String.format("%s月", mMonth - index);
        }

        @Override
        public int getItemsCount() {
            return mMonth;
        }

        void setMaxMonth(int month) {
            mMonth = month;
            notifyDataInvalidatedEvent();
        }

        int getMonth(int position) {
            return mMonth - position - 1;
        }
    }

    private static class YearAdapter extends AbstractWheelTextAdapter {
        List<Integer> mList = new ArrayList<>();

        YearAdapter(Context context) {
            super(context, R.layout.item_date_picker, R.id.idp_label);
        }

        @Override
        protected CharSequence getItemText(int index) {
            return String.valueOf(mList.get(index));
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
