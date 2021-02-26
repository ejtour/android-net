package com.hll_sc_app.app.analysis.purchaser;

import android.graphics.Color;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.operationanalysis.AnalysisBean;
import com.hll_sc_app.utils.ColorStr;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/15
 */

public class PurchaserAnalysisAdapter extends BaseQuickAdapter<AnalysisBean, BaseViewHolder> {
    private int mTimeType;

    PurchaserAnalysisAdapter() {
        super(R.layout.item_purchaser_analysis);
    }

    @Override
    protected void convert(BaseViewHolder helper, AnalysisBean item) {
        helper.itemView.setBackgroundColor(mData.indexOf(item) % 2 == 0 ? Color.parseColor(ColorStr.COLOR_F9F9F9) : Color.WHITE);
        helper.setText(R.id.ipa_date, item.getDateRange(mTimeType))
                .setText(R.id.ipa_group_num, String.valueOf(item.getCoopGroupNum()))
                .setText(R.id.ipa_active_group_num, String.valueOf(item.getCoopActiveGroupNum()))
                .setText(R.id.ipa_add_group_num, String.valueOf(item.getCoopIncrGroupNum()))
                .setText(R.id.ipa_shop_num, String.valueOf(item.getCoopShopNum()))
                .setText(R.id.ipa_active_shop_num, String.valueOf(item.getCoopActiveShopNum()))
                .setText(R.id.ipa_add_shop_num, String.valueOf(item.getCoopIncrShopNum()));
    }

    public void setNewData(@Nullable List<AnalysisBean> data, int timeType) {
        mTimeType = timeType;
        super.setNewData(data);
    }
}
