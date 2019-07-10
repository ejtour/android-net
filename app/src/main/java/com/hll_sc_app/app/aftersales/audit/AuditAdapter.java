package com.hll_sc_app.app.aftersales.audit;

import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.aftersales.common.AfterSalesHelper;
import com.hll_sc_app.bean.aftersales.AfterSalesBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.ThumbnailView;
import com.hll_sc_app.widget.aftersales.AfterSalesActionBar;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/8
 */

public class AuditAdapter extends BaseQuickAdapter<AfterSalesBean, BaseViewHolder> {

    AuditAdapter() {
        super(R.layout.item_after_sales_audit);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        // 绑定点击事件
        helper.addOnClickListener(R.id.asa_thumbnail_wrapper)
                .addOnClickListener(R.id.after_sales_actions_reject)
                .addOnClickListener(R.id.after_sales_actions_customer_service)
                .addOnClickListener(R.id.after_sales_actions_finance)
                .addOnClickListener(R.id.after_sales_actions_driver)
                .addOnClickListener(R.id.after_sales_actions_driver_cancel)
                .addOnClickListener(R.id.after_sales_actions_warehouse);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, AfterSalesBean item) {
        helper.setText(R.id.asa_supplier_name, String.format("%s(%s)", item.getPurchaserName(), item.getShopName())); // 供应商店铺名
        ((ThumbnailView) helper.getView(R.id.asa_thumbnail_wrapper)).setThumbnailListData(item.getDetailList()); // 缩略图
        StringBuilder descBuilder = new StringBuilder(AfterSalesHelper.getRefundTypeDesc(item.getRefundBillType())).append("，");
        if (item.getRefundBillStatus() == 5)
            descBuilder.append(AfterSalesHelper.getRefundInfoPrefix(item.getRefundBillType()));
        else if (item.getRefundBillStatus() == 7)
            descBuilder.append(AfterSalesHelper.getCancelRoleDes(item.getCancelRole()));
        descBuilder.append(AfterSalesHelper.getRefundStatusDesc(item.getRefundBillStatus()));
        helper.setImageResource(R.id.asa_bill_type, item.getRefundBillType() == 1 || item.getRefundBillType() == 2
                ? R.drawable.ic_after_sales_money : R.drawable.ic_after_sales_goods)
                .setText(R.id.asa_bill_status, descBuilder.toString())
                .setText(R.id.asa_apply_time, "申请时间：" + CalendarUtils.format(
                        CalendarUtils.parse(String.valueOf(item.getRefundBillCreateTime())),
                        Constants.FORMAT_YYYY_MM_DD_DASH));
        AfterSalesActionBar actionBar = helper.getView(R.id.asa_action_bar);
        actionBar.setData(item.getButtonList());
        helper.setBackgroundRes(R.id.asa_divider, actionBar.getVisibility() == View.GONE ? 0 : R.color.color_eeeeee);
    }

    private int getItemPosition(AfterSalesBean item) {
        return item != null && mData != null && !mData.isEmpty() ? mData.indexOf(item) : -1;
    }

    void replaceData(AfterSalesBean oldData, AfterSalesBean newData) {
        setData(getItemPosition(oldData), newData);
    }

    void removeData(AfterSalesBean data) {
        remove(getItemPosition(data));
    }
}
