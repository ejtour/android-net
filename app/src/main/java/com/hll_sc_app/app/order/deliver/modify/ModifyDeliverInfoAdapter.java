package com.hll_sc_app.app.order.deliver.modify;

import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.order.detail.OrderDepositBean;
import com.hll_sc_app.bean.order.detail.OrderDetailBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.order.AppendGoodsList;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/13
 */

public class ModifyDeliverInfoAdapter extends BaseQuickAdapter<OrderDetailBean, BaseViewHolder> {
    private boolean mPrice;

    ModifyDeliverInfoAdapter(@Nullable List<OrderDetailBean> data) {
        super(R.layout.item_modify_deliver_info, data);
        if (!CommonUtils.isEmpty(data))
            for (OrderDetailBean bean : data) {
                bean.setDeliverUnit(bean.getAuxiliaryUnit());
            }
    }

    void modifyPrice() {
        mPrice = true;
        notifyDataSetChanged();
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
                Utils.processMoney(s, false);
                OrderDetailBean item = getItem(holder.getAdapterPosition());
                double result = CommonUtils.getDouble(s.toString());
                if (item != null && CommonUtils.getDouble(CommonUtils.formatNumber(item.getAdjustmentNum())) != result) {
                    item.setAdjustmentNum(result);
                    holder.setText(R.id.mdi_total_price_edit, CommonUtils.formatNumber(item.getAdjustmentAmount()));
                    if (!CommonUtils.isEmpty(item.getDepositList())) {
                        for (OrderDepositBean bean : item.getDepositList()) {
                            bean.setProductNum(CommonUtils.mulDouble(bean.getDepositNum(), item.getAdjustmentNum()).doubleValue());
                            bean.setSubtotalAmount(CommonUtils.mulDouble(bean.getProductPrice(), bean.getProductNum()).doubleValue());
                        }
                        ((AppendGoodsList) holder.getView(R.id.mdi_deposit_goods)).setData(item.getDepositList());
                    }
                }
            }
        });
        EditText unitPrice = holder.getView(R.id.mdi_unit_price_edit);
        unitPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Utils.processMoney(s, false);
                OrderDetailBean item = getItem(holder.getAdapterPosition());
                double result = CommonUtils.getDouble(s.toString());
                if (item != null && CommonUtils.getDouble(CommonUtils.formatNumber(item.getAdjustmentPrice())) != result) {
                    item.setAdjustmentPrice(result);
                    holder.setText(R.id.mdi_total_price_edit, CommonUtils.formatNumber(item.getAdjustmentAmount()));
                }
            }
        });
        EditText totalPrice = holder.getView(R.id.mdi_total_price_edit);
        totalPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Utils.processMoney(s, false);
                OrderDetailBean item = getItem(holder.getAdapterPosition());
                double result = CommonUtils.getDouble(s.toString());
                if (item != null && CommonUtils.getDouble(CommonUtils.formatNumber(item.getAdjustmentAmount())) != result) {
                    item.setAdjustmentAmount(result);
                    if (item.getAdjustmentNum() != 0) {
                        item.setAdjustmentPrice(CommonUtils.divDouble(item.getAdjustmentAmount(), item.getAdjustmentNum()).doubleValue());
                        holder.setText(R.id.mdi_unit_price_edit, CommonUtils.formatNumber(item.getAdjustmentPrice()));
                    }
                }
            }
        });
        holder.addOnClickListener(R.id.mdi_modify_unit)
                .addOnClickListener(R.id.mdi_unit);
        return holder;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailBean item) {
        ((GlideImageView) helper.getView(R.id.mdi_image)).setImageURL(item.getImgUrl());
        helper.setText(R.id.mdi_name, item.getProductName())
                .setText(R.id.mdi_spec, item.getProductSpec())
                .setText(R.id.mdi_edit, CommonUtils.formatNumber(item.getAdjustmentNum()))
                .setGone(R.id.mdi_modify_unit, !TextUtils.isEmpty(item.getAuxiliaryUnit()))
                .setText(R.id.mdi_unit, item.getDeliverUnit())
                .setGone(R.id.mdi_price_group, mPrice)
                .setText(R.id.mdi_unit_price_edit, CommonUtils.formatNumber(item.getAdjustmentPrice()))
                .setText(R.id.mdi_total_price_edit, CommonUtils.formatNumber(item.getAdjustmentAmount()))
                .getView(R.id.mdi_unit).setClickable(!TextUtils.isEmpty(item.getAuxiliaryUnit()));


        SpannableString num = new SpannableString(CommonUtils.formatNum(item.getProductNum()));
        num.setSpan(new StyleSpan(Typeface.BOLD), 0, num.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        num.setSpan(new ForegroundColorSpan(ContextCompat.getColor(helper.itemView.getContext(), R.color.color_222222)),
                0, num.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        TextView orderNum = helper.getView(R.id.mdi_order_num);
        orderNum.setText("订：");
        orderNum.append(num);
        orderNum.append(item.getSaleUnitName());

        List<OrderDepositBean> depositList = item.getDepositList();
        helper.setGone(R.id.mdi_deposit_goods, !CommonUtils.isEmpty(depositList));
        ((AppendGoodsList) helper.getView(R.id.mdi_deposit_goods)).setData(depositList);

        List<GoodsBean> goodsList = item.getBundleGoodsList();
        helper.setGone(R.id.mdi_bundle_goods, !CommonUtils.isEmpty(goodsList));
        ((AppendGoodsList) helper.getView(R.id.mdi_bundle_goods)).setBundleList(goodsList);

    }
}
