package com.hll_sc_app.app.bill.list;

import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.bill.BillBean;
import com.hll_sc_app.bean.bill.BillStatus;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/7
 */

public class BillListAdapter extends BaseQuickAdapter<BillBean, BaseViewHolder> {
    private static final String[] WEEK_ARRAY = {"每周日", "每周一", "每周二", "每周三", "每周四", "每周五", "每周六"};
    private final CompoundButton.OnCheckedChangeListener mListener;
    private final boolean mCrm;
    private boolean mIsBatch;

    BillListAdapter(CompoundButton.OnCheckedChangeListener listener) {
        super(R.layout.item_bill_list);
        mListener = listener;
        mCrm = UserConfig.crm();
    }

    @Override
    public void setNewData(@Nullable List<BillBean> data) {
        preProcess(data);
        super.setNewData(data);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        helper.addOnClickListener(R.id.ibl_confirm)
                .addOnClickListener(R.id.ibl_view_detail)
                .setOnCheckedChangeListener(R.id.ibl_check_box, mListener);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, BillBean item) {
        StringBuilder builder = new StringBuilder();
        if (item.getIsConfirm() == 1) builder.append("未确认/");
        else if (item.getIsConfirm() == 2) builder.append("已确认/");
        if (item.getSettlementStatus() == BillStatus.NOT_SETTLE) builder.append("未结算");
        else if (item.getSettlementStatus() == BillStatus.SETTLED) builder.append("已结算");
        else builder.append("部分结算");
        String status = builder.toString();

        builder.delete(0, status.length());
        String flag = item.getAccountDayFlag();
        String payFlag = flag.substring(0, 1);
        int payDate = Integer.parseInt(flag.substring(2));
        if ("1".equals(payFlag)) builder.append("周结，").append(WEEK_ARRAY[payDate]);
        else if ("2".equals(payFlag)) builder.append("月结，每月").append(payDate).append("号");
        helper.getView(R.id.ibl_check_box).setTag(item);
        ((GlideImageView) helper.setText(R.id.ibl_shop_name, item.getShopName())
                .setText(R.id.ibl_group_name, item.getPurchaserName())
                .setText(R.id.ibl_time, DateUtil.getReadableTime(item.getBillCreateTime(), Constants.SLASH_YYYY_MM_DD))
                .setText(R.id.ibl_status, status)
                .setText(R.id.ibl_bill_date, builder)
                .setText(R.id.ibl_bill_num, CommonUtils.formatNumber(item.getBillNum()))
                .setText(R.id.ibl_bill_amount, String.format("¥%s", CommonUtils.formatMoney(item.getTotalAmount())))
                .setGone(R.id.ibl_confirm, !mCrm && !mIsBatch && item.getSettlementStatus() != BillStatus.SETTLED)
                .setGone(R.id.ibl_warehouse_tag, item.getBillStatementFlag() == 1)
                .setGone(R.id.ibl_check_box, mIsBatch)
                .setGone(R.id.ibl_view_detail, !mIsBatch)
                .setChecked(R.id.ibl_check_box, item.isSelected())
                .setGone(R.id.txt_bill_type, !mIsBatch && item.getBillStatementFlag() == 1)
                .setText(R.id.txt_bill_type, String.format("%s/%s", "代仓账单", item.getPayee() == 0 ? "代仓代收款" : "货主收款"))
                .getView(R.id.ibl_icon)).setImageURL(item.getGroupLogoUrl());
    }

    private void preProcess(@Nullable List<BillBean> data) {
        if (!CommonUtils.isEmpty(mData) && !CommonUtils.isEmpty(data)) {
            for (BillBean resp : data) {
                for (BillBean BillBean : mData) {
                    if (resp.getId().equals(BillBean.getId())) {
                        resp.setSelected(BillBean.isSelected());
                        break;
                    }
                }
            }
        }
    }

    void setBatch(boolean isBatch) {
        mIsBatch = isBatch;
        notifyDataSetChanged();
    }

    void removeData(BillBean data) {
        remove(getItemPosition(data));
    }

    private int getItemPosition(BillBean item) {
        return item != null && mData != null && !mData.isEmpty() ? mData.indexOf(item) : -1;
    }
}
