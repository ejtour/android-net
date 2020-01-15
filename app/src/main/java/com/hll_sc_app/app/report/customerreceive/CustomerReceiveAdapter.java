package com.hll_sc_app.app.report.customerreceive;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.report.customreceivequery.CustomReceiveQueryActivity;
import com.hll_sc_app.bean.report.customerreceive.ReceiveCustomerBean;
import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/30
 */

public class CustomerReceiveAdapter extends BaseQuickAdapter<ReceiveCustomerBean, BaseViewHolder> {

    private final ReceiveCustomerBean mBean;

    CustomerReceiveAdapter(ReceiveCustomerBean bean) {
        super(R.layout.item_report_customer_receive);
        mBean = bean;
    }

    @Override
    protected void convert(BaseViewHolder helper, ReceiveCustomerBean item) {
        helper.setText(R.id.rcr_name, mBean == null ? item.getPurchaserName() : item.getShopName())
                .setText(R.id.rcr_in_amount, String.format("进货：¥%s", getAmount(item.getPurchaseAmount())))
                .setText(R.id.rcr_out_amount, String.format("退货：¥%s", getAmount(item.getReturnsAmount())));
    }

    private String getAmount(double amount) {
        return mBean == null || mBean.isShow() ? CommonUtils.formatMoney(amount) : "- -";
    }
}
