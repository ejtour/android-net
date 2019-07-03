package com.hll_sc_app.app.order.transfer;

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
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.bean.order.TransferBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.ColorStr;
import com.hll_sc_app.utils.Constants;

import java.util.Collection;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/3
 */

public class OrderTransferAdapter extends BaseQuickAdapter<TransferBean, BaseViewHolder> {
    private int mSelectableNum;

    OrderTransferAdapter() {
        super(R.layout.item_order_transfer);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = super.onCreateDefViewHolder(parent, viewType);
        holder.addOnClickListener(R.id.iot_check_box);
        return holder;
    }

    @Override
    protected void convert(BaseViewHolder helper, TransferBean item) {
        ((GlideImageView) helper.getView(R.id.iot_image)).setImageURL("");
        View view = helper.getView(R.id.iot_check_box);
        view.setEnabled(item.isCanSelect());
        view.setSelected(item.isSelected());
        boolean showTag = item.getHomologous() == 0 && (item.getStatus() == 1 || item.getStatus() == 3);
        helper.setText(R.id.iot_name, item.getAllotName())
                .setText(R.id.iot_money, "¥" + CommonUtils.formatMoney(item.getTotalPrice()))
                .setText(R.id.iot_purchase_name, "采购商：" + item.getGroupName())
                .setText(R.id.iot_order_no, "订单号：" + item.getPurchaseBillNo())
                .setText(R.id.iot_extra_info, processExtraInfo(item.getBillExecuteTime()))
                .setGone(R.id.iot_divider, helper.getAdapterPosition() != getItemCount() - 1)
                .setGone(R.id.iot_tag, showTag)
                .setText(R.id.iot_tag, showTag && item.getStatus() == 1 ? "含未关联商品" : item.getFailReason());
    }

    private SpannableString processExtraInfo(String extra) {
        String formatTime = CalendarUtils.getDateFormatString(extra,
                Constants.FORMAT_YYYY_MM_DD_HH_MM,
                Constants.FORMAT_YYYY_MM_DD_HH_MM_DASH);
        String source = "要求：" + formatTime + "送达";
        SpannableString spannableString = new SpannableString(source);
        spannableString.setSpan(new ForegroundColorSpan(
                        Color.parseColor(ColorStr.COLOR_222222)),
                source.indexOf("：") + 1,
                source.length() - 2,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
    private int getItemPosition(TransferBean item) {
        return item != null && mData != null && !mData.isEmpty() ? mData.indexOf(item) : -1;
    }

    void removeData(TransferBean data) {
        remove(getItemPosition(data));
    }

    @Override
    public void setNewData(@Nullable List<TransferBean> data) {
        preProcess(data);
        super.setNewData(data);
        mSelectableNum = 0;
        updateNum(data);
    }

    private void preProcess(@Nullable List<TransferBean> data) {
        if (!CommonUtils.isEmpty(mData) && !CommonUtils.isEmpty(data)) {
            for (TransferBean resp : data) {
                for (TransferBean orderResp : mData) {
                    if (resp.getPurchaseBillNo().equals(orderResp.getPurchaseBillNo())) {
                        resp.setSelected(orderResp.isSelected());
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void addData(@NonNull Collection<? extends TransferBean> newData) {
        super.addData(newData);
        updateNum(newData);
    }

    private void updateNum(Collection<? extends TransferBean> newData) {
        if (!CommonUtils.isEmpty(newData)) {
            for (TransferBean resp : newData) {
                if (resp.isCanSelect()) mSelectableNum++;
            }
        }
    }

    int getSelectableNum() {
        return mSelectableNum;
    }
}
