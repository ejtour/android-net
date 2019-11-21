package com.hll_sc_app.app.order;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.order.common.OrderHelper;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.Collection;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/5
 */

public class OrderManageAdapter extends BaseQuickAdapter<OrderResp, BaseViewHolder> {
    private int mSelectableNum;
    private String mGroupID;
    private boolean mCanCheck;

    OrderManageAdapter() {
        super(R.layout.item_order_manage);
        mGroupID = GreenDaoUtils.getUser().getGroupID();
    }

    void setCanCheck() {
        mCanCheck = true;
        notifyDataSetChanged();
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = super.onCreateDefViewHolder(parent, viewType);
        holder.addOnClickListener(R.id.iom_check_box)
                .setGone(R.id.iom_check_box, mCanCheck)
                .setGone(R.id.iom_image_tag, false);
        return holder;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderResp item) {
        ((GlideImageView) helper.getView(R.id.iom_image)).setImageURL(item.getImgUrl());
        View view = helper.getView(R.id.iom_check_box);
        view.setEnabled(item.isCanSelect(mGroupID));
        view.setSelected(item.isSelected());
        String name = TextUtils.isEmpty(item.getStallName()) ? "" :(item.getStallName()+" - ") + item.getShopName();
        helper.setText(R.id.iom_name, name)
                .setText(R.id.iom_money, "¥" + CommonUtils.formatMoney(item.getTotalAmount()))
                .setText(R.id.iom_purchase_name, "采购商：" + item.getPurchaserName())
                .setText(R.id.iom_order_no, "订单号：" + item.getSubBillNo())
                .setText(R.id.iom_extra_info, OrderHelper.handleExtraInfo(item))
                .setGone(R.id.iom_divider, helper.getAdapterPosition() != getItemCount() - 1)
                .setGone(R.id.iom_self_lift_tag, item.getDeliverType() == 2)
                .setGone(R.id.iom_warehouse_tag, item.getShipperType() > 0)
                .setGone(R.id.iom_makeup_tag, item.getIsSupplement() == 1)
                .setGone(R.id.iom_next_day_tag, item.getNextDayDelivery() == 1);
    }

    private int getItemPosition(OrderResp item) {
        return item != null && mData != null && !mData.isEmpty() ? mData.indexOf(item) : -1;
    }

    void replaceData(OrderResp oldData, OrderResp newData) {
        if (oldData == null || newData == null) {
            return;
        }
        newData.setSelected(oldData.isSelected());
        setData(getItemPosition(oldData), newData);
    }

    void removeData(OrderResp data) {
        remove(getItemPosition(data));
    }

    @Override
    public void setNewData(@Nullable List<OrderResp> data) {
        preProcess(data);
        super.setNewData(data);
        mSelectableNum = 0;
        updateNum(data);
    }

    private void preProcess(@Nullable List<OrderResp> data) {
        if (!CommonUtils.isEmpty(mData) && !CommonUtils.isEmpty(data)) {
            for (OrderResp resp : data) {
                for (OrderResp orderResp : mData) {
                    if (resp.getSubBillNo().equals(orderResp.getSubBillNo())) {
                        resp.setSelected(orderResp.isSelected() && resp.isCanSelect(mGroupID));
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void addData(@NonNull Collection<? extends OrderResp> newData) {
        super.addData(newData);
        updateNum(newData);
    }

    private void updateNum(Collection<? extends OrderResp> newData) {
        if (!CommonUtils.isEmpty(newData)) {
            for (OrderResp resp : newData) {
                if (resp.isCanSelect(mGroupID))
                    mSelectableNum++;
            }
        }
    }

    int getSelectableNum() {
        return mSelectableNum;
    }
}
