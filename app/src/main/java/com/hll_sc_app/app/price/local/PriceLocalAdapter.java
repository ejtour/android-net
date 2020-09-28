package com.hll_sc_app.app.price.local;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.price.LocalPriceBean;
import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/29
 */

public class PriceLocalAdapter extends BaseQuickAdapter<LocalPriceBean, BaseViewHolder> {
    PriceLocalAdapter() {
        super(R.layout.item_price_local);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalPriceBean item) {
        helper.itemView.setBackgroundColor(mData.indexOf(item) % 2 != 0 ? 0x80f1f3f7 : Color.WHITE);
        helper.setText(R.id.ipl_category, item.getFarmProduceName())
                .setText(R.id.ipl_market, item.getMarketName())
                .setText(R.id.ipl_max, CommonUtils.formatMoney(item.getMaxPrice()))
                .setText(R.id.ipl_min, CommonUtils.formatMoney(item.getMinPrice()))
                .setText(R.id.ipl_bulk, CommonUtils.formatMoney(item.getAveragePrice()));
    }
}
