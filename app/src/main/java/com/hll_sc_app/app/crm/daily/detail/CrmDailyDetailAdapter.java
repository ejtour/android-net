package com.hll_sc_app.app.crm.daily.detail;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.daily.DailyReplyBean;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/13
 */

public class CrmDailyDetailAdapter extends BaseQuickAdapter<DailyReplyBean, BaseViewHolder> {
    CrmDailyDetailAdapter() {
        super(R.layout.item_crm_daily_detail);
    }

    @Override
    protected void convert(BaseViewHolder helper, DailyReplyBean item) {
        helper.setText(R.id.cdd_name, item.getEmployeeName())
                .setText(R.id.cdd_content, item.getReply())
                .setText(R.id.cdd_time, DateUtil.getReadableTime(item.getCreateTime(), Constants.SLASH_YYYY_MM_DD_HH_MM));
    }
}
