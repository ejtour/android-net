package com.hll_sc_app.app.aftersales.audit;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.aftersales.common.AfterSalesHelper;
import com.hll_sc_app.bean.aftersales.AfterSalesBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.widget.ThumbnailView;
import com.hll_sc_app.widget.aftersales.AfterSalesActionBar;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/8
 */

public class AuditAdapter extends BaseQuickAdapter<AfterSalesBean, BaseViewHolder> {

    private boolean mIsCheckable;

    public AuditAdapter() {
        super(R.layout.item_after_sales_audit);
    }

    void setCheckable(boolean checkable) {
        mIsCheckable = checkable;
        notifyDataSetChanged();
    }

    int getSelectedCount() {
        int count = 0;
        for (AfterSalesBean bean : getData()) {
            if (bean.isSelected()) count++;
        }
        return count;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        // 绑定点击事件
        helper.addOnClickListener(R.id.asa_action_bar)
                .addOnClickListener(R.id.asa_check);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, AfterSalesBean item) {
        helper.setText(R.id.asa_supplier_name, item.getShopName()); // 供应商店铺名
        helper.setText(R.id.asa_company_name, item.getPurchaserName()); // 供应商集团名
        ((ThumbnailView) helper.getView(R.id.asa_thumbnail_wrapper)).setThumbnailListData(item.getDetailList()); // 缩略图
        StringBuilder descBuilder = new StringBuilder(AfterSalesHelper.getRefundTypeDesc(item.getRefundBillType())).append("，");
        if (item.getRefundBillStatus() == 5)
            descBuilder.append(AfterSalesHelper.getRefundTypeLabel(item.getRefundBillType()));
        else if (item.getRefundBillStatus() == 7)
            descBuilder.append(AfterSalesHelper.getCancelRoleDes(item.getCancelRole()));
        descBuilder.append(AfterSalesHelper.getRefundStatusDesc(item.getRefundBillStatus()));
        helper.setImageResource(R.id.asa_bill_type, AfterSalesHelper.getRefundBillFlag(item.getRefundBillType()))
                .setText(R.id.asa_bill_status, descBuilder.toString())
                .setGone(R.id.asa_warehouse_tag, item.getShipperID() > 0)
                .setGone(R.id.asa_shop_flag, !mIsCheckable)
                .setGone(R.id.asa_check, mIsCheckable)
                .setText(R.id.asa_apply_time, "申请时间：" + CalendarUtils.format(
                        DateUtil.parse(String.valueOf(item.getRefundBillCreateTime())),
                        Constants.SIGNED_YYYY_MM_DD));
        helper.getView(R.id.asa_check).setSelected(item.isSelected());
        AfterSalesActionBar actionBar = helper.getView(R.id.asa_action_bar);
        actionBar.setData(item.getButtonList());
        helper.setBackgroundRes(R.id.asa_divider, actionBar.getVisibility() == View.GONE ? 0 : R.color.color_eeeeee);
    }

    private int getItemPosition(AfterSalesBean item) {
        return item != null && mData != null && !mData.isEmpty() ? mData.indexOf(item) : -1;
    }

    void replaceData(AfterSalesBean oldData, AfterSalesBean newData) {
        int position = getItemPosition(oldData);
        if (position == -1 || newData == null) {
            return;
        }
        setData(position, newData);
    }

    void removeData(AfterSalesBean data) {
        int position = getItemPosition(data);
        if (position > -1) {
            remove(position);
        }
    }

    @Override
    public void setNewData(@Nullable List<AfterSalesBean> data) {
        preProcess(data);
        super.setNewData(data);
    }

    private void preProcess(@Nullable List<AfterSalesBean> data) {
        if (!CommonUtils.isEmpty(mData) && !CommonUtils.isEmpty(data)) {
            for (AfterSalesBean resp : data) {
                for (AfterSalesBean bean : mData) {
                    if (resp.getId().equals(bean.getId())) {
                        resp.setSelected(bean.isSelected());
                        break;
                    }
                }
            }
        }
    }
}
