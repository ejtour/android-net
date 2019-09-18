package com.hll_sc_app.app.order.place.select;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.SpecsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.LogUtil;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/17
 */

public class SelectGoodsSpecAdapter extends BaseQuickAdapter<SpecsBean, BaseViewHolder> {
    private final View.OnFocusChangeListener mListener;
    private GoodsBean mBean;

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
                SpecsBean bean = mData.get((int) edit.getTag());
                if ("1".equals(bean.getIsDecimalBuy())) { // 允许输入小数
                    if (!CommonUtils.checkDotNum(s.toString()) && s.length() - 1 >= 0) {
                        s.delete(s.length() - 1, s.length());
                    }
                } else if (!CommonUtils.checkIntegerNum(s.toString()) && s.length() - 1 >= 0) {
                    s.delete(s.length() - 1, s.length());
                }
                bean.setBuyQtyBackup(s.toString());
            }
        });
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, SpecsBean item) {
        LogUtil.d("vixb-ddd", "position = " + mData.indexOf(item));
        StringBuilder specInfo = new StringBuilder(item.getSpecContent());
        // 辅助价格
        double ration = CommonUtils.getDouble(item.getRation()); // 转换率
        if ("1".equals(item.getAssistUnitStatus()) // 启用辅助单位
                // 转换率不为0
                && ration != 0
                // 标准单位不为空
                && !TextUtils.isEmpty(item.getStandardUnitName())) {
            specInfo.append(" [每").append(item.getStandardUnitName()).append("¥")
                    .append(CommonUtils.formatMoney(
                            CommonUtils.divDouble(CommonUtils.getDouble(item.getProductPrice()), ration).doubleValue()
                    )).append("]");
        }

        // 起购金额
        double minNum = CommonUtils.getDouble(item.getBuyMinNum());
        if (minNum != 0 && minNum != 1) {
            specInfo.append(" [").append(CommonUtils.formatNumber(minNum)).append(item.getSaleUnitName()).append("起购]");
        }

        // 库存
        if ("1".equals(mBean.getIsWareHourse()) && CommonUtils.getDouble(item.getUsableStock()) != 0) {
            specInfo.append(" | 库存：").append(CommonUtils.formatNumber(item.getUsableStock()));
        }

        // 押金
        double deposit = CommonUtils.getDouble(item.getDepositTotalPrice());
        if (deposit > 0) {
            specInfo.append(" [每").append(item.getSaleUnitName()).append("押金：¥").append(CommonUtils.formatMoney(deposit)).append("]");
        }
        CharSequence spec;
        if (!TextUtils.isEmpty(item.getSpecContent())) {
            SpannableString ss = new SpannableString(specInfo);
            ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(helper.itemView.getContext(), R.color.colorPrimary)), 0, item.getSpecContent().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spec = ss;
        } else spec = specInfo;

        String source = String.format("¥%s/%s", CommonUtils.formatMoney(CommonUtils.getDouble(item.getProductPrice())), item.getSaleUnitName());
        SpannableString price = new SpannableString(source);
        price.setSpan(new RelativeSizeSpan(1.6f), 1, source.indexOf("/"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        String status;
        if (item.isLowStock())
            status = "商品已售罄";
        else if ("7".equals(mBean.getProductStatus()) || "5".equals(item.getSpecStatus()))
            status = "商品已下架";
        else if (-2 == CommonUtils.getDouble(item.getProductPrice()))
            status = "供应商未报价";
        else if (!mBean.isIsDeliveryRange())
            status = "非配送范围";
        else status = null;

        helper.setText(R.id.sgs_spec, spec)
                .setText(R.id.sgs_unit_price, price)
                .setGone(R.id.sgs_change_group, status == null && 0 != CommonUtils.getDouble(item.getBuyQty()))
                .setGone(R.id.sgs_add_btn, status == null)
                .setGone(R.id.sgs_status, status != null)
                .setText(R.id.sgs_status, status)
                .setText(R.id.sgs_unit, item.getSaleUnitName())
                .setTag(R.id.sgs_edit, mData.indexOf(item))
                .setText(R.id.sgs_edit, item.getBuyQty());
    }

    public void setNewData(@Nullable List<SpecsBean> data, GoodsBean bean) {
        mBean = bean;
        super.setNewData(data);
    }
}
