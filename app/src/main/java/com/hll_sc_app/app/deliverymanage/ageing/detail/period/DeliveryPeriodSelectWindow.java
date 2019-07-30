package com.hll_sc_app.app.deliverymanage.ageing.detail.period;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;

import com.hll_sc_app.R;
import com.hll_sc_app.base.widget.BaseShadowPopupWindow;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

/**
 * 新增配送时段
 *
 * @author 朱英松
 * @date 2019/7/29
 */
public class DeliveryPeriodSelectWindow extends BaseShadowPopupWindow implements View.OnClickListener {
    private String mSelectEnd;
    private List<String> mData;
    private String mSelectStart;

    private PayTermTypeAdapter mAdapter;
    private PayTermTypeAdapter mAdapterEnd;
    private PeriodSelectListener mSelectListener;

    DeliveryPeriodSelectWindow(Activity context) {
        super(context);
        initData();
        View rootView = View.inflate(context, R.layout.window_delivery_term_select, null);
        this.setContentView(rootView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setAnimationStyle(R.style.BasePopupAnimation);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable(0xb000000));
        initStartPicker(rootView);
    }

    private void initData() {
        mData = new ArrayList<>();
        DecimalFormat format = new DecimalFormat("00");
        for (int i = 0; i < 24; i++) {
            mData.add(format.format(i) + ":00");
        }
    }

    private void initStartPicker(View rootView) {
        WheelView pickerStart = rootView.findViewById(R.id.picker_start);
        pickerStart.setVisibleItems(5);
        mAdapter = new PayTermTypeAdapter(mActivity, mData);
        mSelectStart = String.valueOf(mAdapter.getItemText(0));
        pickerStart.setViewAdapter(mAdapter);
        pickerStart.setCurrentItem(0);
        pickerStart.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                //no-op
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                mSelectStart = String.valueOf(mAdapter.getItemText(wheel.getCurrentItem()));
            }
        });

        WheelView pickerEnd = rootView.findViewById(R.id.picker_end);
        pickerEnd.setVisibleItems(5);
        mAdapterEnd = new PayTermTypeAdapter(mActivity, mData);
        mSelectEnd = String.valueOf(mAdapterEnd.getItemText(0));
        pickerEnd.setViewAdapter(mAdapterEnd);
        pickerEnd.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                //no-op
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                mSelectEnd = String.valueOf(mAdapterEnd.getItemText(wheel.getCurrentItem()));
            }
        });
        pickerEnd.setCurrentItem(0);

        rootView.findViewById(R.id.txt_cancel).setOnClickListener(this);
        rootView.findViewById(R.id.txt_confirm).setOnClickListener(this);
    }

    void setSelectListener(PeriodSelectListener selectListener) {
        this.mSelectListener = selectListener;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.txt_confirm) {
            if (mSelectListener != null) {
                mSelectListener.select(mSelectStart, mSelectEnd);
            }
        }
        dismiss();
    }

    /**
     * 点击确定后监听
     */
    public interface PeriodSelectListener {
        /**
         * 新增配送时段确定
         *
         * @param start 开始时间
         * @param end   结束时间
         */
        void select(String start, String end);
    }

    class PayTermTypeAdapter extends AbstractWheelTextAdapter {
        private List<String> mList;

        PayTermTypeAdapter(Context context, List<String> list) {
            super(context, R.layout.window_date_select_item, R.id.txt_select_item);
            this.mList = list;
        }

        @Override
        protected CharSequence getItemText(int index) {
            return mList.get(index);
        }

        @Override
        public int getItemsCount() {
            return mList == null ? 0 : mList.size();
        }

        public void setList(List<String> list) {
            this.mList = list;
            notifyDataInvalidatedEvent();
        }
    }
}
