package com.hll_sc_app.app.order.deliver.modify;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.order.detail.OrderDepositBean;
import com.hll_sc_app.bean.order.detail.OrderDetailBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.order.OrderDepositList;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/13
 */

public class ModifyDeliverInfoAdapter extends BaseQuickAdapter<OrderDetailBean, BaseViewHolder> {
    ModifyDeliverInfoAdapter(@Nullable List<OrderDetailBean> data) {
        super(R.layout.item_modify_deliver_info, data);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = super.onCreateDefViewHolder(parent, viewType);
        EditText editText = holder.getView(R.id.mdi_edit);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                OrderDetailBean item = getItem(holder.getAdapterPosition());
                if (item != null) {
                    if (s.toString().startsWith("."))
                        s.insert(0, "0");
                    if (!CommonUtils.checkMoenyNum(s.toString()) && s.length() > 1) {
                        s.delete(s.length() - 1, s.length());
                    }
                    item.setAdjustmentNum(TextUtils.isEmpty(s.toString()) ? 0 : Double.parseDouble(s.toString()));
                    item.setAdjustmentAmount(CommonUtils.mulDouble(item.getProductPrice(), item.getAdjustmentNum()).doubleValue());
                    if (!CommonUtils.isEmpty(item.getDepositList())) {
                        for (OrderDepositBean bean : item.getDepositList()) {
                            bean.setProductNum(CommonUtils.mulDouble(bean.getDepositNum(), item.getAdjustmentNum()).doubleValue());
                            bean.setSubtotalAmount(CommonUtils.mulDouble(bean.getProductPrice(), bean.getProductNum()).doubleValue());
                        }
                        ((OrderDepositList) holder.getView(R.id.mdi_deposit_list)).setData(item.getDepositList());
                    }
                }
            }
        });
        return holder;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailBean item) {
        ((GlideImageView) helper.getView(R.id.mdi_image)).setImageURL(item.getImgUrl());
        helper.setText(R.id.mdi_name, item.getProductName())
                .setText(R.id.mdi_spec, item.getProductSpec())
                .setText(R.id.mdi_order_num, "订货： " + CommonUtils.formatNum(item.getProductNum()) + item.getSaleUnitName())
                .setText(R.id.mdi_edit, CommonUtils.formatNum(item.getAdjustmentNum()))
                .setText(R.id.mdi_unit, item.getAdjustmentUnit());
        List<OrderDepositBean> depositList = item.getDepositList();
        helper.setGone(R.id.mdi_deposit_group, !CommonUtils.isEmpty(depositList));
        ((OrderDepositList) helper.getView(R.id.mdi_deposit_list)).setData(depositList);
    }
}
