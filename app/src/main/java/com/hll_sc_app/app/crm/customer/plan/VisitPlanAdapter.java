package com.hll_sc_app.app.crm.customer.plan;

import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.crm.customer.CustomerHelper;
import com.hll_sc_app.bean.customer.VisitPlanBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.utils.DateUtil;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/23
 */

public class VisitPlanAdapter extends BaseQuickAdapter<VisitPlanBean, BaseViewHolder> {
    VisitPlanAdapter() {
        super(R.layout.item_crm_visit_plan);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        helper.addOnClickListener(R.id.cvp_root)
                .addOnClickListener(R.id.cvp_del);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, VisitPlanBean item) {
        helper.setText(R.id.cvp_name, item.getCustomerName())
                .setText(R.id.cvp_date, "计划日期：" + DateUtil.getReadableTime(item.getVisitTime(), CalendarUtils.FORMAT_DATE_TIME))
                .setText(R.id.cvp_way, CustomerHelper.getVisitWay(item.getVisitWay()))
                .setText(R.id.cvp_goal, "拜访目的：" + CustomerHelper.getVisitGoal(item.getVisitGoal()));
    }

    private int getItemPosition(VisitPlanBean item) {
        return item != null && mData != null && !mData.isEmpty() ? mData.indexOf(item) : -1;
    }

    public void replaceData(VisitPlanBean oldData, VisitPlanBean newData) {
        if (oldData == null || newData == null) {
            return;
        }
        setData(getItemPosition(oldData), newData);
    }
}
