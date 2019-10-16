package com.hll_sc_app.app.analysis.order;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.operationanalysis.AnalysisBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.ColorStr;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/15
 */

public class OrderAnalysisAdapter extends BaseQuickAdapter<AnalysisBean, BaseViewHolder> {
    private int mTimeType;

    OrderAnalysisAdapter() {
        super(R.layout.item_order_analysis);
    }

    @Override
    protected void convert(BaseViewHolder helper, AnalysisBean item) {
        helper.itemView.setBackgroundColor(mData.indexOf(item) % 2 == 0 ? Color.parseColor(ColorStr.COLOR_F9F9F9) : Color.WHITE);
        helper.setText(R.id.ioa_date, item.getDateRange(mTimeType))
                .setText(R.id.ioa_shop_num, String.valueOf(item.getShopNum()))
                .setText(R.id.ioa_price, String.format("¥%s", CommonUtils.formatMoney(item.getAverageShopTradeAmount())))
                .setText(R.id.ioa_order, String.valueOf(item.getValidOrderNum()))
                .setText(R.id.ioa_ave_price, String.format("¥%s", CommonUtils.formatMoney(item.getAverageTradeAmount())));
    }

    public void setNewData(@Nullable List<AnalysisBean> data, int timeType) {
        mTimeType = timeType;
        super.setNewData(data);
    }
}
