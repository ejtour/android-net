package com.hll_sc_app.app.order.place.select;

import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.order.place.ProductBean;
import com.hll_sc_app.bean.order.place.ProductSpecBean;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/17
 */

public class SelectGoodsSpecAdapter extends BaseQuickAdapter<ProductSpecBean, BaseViewHolder> {
    private final View.OnFocusChangeListener mListener;
    private ProductBean mBean;

    SelectGoodsSpecAdapter(View.OnFocusChangeListener listener) {
        super(R.layout.item_order_select_goods_spec);
        mListener = listener;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        helper.addOnClickListener(R.id.sgs_add_btn)
                .addOnClickListener(R.id.sgs_sub_btn);
        EditText edit = helper.getView(R.id.sgs_edit);
        edit.setOnFocusChangeListener(mListener);
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ProductSpecBean bean = (ProductSpecBean) edit.getTag();
                if (bean.getIsDecimalBuy() == 1) { // 允许输入小数
                    if (!CommonUtils.checkDotNum(s.toString()) && s.length() - 1 >= 0) {
                        s.delete(s.length() - 1, s.length());
                    }
                } else if (!CommonUtils.checkIntegerNum(s.toString()) && s.length() - 1 >= 0) {
                    s.delete(s.length() - 1, s.length());
                }
            }
        });
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductSpecBean item) {
        StringBuilder specInfo = new StringBuilder(item.getSpecContent() == null ? "" : item.getSpecContent());
        // 辅助价格
        double ration = item.getRation(); // 转换率
        double producePrice = item.getProductPrice();
        boolean showPrice = producePrice > 0;
        if (item.getAssistUnitStatus() == 1 // 启用辅助单位
                // 转换率不为0
                && ration != 0
                // 标准单位不为空
                && !TextUtils.isEmpty(item.getStandardUnitName())) {
            specInfo.append(" [每").append(item.getStandardUnitName()).append("¥")
                    .append(showPrice ? CommonUtils.formatMoney(
                            CommonUtils.divDouble(producePrice, ration).doubleValue()
                    ) : "**").append("]");
        }

        // 起购金额
        double minNum = item.getBuyMinNum();
        if (minNum != 0 && minNum != 1) {
            specInfo.append(" [").append(CommonUtils.formatNumber(minNum)).append(item.getSaleUnitName()).append("起购]");
        }

        // 库存
        if (mBean.getIsWareHourse() == 1 && item.getUsableStock() != 0) {
            specInfo.append(" | 库存：").append(CommonUtils.formatNumber(item.getUsableStock()));
        }

        // 押金
        double deposit = item.getDepositTotalPrice();
        if (deposit > 0) {
            specInfo.append(" [每").append(item.getSaleUnitName()).append("押金：¥").append(CommonUtils.formatMoney(deposit)).append("]");
        }
        CharSequence spec;
        if (!TextUtils.isEmpty(item.getSpecContent())) {
            SpannableString ss = new SpannableString(specInfo);
            ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(helper.itemView.getContext(), R.color.colorPrimary)), 0, item.getSpecContent().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spec = ss;
        } else spec = specInfo;

        String source = String.format("¥%s/%s", showPrice ? CommonUtils.formatMoney(producePrice) : "**", item.getSaleUnitName());
        SpannableString price = new SpannableString(source);
        price.setSpan(new RelativeSizeSpan(1.6f), 1, source.indexOf("/"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        String status;
        if (item.isLowStock())
            status = "商品已售罄";
        else if (-2 == producePrice)
            status = "供应商未报价";
        else status = null;

        helper.setText(R.id.sgs_spec, spec)
                .setText(R.id.sgs_unit_price, price)
                .setGone(R.id.sgs_change_group, status == null && 0 != item.getShopcartNum())
                .setGone(R.id.sgs_add_btn, status == null)
                .setGone(R.id.sgs_status, status != null)
                .setText(R.id.sgs_status, status)
                .setText(R.id.sgs_unit, item.getSaleUnitName())
                .setTag(R.id.sgs_edit, item)
                .setTag(R.id.sgs_edit, R.id.sgs_spec, this)
                .setText(R.id.sgs_edit, CommonUtils.formatNumber(item.getShopcartNum()));
    }

    public void setNewData(@Nullable List<ProductSpecBean> data, ProductBean bean) {
        mBean = bean;
        super.setNewData(data);
    }
}
