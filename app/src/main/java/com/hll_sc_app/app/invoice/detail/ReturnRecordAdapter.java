package com.hll_sc_app.app.invoice.detail;

import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.invoice.ReturnRecordBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.LogUtil;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/16
 */

public class ReturnRecordAdapter extends BaseQuickAdapter<ReturnRecordBean, BaseViewHolder> {
    ReturnRecordAdapter() {
        super(R.layout.item_invoice_return_record);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        helper.addOnClickListener(R.id.irr_edit)
                .addOnClickListener(R.id.irr_confirm);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, ReturnRecordBean item) {
        helper.setText(R.id.irr_type, getReturnType(item.getReturnPayType()))
                .setText(R.id.irr_time, String.format("回款时间：%s", DateUtil.getReadableTime(item.getReturnDate(), Constants.SLASH_YYYY_MM_DD)))
                .setText(R.id.irr_money, String.format("¥%s", CommonUtils.formatMoney(item.getReturnMoney())))
                .setGone(R.id.irr_edit, item.getSettlementStatus() == 1)
                .setGone(R.id.irr_line, item.getSettlementStatus() == 1)
                .setGone(R.id.irr_confirm, item.getSettlementStatus() == 1)
                .setGone(R.id.irr_done, item.getSettlementStatus() == 2);
    }

    private String getReturnType(int type) {
        switch (type) {
            case 1:
                return "现金";
            case 2:
                return "银行转账";
            case 3:
                return "支票";
            default:
                return "其他";
        }
    }
}
