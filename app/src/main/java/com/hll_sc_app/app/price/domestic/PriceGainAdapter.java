package com.hll_sc_app.app.price.domestic;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.price.DomesticPriceBean;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/29
 */

public class PriceGainAdapter extends BaseQuickAdapter<DomesticPriceBean, BaseViewHolder> {
    PriceGainAdapter(List<DomesticPriceBean> data) {
        super(R.layout.item_price_gain, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DomesticPriceBean item) {
        helper.itemView.setBackgroundColor(mData.indexOf(item) % 2 != 0 ? 0x80f1f3f7 : Color.WHITE);
        helper.setText(R.id.ipg_number, String.valueOf(item.getOrderNum()))
                .setText(R.id.ipg_name, item.getFarmProduceName())
                .setText(R.id.ipg_last_avg, CommonUtils.formatMoney(item.getLastWeekAveragePrice()))
                .setText(R.id.ipg_cur_avg, CommonUtils.formatMoney(item.getThisWeekAveragePrice()))
                .setText(R.id.ipg_rate, item.getRiseFallRate());
    }
}
