package com.hll_sc_app.app.inquiry;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.inquiry.detail.InquiryDetailActivity;
import com.hll_sc_app.bean.inquiry.InquiryBean;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/18
 */

class InquiryAdapter extends BaseQuickAdapter<InquiryBean, BaseViewHolder> {
    public InquiryAdapter() {
        super(R.layout.item_inquiry);
        setOnItemClickListener((adapter, view, position) -> InquiryDetailActivity.start(getItem(position)));
    }

    @Override
    protected void convert(BaseViewHolder helper, InquiryBean item) {
        helper.setText(R.id.ii_status, getStatusFlag(item.getEnquiryStatus()))
                .setTextColor(R.id.ii_status, ContextCompat.getColor(helper.itemView.getContext(),
                        getStatusColor(item.getEnquiryStatus())))
                .setText(R.id.ii_name, item.getPurchaserName())
                .setText(R.id.ii_num, String.format("品项：%s个", item.getEnquiryNum()))
                .setText(R.id.ii_enquiryShopNum, String.format("门店：%s个", item.getEnquiryShopNum()))
                .setText(R.id.ii_time, String.format("截至：%s", DateUtil.getReadableTime(item.getEnquiryEndTime(),
                        Constants.SLASH_YYYY_MM_DD)));
    }

    private String getStatusFlag(int status) {
        switch (status) {
            case 1:
                return "待报价";
            case 2:
                return "已报价";
            case 3:
                return "已失效";
            default:
                return "";
        }
    }

    private int getStatusColor(int status) {
        switch (status) {
            case 1:
                return R.color.color_f5a623;
            case 2:
                return R.color.color_95de64;
            case 3:
                return R.color.color_999999;
            default:
                return android.R.color.transparent;
        }
    }
}
