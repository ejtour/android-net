package com.hll_sc_app.app.order.statistic;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.order.statistic.OrderStatisticShopBean;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/8
 */
public class OrderShopStatisticAdapter extends BaseQuickAdapter<OrderStatisticShopBean, BaseViewHolder> {

    private String mPrefix;

    public OrderShopStatisticAdapter() {
        super(R.layout.item_order_statistic_shop);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderStatisticShopBean item) {
        helper.itemView.setBackgroundResource(helper.getAdapterPosition() % 2 == 0 ? R.drawable.bg_fafafa_radius_5_solid : android.R.color.transparent);
        String info = String.format("%s订单数：%s  |  金额：¥%s", mPrefix, CommonUtils.formatNum(item.getTotalNum()),
                CommonUtils.formatMoney(item.getTotalAmount()));
        helper.setText(R.id.oss_shop_name, item.getShopName())
                .setText(R.id.oss_info, info);
    }

    public void setNewData(@Nullable List<OrderStatisticShopBean> data, String prefix) {
        mPrefix = prefix == null ? "" : prefix;
        super.setNewData(data);
    }
}
