package com.hll_sc_app.app.report.credit;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.report.credit.CreditBean;
import com.hll_sc_app.bean.report.credit.CreditItemBean;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/18
 */

public class CustomerCreditAdapter extends BaseQuickAdapter<CreditBean, BaseViewHolder> {
    CustomerCreditAdapter() {
        super(R.layout.item_report_customer_credit);
    }

    @Override
    protected void convert(BaseViewHolder helper, CreditBean item) {
        helper.setText(R.id.rcc_shop, item.getShopName())
                .setText(R.id.rcc_group, item.getPurchaserName())
                .setText(R.id.rcc_sales_amount, CommonUtils.formatMoney(item.getReceiveAmount()))
                .setText(R.id.rcc_return_amount, CommonUtils.formatMoney(item.getPayAmount()))
                .setText(R.id.rcc_no_return_amount, CommonUtils.formatMoney(item.getUnPayAmount()));
        ((GlideImageView) helper.getView(R.id.rcc_image)).setImageURL(item.getImagePath());
        List<CreditItemBean> list = item.getList();
        ((RecyclerView) helper.getView(R.id.rcc_list_view)).setAdapter(new CustomerCreditItemAdapter(list));
    }

    static class CustomerCreditItemAdapter extends BaseQuickAdapter<CreditItemBean, BaseViewHolder> {

        CustomerCreditItemAdapter(List<CreditItemBean> data) {
            super(R.layout.item_report_customer_credit_view, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CreditItemBean item) {
            helper.itemView.setBackgroundResource(helper.getAdapterPosition() % 2 == 0 ? android.R.color.white : R.color.color_fafafa);
            helper.setText(R.id.ccv_month, String.format("%s月", mData.indexOf(item) + 1))
                    .setText(R.id.ccv_receive, item.getReceiveAmount())
                    .setText(R.id.ccv_pay, item.getPayAmount())
                    .setText(R.id.ccv_un_pay, item.getUnPayAmount());
        }
    }
}
