package com.hll_sc_app.app.analysis.toptean;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.operationanalysis.TopTenBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.ColorStr;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/15
 */

public class TopTenAdapter extends BaseQuickAdapter<TopTenBean, BaseViewHolder> {
    TopTenAdapter() {
        super(R.layout.item_shop_top_ten);
    }

    @Override
    protected void convert(BaseViewHolder helper, TopTenBean item) {
        helper.itemView.setBackgroundColor(mData.indexOf(item) % 2 == 0 ? Color.parseColor(ColorStr.COLOR_F9F9F9) : Color.WHITE);
        helper.setText(R.id.sst_id, item.getGroupID())
                .setText(R.id.sst_group_name, item.getGroupName())
                .setText(R.id.sst_shop_name, item.getShopName())
                .setText(R.id.sst_order_num, String.valueOf(item.getValidOrderNum()))
                .setText(R.id.sst_amount, String.format("¥%s", CommonUtils.formatMoney(item.getValidTradeAmount())));
    }
}
