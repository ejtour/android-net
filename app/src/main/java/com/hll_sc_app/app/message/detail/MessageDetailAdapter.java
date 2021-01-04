package com.hll_sc_app.app.message.detail;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.message.MessageDetailBean;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/18
 */

public class MessageDetailAdapter extends BaseQuickAdapter<MessageDetailBean, BaseViewHolder> {
    MessageDetailAdapter() {
        super(R.layout.item_message_detail);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageDetailBean item) {
        boolean show = !TextUtils.equals("1003", item.getMessageTypeCode());
        GlideImageView view = helper.setGone(R.id.imd_image, show)
                .setGone(R.id.imd_status, item.getReadStatus() == 1)
                .setText(R.id.imd_title, item.getMessageTitle())
                .setText(R.id.imd_message, item.getContent())
                .setText(R.id.imd_time, item.getActionTime())
                .getView(R.id.imd_image);
        if (show) view.setImageURL(item.getImgUrl());
    }

    void markAsRead(MessageDetailBean item) {
        if (item == null) return;
        item.setReadStatus(2);
        notifyItemChanged(mData.indexOf(item));
    }
}
