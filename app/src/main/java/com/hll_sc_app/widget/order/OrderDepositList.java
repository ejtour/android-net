package com.hll_sc_app.widget.order;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.order.detail.OrderDepositBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SimpleDecoration;

import java.util.List;

public class OrderDepositList extends RecyclerView {
    public OrderDepositList(Context context) {
        this(context, null);
    }

    public OrderDepositList(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OrderDepositList(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOverScrollMode(OVER_SCROLL_NEVER);
        setNestedScrollingEnabled(false);
        setLayoutManager(new LinearLayoutManager(context));
        addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(5)));
    }

    public void setData(List<OrderDepositBean> list) {
        if (list == null || list.size() == 0) {
            setAdapter(null);
        } else {
            setAdapter(new OrderDepositAdapter(list));
        }
    }

    class OrderDepositAdapter extends BaseQuickAdapter<OrderDepositBean, BaseViewHolder> {

        OrderDepositAdapter(@Nullable List<OrderDepositBean> data) {
            super(R.layout.item_order_deposit, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, OrderDepositBean item) {
            helper.setText(R.id.iod_name_spec, TextUtils.isEmpty(item.getProductSpec()) ?
                    item.getProductName() :
                    String.format("%s（%s）", item.getProductName(), item.getProductSpec()));
            helper.setText(R.id.iod_price, "¥" + CommonUtils.formatMoney(item.getProductPrice()));
            helper.setText(R.id.iod_num, CommonUtils.formatNum(item.getProductNum()));
            helper.setText(R.id.iod_unit, item.getSaleUnitName());
        }
    }
}
