package com.hll_sc_app.app.report.customerreceive;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
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
        setOnItemClickListener((adapter, view, position) -> {
            if (mBean == null) {
                CustomerReceiveActivity.start(getItem(position));
            } else {
                RouterUtil.goToActivity(RouterConfig.ACTIVITY_QUERY_CUSTOM_RECEIVE);
            }
        });
    }

    @Override
    protected void convert(BaseViewHolder helper, ReceiveCustomerBean item) {
        helper.setText(R.id.rcr_name, mBean == null ? item.getPurchaserName() : item.getShopName())
                .setText(R.id.rcr_in_amount, String.format("进货：¥%s", CommonUtils.formatMoney(item.getPuchaseAmount())))
                .setText(R.id.rcr_out_amount, String.format("退货：¥%s", CommonUtils.formatMoney(item.getReturnsAmount())));
    }
}
