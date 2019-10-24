package com.hll_sc_app.app.goodsdemand;

import android.app.Activity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goodsdemand.detail.GoodsDemandDetailActivity;
import com.hll_sc_app.bean.goodsdemand.GoodsDemandBean;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/23
 */

public class GoodsDemandAdapter extends BaseQuickAdapter<GoodsDemandBean, BaseViewHolder> {
    GoodsDemandAdapter() {
        super(R.layout.item_goods_demand);
        setOnItemClickListener((adapter, view, position) -> {
            GoodsDemandBean item = getItem(position);
            if (item != null) GoodsDemandDetailActivity.start(item);
        });
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsDemandBean item) {
        helper.setText(R.id.igd_goods_name, item.getProductName())
                .setText(R.id.igd_group_name, item.getPurchaserName())
                .setText(R.id.igd_submitter, String.format("提报人：%s / %s", item.getCreateBy(), item.getSupplyPhone()))
                .setText(R.id.igd_time, DateUtil.getReadableTime(item.getCreateTime(), Constants.SLASH_YYYY_MM_DD));
    }
}
