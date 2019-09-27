package com.hll_sc_app.app.deliveryroute;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.other.RouteBean;
import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/27
 */

public class DeliveryRouteAdapter extends BaseQuickAdapter<RouteBean, BaseViewHolder> {
    public DeliveryRouteAdapter() {
        super(R.layout.item_delivery_route);
    }

    @Override
    protected void convert(BaseViewHolder helper, RouteBean item) {
        helper.setText(R.id.idr_name, String.format("%s（%s）", item.getLineName(), item.getLineCode()))
                .setText(R.id.idr_shop_num, "配送门店：" + CommonUtils.formatNum(item.getDemandNum()))
                .setText(R.id.idr_driver, "司机：" + item.getDriverName())
                .setText(R.id.idr_plate_number, "车牌：" + item.getPlateNumber())
                .setText(R.id.idr_done, "已完成：" + CommonUtils.formatNum(item.getCompletedBillNum()))
                .setText(R.id.idr_done, "未配送：" + CommonUtils.formatNum(item.getNoDeliveryBillNum()))
                .setText(R.id.idr_done, "准时：" + CommonUtils.formatNum(item.getOnTimeBillNum()))
                .setText(R.id.idr_done, "延迟：" + CommonUtils.formatNum(item.getDelayBillNum()));
    }
}
