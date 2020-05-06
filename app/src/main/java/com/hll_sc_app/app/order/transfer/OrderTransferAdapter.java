package com.hll_sc_app.app.order.transfer;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.order.transfer.TransferBean;
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
        super(R.layout.item_order_manage);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = super.onCreateDefViewHolder(parent, viewType);
        holder.addOnClickListener(R.id.iom_check_box)
                .setGone(R.id.iom_check_box, true)
                .setVisible(R.id.iom_image, false);
        return holder;
    }

    @Override
    protected void convert(BaseViewHolder helper, TransferBean item) {
        View view = helper.getView(R.id.iom_check_box);
        view.setEnabled(item.isCanSelect());
        view.setSelected(item.isSelected());
        boolean showTag = item.getHomologous() == 0 && (item.getStatus() == 1 || item.getStatus() == 3);
        SpannableString money = new SpannableString("¥ " + CommonUtils.formatMoney(item.getTotalPrice()));
        money.setSpan(new RelativeSizeSpan(0.75f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        helper.setText(R.id.iom_name, item.getAllotName())
                .setText(R.id.iom_money, money)
                .setText(R.id.iom_purchaser_name, "集团：" + item.getGroupName())
                .setText(R.id.iom_order_no, item.getPurchaseBillNo())
                .setText(R.id.iom_extra_info, processExtraInfo(item.getBillExecuteTime()))
                .setGone(R.id.iom_divider, helper.getAdapterPosition() != getItemCount() - 1)
                .setGone(R.id.iom_warn, showTag)
                .setText(R.id.iom_warn, showTag && item.getStatus() == 1 ? "含未关联商品" : item.getFailReason());
    }

    private SpannableString processExtraInfo(String extra) {
        String formatTime = CalendarUtils.getDateFormatString(extra,
                Constants.UNSIGNED_YYYY_MM_DD_HH_MM,
                Constants.SIGNED_YYYY_MM_DD);
        String source = "要求 " + formatTime + " 送达";
        SpannableString spannableString = new SpannableString(source);
        spannableString.setSpan(new ForegroundColorSpan(
                        Color.parseColor(ColorStr.COLOR_222222)),
                3, source.length() - 2,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    private int getItemPosition(TransferBean item) {
        return item != null && mData != null && !mData.isEmpty() ? mData.indexOf(item) : -1;
    }

    void replaceData(TransferBean oldData, TransferBean newData) {
        if (oldData == null || newData == null) {
            return;
        }
        newData.setSelected(oldData.isSelected());
        setData(getItemPosition(oldData), newData);
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
