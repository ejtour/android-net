package com.hll_sc_app.widget.order;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.order.detail.OrderDepositBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SimpleDecoration;

import java.util.List;

public class OrderInspectionDepositList extends RecyclerView {

    public OrderInspectionDepositList(Context context) {
        this(context, null);
    }

    public OrderInspectionDepositList(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OrderInspectionDepositList(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOverScrollMode(OVER_SCROLL_NEVER);
        setLayoutManager(new LinearLayoutManager(context));
        addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(5)));
    }

    public void setData(List<OrderDepositBean> list) {
        if (list == null || list.size() == 0) {
            setAdapter(null);
        } else {
            setAdapter(new OrderInspectionDepositAdapter(list));
        }
    }

    class OrderInspectionDepositAdapter extends BaseQuickAdapter<OrderDepositBean, BaseViewHolder> {

        OrderInspectionDepositAdapter(@Nullable List<OrderDepositBean> data) {
            super(R.layout.item_order_inspection_deposit, data);
            if (!CommonUtils.isEmpty(data)) {
                for (OrderDepositBean bean : data) {
                    bean.setRawProductNum(bean.getProductNum());
                }
            }
        }

        private void changeNum(int position, double value) {
            if (position < 0 || position >= getItemCount()) return;
            getData().get(position).setProductNum(value);
        }

        @Override
        protected void convert(BaseViewHolder helper, OrderDepositBean item) {
            helper.setText(R.id.oid_name_spec, TextUtils.isEmpty(item.getProductSpec()) ?
                    item.getProductName() : String.format("%s（%s）", item.getProductName(), item.getProductSpec()));
            helper.setText(R.id.oid_edit_num, CommonUtils.formatNumber(item.getProductNum()));
            helper.setText(R.id.oid_unit, item.getSaleUnitName());
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder holder = super.onCreateDefViewHolder(parent, viewType);
            ((EditText) holder.getView(R.id.oid_edit_num)).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    changeNum(holder.getAdapterPosition(), Double.parseDouble(TextUtils.isEmpty(s.toString()) ? "0" : s.toString()));
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            return holder;
        }
    }
}
