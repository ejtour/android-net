package com.hll_sc_app.app.crm.customer.search.plan;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.customer.VisitPlanBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.utils.DateUtil;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/28
 */

public class SearchPlanAdapter extends BaseQuickAdapter<VisitPlanBean, BaseViewHolder> {

    private final String mId;

    SearchPlanAdapter(String id) {
        super(R.layout.item_crm_plan_search);
        mId = id;
    }

    @Override
    protected void convert(BaseViewHolder helper, VisitPlanBean item) {
        boolean selected = item.getId().equals(mId);
        helper.setText(R.id.cps_name, item.getCustomerName())
                .setVisible(R.id.cps_check, selected)
                .setText(R.id.cps_date, DateUtil.getReadableTime(item.getVisitTime(), CalendarUtils.FORMAT_DATE_TIME));
        helper.getView(R.id.cps_name).setSelected(selected);
        helper.getView(R.id.cps_date).setSelected(selected);
    }
}
