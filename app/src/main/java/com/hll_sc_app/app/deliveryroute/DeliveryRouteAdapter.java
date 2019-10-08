package com.hll_sc_app.app.deliveryroute;

import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.other.RouteBean;
import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/27
 */

public class DeliveryRouteAdapter extends BaseQuickAdapter<RouteBean, BaseViewHolder> {
    DeliveryRouteAdapter() {
        super(R.layout.item_delivery_route);
        setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getTag() != null)
                UIUtils.callPhone(view.getContext(), view.getTag().toString());
        });
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        helper.addOnClickListener(R.id.idr_driver);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, RouteBean item) {
        helper.setText(R.id.idr_name, String.format("%s（%s）", item.getLineName(), item.getLineCode()))
                .setText(R.id.idr_shop_num, String.format("配送门店：%s", CommonUtils.formatNum(item.getDemandNum())))
                .setText(R.id.idr_driver, String.format("司机：%s", item.getDriverName()))
                .setTag(R.id.idr_driver, item.getMobilePhone())
                .setText(R.id.idr_plate_number, String.format("车牌：%s", item.getPlateNumber()))
                .setText(R.id.idr_done, String.format("已完成：%s单", CommonUtils.formatNum(item.getCompletedBillNum())))
                .setText(R.id.idr_no_delivery, String.format("未配送：%s单", CommonUtils.formatNum(item.getNoDeliveryBillNum())))
                .setText(R.id.idr_on_time, String.format("准时：%s单", CommonUtils.formatNum(item.getOnTimeBillNum())))
                .setText(R.id.idr_delay, String.format("延迟：%s单", CommonUtils.formatNum(item.getDelayBillNum())));
    }
}
