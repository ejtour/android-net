package com.hll_sc_app.app.crm.customer.record;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.crm.customer.CustomerHelper;
import com.hll_sc_app.bean.customer.VisitRecordBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.utils.DateUtil;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/21
 */

public class VisitRecordAdapter extends BaseQuickAdapter<VisitRecordBean, BaseViewHolder> {

    public VisitRecordAdapter() {
        super(R.layout.item_crm_visit_record);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        helper.addOnClickListener(R.id.cvr_del);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, VisitRecordBean item) {
        TextView status = helper.setText(R.id.cvr_name, item.getCustomerName())
                .setText(R.id.cvr_date, String.format("拜访日期：%s", DateUtil.getReadableTime(item.getVisitTime(), CalendarUtils.FORMAT_DATE_TIME)))
                .setText(R.id.cvr_people, item.getVisitPersonnel())
                .setText(R.id.cvr_goal, String.format("拜访目的：%s", CustomerHelper.getVisitGoal(item.getVisitGoal())))
                .getView(R.id.cvr_status);
        status.setText(item.getIsActive() == 1 ? "有效" : "无效");
        status.setCompoundDrawablesWithIntrinsicBounds(item.getIsActive() == 1 ? R.drawable.ic_valid : R.drawable.ic_invalid, 0, 0, 0);
    }
}
