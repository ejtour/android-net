package com.hll_sc_app.app.cooperation.detail.shopsettlement;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.hll_sc_app.R;
import com.hll_sc_app.base.widget.BaseShadowPopupWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

/**
 * 选择结算方式-账期选择
 *
 * @author 朱英松
 * @date 2017/11/3
 */
public class AccountPeriodSelectWindow extends BaseShadowPopupWindow implements View.OnClickListener {
    private String mSelectPayTermType;
    private PayTermTypeAdapter mPayTermTypeAdapter;

    private int mSelectPayTerm;
    private WheelView mPayTermPicker;
    private WheelAdapter mPayTermAdapter;

    private Map<String, List<Integer>> mMap;
    private DateSelectListener mSelectListener;

    AccountPeriodSelectWindow(Activity context) {
        super(context);
        initMap();
        View rootView = View.inflate(context, R.layout.window_type_term_select, null);
        this.setContentView(rootView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setAnimationStyle(R.style.BasePopupAnimation);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable(0xb000000));
        initStartPicker(rootView);
    }

    private void initMap() {
        mMap = new HashMap<>();
        List<Integer> listWeek = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            listWeek.add(i);
        }
        mMap.put("周结", listWeek);
        List<Integer> listMonth = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            listMonth.add(i);
        }
        mMap.put("月结", listMonth);
    }

    private void initStartPicker(View rootView) {
        WheelView payTermTypePicker = rootView.findViewById(R.id.picker_payTermType);
        payTermTypePicker.setVisibleItems(5);
        mPayTermTypeAdapter = new PayTermTypeAdapter(mActivity, new ArrayList<>(mMap.keySet()));
        mSelectPayTermType = String.valueOf(mPayTermTypeAdapter.getItemText(0));
        payTermTypePicker.setViewAdapter(mPayTermTypeAdapter);
        payTermTypePicker.setCurrentItem(0);
        payTermTypePicker.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                //no-op
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                mSelectPayTermType = String.valueOf(mPayTermTypeAdapter.getItemText(wheel.getCurrentItem()));
                mPayTermAdapter.setList(mMap.get(mSelectPayTermType),
                    TextUtils.equals("周结", mSelectPayTermType) ? CooperationShopSettlementActivity.TERM_WEEK :
                        CooperationShopSettlementActivity.TERM_MONTH);
                mPayTermPicker.setCurrentItem(0, true);
                mSelectPayTerm = mPayTermAdapter.getItem(0);
            }
        });


        mPayTermPicker = rootView.findViewById(R.id.picker_payTerm);
        mPayTermPicker.setVisibleItems(5);
        mPayTermAdapter = new WheelAdapter(mActivity,
            mMap.get(mSelectPayTermType),
            TextUtils.equals("周结", mSelectPayTermType) ? CooperationShopSettlementActivity.TERM_WEEK :
                CooperationShopSettlementActivity.TERM_MONTH);
        mSelectPayTerm = mPayTermAdapter.getItem(0);
        mPayTermPicker.setViewAdapter(mPayTermAdapter);
        mPayTermPicker.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                //no-op
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                mSelectPayTerm = mPayTermAdapter.getItem(wheel.getCurrentItem());
            }
        });
        mPayTermPicker.setCurrentItem(0);

        rootView.findViewById(R.id.txt_cancel).setOnClickListener(this);
        rootView.findViewById(R.id.txt_confirm).setOnClickListener(this);
    }

    void setSelectListener(DateSelectListener selectListener) {
        this.mSelectListener = selectListener;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.txt_confirm) {
            if (mSelectListener != null) {
                mSelectListener.select(mSelectPayTermType, mSelectPayTerm);
            }
        }
        dismiss();
    }

    /**
     * 点击确定后监听
     */
    public interface DateSelectListener {
        /**
         * 选择的配送日期、时间
         *
         * @param payTermType 周期
         * @param payTerm     时间
         */
        void select(String payTermType, int payTerm);
    }

    class WheelAdapter extends AbstractWheelTextAdapter {
        private List<Integer> mList;
        private String type;

        WheelAdapter(Context context, List<Integer> list, String type) {
            super(context, R.layout.window_date_select_item, R.id.txt_select_item);
            this.mList = list;
            this.type = type;
        }

        @Override
        protected CharSequence getItemText(int index) {
            if (TextUtils.equals(type, CooperationShopSettlementActivity.TERM_WEEK)) {
                return CooperationShopSettlementActivity.getPayTermStr(mList.get(index));
            } else if (TextUtils.equals(type, CooperationShopSettlementActivity.TERM_MONTH)) {
                return "每月" + mList.get(index) + "号";
            }
            return null;
        }

        public Integer getItem(int index) {
            return mList.get(index);
        }

        @Override
        public int getItemsCount() {
            return mList == null ? 0 : mList.size();
        }

        public void setList(List<Integer> list, String type) {
            this.mList = list;
            this.type = type;
            notifyDataInvalidatedEvent();
        }
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
