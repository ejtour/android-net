package com.hll_sc_app.app.complainmanage.history;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.BuildConfig;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.complain.ComplainHistoryResp;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.ViewUtils;

import java.util.List;

import static com.hll_sc_app.citymall.util.CalendarUtils.FORMAT_HH_MM_SS;

public class ComplianHistoryAdapter extends BaseQuickAdapter<ComplainHistoryResp.HistoryBean, BaseViewHolder> {

    private LinearLayout.LayoutParams layoutParams;
    private Context mContext;

    public ComplianHistoryAdapter(Context context, @Nullable List<ComplainHistoryResp.HistoryBean> data) {
        super(R.layout.list_item_complain_history, data);
        int size = ViewUtils.dip2px(context, 60);
        layoutParams = new LinearLayout.LayoutParams(size, size);
        layoutParams.rightMargin = ViewUtils.dip2px(context, 10);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ComplainHistoryResp.HistoryBean item) {
        TranslateText translateText = getTransformInfo(item);
        ((GlideImageView) helper.setGone(R.id.group_complain_info, item.getStatus() == 1 || item.getStatus() == 6)
                .setGone(R.id.group_reply, item.getStatus() == 2)
                .setText(R.id.txt_name, translateText.getTitle())
                .setText(R.id.txt_operater, item.getCreateBy())
                .setText(R.id.txt_time, CalendarUtils.getDateFormatString(item.getCreateTime(), FORMAT_HH_MM_SS, "yyyy-MM-dd HH:mm:ss"))
                .setText(R.id.txt_status, translateText.getStatus())
                .setText(R.id.txt_type, item.getTypeName())
                .setText(R.id.txt_reason, item.getReasonName())
                .setText(R.id.txt_order, item.getBillID())
                .setText(R.id.txt_explain, item.getComplaintExplain())
                .setText(R.id.txt_reply, item.getReply())
                .getView(R.id.img_url))
                .setImageURL(item.getGroupLogoUrl());

        LinearLayout mphotoContainer = helper.getView(R.id.ll_scroll_photo);
        if (!TextUtils.isEmpty(item.getImgUrls())) {
            String[] urls = item.getImgUrls().split(",");
            for (String url : urls) {
                GlideImageView glideImageView = new GlideImageView(mContext);
                glideImageView.setImageURL(url);
                glideImageView.isPreview(true);
                glideImageView.setUrls(urls);
                glideImageView.setRadius(5);
                glideImageView.setLayoutParams(layoutParams);
                mphotoContainer.addView(glideImageView);
            }
        }

    }

    private TranslateText getTransformInfo(ComplainHistoryResp.HistoryBean historyBean) {
        TranslateText translateText = new TranslateText();
        String purchaserName = historyBean.getPurchaserName();
        String supplyName = historyBean.getSupplyName();
        int source = historyBean.getSource();
        switch (historyBean.getStatus()) {
            case 1:
                translateText.setTitle(TextUtils.isEmpty(purchaserName) ? supplyName : purchaserName);
                translateText.setStatus(TextUtils.isEmpty(purchaserName) ? String.format("供应商（%s）向平台发起了投诉", supplyName)
                        : String.format("客户（%s）发起了投诉", purchaserName));
                break;
            case 2:
                if (source == 2) {
                    translateText.setTitle(supplyName);
                    translateText.setStatus(String.format("供应商（%s）回复了投诉", supplyName));
                } else {
                    translateText.setTitle(BuildConfig.ODM_NAME + "平台客服");
                    translateText.setStatus("平台客服回复了投诉");
                }
                break;
            case 3:
                translateText.setTitle(TextUtils.isEmpty(purchaserName) ? supplyName : purchaserName);
                translateText.setStatus(TextUtils.isEmpty(purchaserName) ? String.format("供应商（%s）结束了投诉", supplyName) : String.format("客户（%s）结束了投诉", purchaserName));
                break;
            case 4:
                translateText.setTitle(TextUtils.isEmpty(purchaserName) ? supplyName : purchaserName);
                translateText.setStatus(TextUtils.isEmpty(purchaserName) ? String.format("供应商（%s）撤销了投诉", supplyName) : String.format("客户（%s）撤销了投诉", purchaserName));
                break;
            case 5:
                if (source == 2) {
                    translateText.setTitle(supplyName);
                    translateText.setStatus(String.format("供应商（%s）申请了平台介入", supplyName));
                } else {
                    translateText.setTitle(purchaserName);
                    translateText.setStatus(String.format("客户（%s）申请了平台介入", purchaserName));
                }
                break;
            case 6:
                translateText.setTitle(purchaserName);
                translateText.setStatus(String.format("客户（%s）发起了继续投诉", purchaserName));
                break;
            default:
                break;
        }
        return translateText;
    }


    private class TranslateText {
        private String title;
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
