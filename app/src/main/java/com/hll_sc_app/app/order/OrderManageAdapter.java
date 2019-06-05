package com.hll_sc_app.app.order;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.ColorStr;
import com.hll_sc_app.utils.Constants;

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

    void setCanCheck(boolean canCheck) {
        mCanCheck = canCheck;
        notifyDataSetChanged();
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = super.onCreateDefViewHolder(parent, viewType);
        holder.addOnClickListener(R.id.iom_check_box)
                .setGone(R.id.iom_check_box, mCanCheck);
        return holder;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderResp item) {
        ((GlideImageView) helper.getView(R.id.iom_image)).setImageURL(item.getImgUrl());
        View view = helper.getView(R.id.iom_check_box);
        view.setEnabled(item.isCanSelect());
        view.setSelected(item.isSelected());
        helper.setText(R.id.iom_name, item.getPurchaserName())
                .setText(R.id.iom_money, "¥" + CommonUtils.formatMoney(item.getTotalAmount()))
                .setText(R.id.iom_purchase_name, "采购商：" + item.getPurchaserName())
                .setText(R.id.iom_order_no, "订单号：" + item.getSubBillNo())
                .setText(R.id.iom_extra_info, handleExtraInfo(item))
                .setGone(R.id.iom_divider, helper.getAdapterPosition() != getItemCount() - 1);
    }

    private CharSequence handleExtraInfo(OrderResp resp) {
        String source = null;
        switch (resp.getSubBillStatus()) {
            case 0:
            case 1:
            case 2:
                String formatTime = CalendarUtils.getDateFormatString(resp.getSubBillExecuteDate(),
                        Constants.FORMAT_YYYY_MM_DD_HH_MM,
                        Constants.FORMAT_YYYY_MM_DD_HH_MM_DASH);
                source = "要求：" + formatTime + "送达";
                SpannableString spannableString = new SpannableString(source);
                spannableString.setSpan(new ForegroundColorSpan(
                                Color.parseColor(ColorStr.COLOR_222222)),
                        source.indexOf("：") + 1,
                        source.length() - 2,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                return spannableString;
            case 3:
            case 4:
                source = getFormatTime(resp.getDeliveryTime()) + "发货";
                break;
            case 5:
                source = getFormatTime(resp.getSignTime()) + "签收";
                break;
            case 6:
                source = getCancelRole(resp.getCanceler()) + "取消";
                break;
        }
        return source;
    }

    private String getCancelRole(int canceler) {
        switch (canceler) {
            case 1:
                return "采购商";
            case 2:
                return "供应商";
            case 3:
                return "客服";
            default:
                return "";
        }
    }

    private String getFormatTime(String date) {
        return CalendarUtils.format(CalendarUtils.parse(date), "yyyy-MM-dd HH:mm:ss");
    }

    private int getItemPosition(OrderResp item) {
        return item != null && mData != null && !mData.isEmpty() ? mData.indexOf(item) : -1;
    }

    void replaceData(OrderResp oldData, OrderResp newData) {
        newData.setSelected(oldData.isSelected());
        newData.setCanSelect(oldData.isCanSelect());
        setData(getItemPosition(oldData), newData);
    }

    void removeData(OrderResp data) {
        remove(getItemPosition(data));
    }

    @Override
    public void setNewData(@Nullable List<OrderResp> data) {
        super.setNewData(data);
        mSelectableNum = 0;
        updateNum(data);
    }

    @Override
    public void addData(@NonNull Collection<? extends OrderResp> newData) {
        super.addData(newData);
        updateNum(newData);
    }

    private void updateNum(Collection<? extends OrderResp> newData) {
        if (!CommonUtils.isEmpty(newData)) {
            for (OrderResp resp : newData) {
                if (resp.getIsCheck() == 2 && mGroupID.equals(resp.getGroupID()) ||
                        resp.getIsCheck() == 1 && mGroupID.equals(resp.getAgencyID())) {
                    resp.setCanSelect(false);
                    continue;
                }
                resp.setCanSelect(true);
                mSelectableNum++;
            }
        }
    }

    int getSelectableNum() {
        return mSelectableNum;
    }
}
