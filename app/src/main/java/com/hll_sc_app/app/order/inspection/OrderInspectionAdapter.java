package com.hll_sc_app.app.order.inspection;

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
import com.hll_sc_app.bean.order.inspection.OrderInspectionReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.order.OrderInspectionDepositList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/17
 */

public class OrderInspectionAdapter extends BaseQuickAdapter<OrderDetailBean, BaseViewHolder> {
    private List<OrderInspectionReq.OrderInspectionItem> mReqList;

    OrderInspectionAdapter(@Nullable List<OrderDetailBean> data) {
        super(R.layout.item_order_inspection, data);
        // 构造请求列表
        mReqList = new ArrayList<>();
        for (OrderDetailBean bean : getData()) {
            mReqList.add(OrderInspectionReq.OrderInspectionItem.copyFromDetailList(bean));
        }
    }

    /**
     * 获取请求列表
     */
    List<OrderInspectionReq.OrderInspectionItem> getReqList() {
        return mReqList;
    }

    /**
     * 更改请求列表中的操作数
     *
     * @param position 位置
     * @param value    操作数的值
     */
    private void changeNum(int position, double value) {
        if (position < 0 || position >= mReqList.size()) return;
        mReqList.get(position).setInspectionNum(value);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = super.onCreateDefViewHolder(parent, viewType);
        ((EditText) holder.getView(R.id.ioi_edit)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith("."))
                    s.insert(0, "0");
                if (!CommonUtils.checkMoenyNum(s.toString()) && s.length() > 1) {
                    s.delete(s.length() - 1, s.length());
                }
                changeNum(holder.getAdapterPosition(), Double.parseDouble(TextUtils.isEmpty(s.toString()) ? "0" : s.toString()));
            }
        });
        return holder;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailBean item) {
        helper.setText(R.id.ioi_name, item.getProductName()); // 商品名
        helper.setText(R.id.ioi_spec, item.getProductSpec()); // 商品规格
        helper.setText(R.id.ioi_order, "订货： " + CommonUtils.formatNum(item.getProductNum()) + item.getSaleUnitName()); // 订货数
        helper.setText(R.id.ioi_deliver, "发货： " + CommonUtils.formatNum(item.getAdjustmentNum()) + item.getAdjustmentUnit()); // 发货数
        helper.setText(R.id.ioi_unit, item.getInspectionUnit()); //签收单位
        helper.setText(R.id.ioi_edit, CommonUtils.formatNum(item.getInspectionNum())); // 签收数量
        GlideImageView imageView = helper.getView(R.id.ioi_image);
        imageView.setImageURL(item.getImgUrl()); // 商品图
        // 押金商品
        List<OrderDepositBean> depositList = item.getDepositList();
        helper.setGone(R.id.ioi_deposit_group, depositList != null && depositList.size() > 0);
        ((OrderInspectionDepositList) helper.getView(R.id.ioi_deposit_list)).setData(depositList);
    }
}
