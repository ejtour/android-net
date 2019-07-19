package com.hll_sc_app.app.aftersales.goodsoperation;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.aftersales.AfterSalesActionReq;
import com.hll_sc_app.bean.aftersales.AfterSalesDetailsBean;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/15
 */

public class GoodsOperationAdapter extends BaseQuickAdapter<AfterSalesDetailsBean, BaseViewHolder> {
    private boolean mDecimal;
    private OnNumChangedListener mListener;

    interface OnNumChangedListener {
        void onNumChanged();
    }

    GoodsOperationAdapter(@Nullable List<AfterSalesDetailsBean> data, boolean decimal, OnNumChangedListener listener) {
        super(R.layout.item_after_sales_goods_operation, data);
        mDecimal = decimal;
        mListener = listener;
    }

    /**
     * 获取请求参数
     */
    List<AfterSalesActionReq.ActionBean> getReqList(boolean driver) {
        List<AfterSalesActionReq.ActionBean> list = new ArrayList<>();
        for (AfterSalesDetailsBean bean : getData()) {
            AfterSalesActionReq.ActionBean item = new AfterSalesActionReq.ActionBean();
            if (driver) {
                item.setDeliveryNum(bean.getRefundNum());
                item.setDeliveryPrice(bean.getProductPrice());
            } else {
                item.setInNum(bean.getRefundNum());
                item.setInPrice(bean.getProductPrice());
            }
            item.setRefundBillDetailID(bean.getId());
            list.add(item);
        }
        return list;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        EditText editText = helper.getView(R.id.sgo_edit);
        editText.setInputType(mDecimal ? InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER
                : InputType.TYPE_CLASS_NUMBER);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                AfterSalesDetailsBean bean = getItem(helper.getAdapterPosition());
                if (bean == null) return;
                if (mDecimal) {
                    if (s.toString().startsWith("."))
                        s.insert(0, "0");
                    if (!CommonUtils.checkMoenyNum(s.toString()) && s.length() > 1) {
                        s.delete(s.length() - 1, s.length());
                    }
                }
                bean.setInspectionNum(Double.parseDouble(TextUtils.isEmpty(s.toString()) ? "0" : s.toString()));
                if (mListener != null) mListener.onNumChanged();
            }
        });
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, AfterSalesDetailsBean item) {
        GlideImageView imageView = helper.getView(R.id.sgo_image);
        imageView.setImageURL(item.getImgUrl()); // 商品图
        helper.setText(R.id.sgo_name, item.getProductName()) // 商品名
                .setText(R.id.sgo_spec, item.getProductSpec()) // 商品规格
                .setText(R.id.sgo_order, "订货： " + CommonUtils.formatNum(item.getProductNum()) + item.getSaleUnitName()) // 订货数
                .setText(R.id.sgo_deliver, "发货： " + CommonUtils.formatNum(item.getAdjustmentNum()) + item.getAdjustmentUnit()) // 发货数
                .setText(R.id.sgo_unit, item.getRefundUnit()) // 单位
                .setText(R.id.sgo_edit, CommonUtils.formatNum(item.getRefundNum())); // 数量
    }
}
