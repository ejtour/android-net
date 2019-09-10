package com.hll_sc_app.app.complainmanage.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.complain.ComplainListResp;
import com.hll_sc_app.citymall.util.CalendarUtils;

import java.util.List;

public class ComplainListApdater extends BaseQuickAdapter<ComplainListResp.ComplainListBean, BaseViewHolder> {
    public ComplainListApdater(@Nullable List<ComplainListResp.ComplainListBean> data) {
        super(R.layout.list_item_comlain_manage_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ComplainListResp.ComplainListBean item) {
        helper.setText(R.id.txt_shop_name, item.getPurchaserShopName())
                .setText(R.id.txt_group_name, item.getPurchaserName())
                .setText(R.id.txt_status, getStatus(item.getStatus()))
                .setText(R.id.txt_time, CalendarUtils.getDateFormatString(item.getCreateTime(), "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss"))
                .setText(R.id.txt_complain_type, "投诉类型:" + item.getTypeName())
                .setText(R.id.txt_complain_reason, "投诉原因:" + item.getReasonName());

        GlideImageView glideImageView = helper.getView(R.id.glide_img);
        glideImageView.setImageURL(item.getGroupLogoUrl());
        /*退款tip*/
        TextView mTxtTip = helper.getView(R.id.txt_tip);
        mTxtTip.setVisibility(item.getSourceBusiness() == 2 ? View.VISIBLE : View.GONE);
        /*平台介入tip*/
        TextView mTxtInject = helper.getView(R.id.txt_platform_inject);
        mTxtInject.setVisibility(item.getOperationIntervention() > 0 ? View.VISIBLE : View.GONE);


    }

    private String getStatus(int status) {
        switch (status) {
            case 0:
                return "未处理";
            case 1:
                return "已回复";
            case 2:
                return "已结束";
            case 3:
                return "已撤销";
            default:
                return "";
        }
    }
}
