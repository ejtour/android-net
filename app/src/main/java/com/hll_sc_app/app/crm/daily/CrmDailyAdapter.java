package com.hll_sc_app.app.crm.daily;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.crm.daily.detail.CrmDailyDetailActivity;
import com.hll_sc_app.bean.daily.DailyBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.utils.DateUtil;

import java.util.Date;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/13
 */

public class CrmDailyAdapter extends BaseQuickAdapter<DailyBean, BaseViewHolder> {
    private final boolean mSend;

    public CrmDailyAdapter(boolean send) {
        super(R.layout.item_crm_daily);
        mSend = send;
        setOnItemClickListener((adapter, view, position) ->
                CrmDailyDetailActivity.start(((Activity) view.getContext()), getItem(position)));
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        helper.setGone(R.id.icd_date_first, mSend)
                .setGone(R.id.icd_date_last, mSend)
                .setGone(R.id.icd_name, !mSend);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, DailyBean item) {
        Date date = DateUtil.parse(item.getCreateTime());
        helper.setText(R.id.icd_date_first, CalendarUtils.format(date, "yyyy/MM\nEEEE"))
                .setText(R.id.icd_date_last, CalendarUtils.format(date, "dd"))
                .setText(R.id.icd_time, CalendarUtils.format(date, "HH:mm"))
                .setText(R.id.icd_name, item.getEmployeeName())
                .setText(R.id.icd_content, item.getTodayWork());
        TextView read = helper.getView(R.id.icd_read_status);
        boolean readSelected = item.getReadStatus() == 1;
        read.setText(readSelected ? "已读" : "未读");
        read.setSelected(readSelected);
        TextView review = helper.getView(R.id.icd_review_status);
        boolean reviewed = item.getReplyStatus() == 1;
        review.setText(reviewed ? String.format("%s条点评", item.getReplyNum()) : "暂无点评");
        review.setSelected(reviewed);
    }
}
