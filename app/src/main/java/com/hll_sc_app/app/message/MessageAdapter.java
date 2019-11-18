package com.hll_sc_app.app.message;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.message.MessageBean;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/18
 */

public class MessageAdapter extends BaseQuickAdapter<MessageBean, BaseViewHolder> {
    MessageAdapter() {
        super(R.layout.item_message);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean item) {
        ((GlideImageView) helper.setText(R.id.im_name, item.getName())
                .setText(R.id.im_message, item.getMessage())
                .setText(R.id.im_time, item.getTime())
                .setGone(R.id.im_status, item.getUnreadCount() > 0)
                .getView(R.id.im_image)).setImageURL(item.getImgUrl());
    }
}
