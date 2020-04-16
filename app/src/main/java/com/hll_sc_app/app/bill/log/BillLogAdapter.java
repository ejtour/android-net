package com.hll_sc_app.app.bill.log;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.bill.BillLogBean;
import com.hll_sc_app.utils.DateUtil;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/9
 */

public class BillLogAdapter extends BaseQuickAdapter<BillLogBean, BaseViewHolder> {
    BillLogAdapter() {
        super(R.layout.item_bill_log);
    }

    @Override
    protected void convert(BaseViewHolder helper, BillLogBean item) {
        int position = helper.getAdapterPosition();
        helper.setText(R.id.ibl_label, item.getOperateName())
                .setText(R.id.ibl_desc, item.getOperateLog())
                .setVisible(R.id.ibl_current_tag, position == 0)
                .setGone(R.id.ibl_earlier_tag, position > 0)
                .setGone(R.id.ibl_stress, !TextUtils.isEmpty(item.getShowLog()))
                .setText(R.id.ibl_stress, item.getShowLog())
                .setText(R.id.ibl_time, DateUtil.getReadableTime(item.getCreateTime()))
                .setGone(R.id.ibl_extra, !TextUtils.isEmpty(item.getCreateBy()))
                .setGone(R.id.ibl_bottom_padding, position < mData.size() - 1)
                .setText(R.id.ibl_extra, "操作人：" + item.getCreateBy());
    }
}
