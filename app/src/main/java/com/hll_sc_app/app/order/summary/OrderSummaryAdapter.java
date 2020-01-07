package com.hll_sc_app.app.order.summary;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.order.summary.OrderSummaryWrapper;
import com.hll_sc_app.bean.order.summary.SummaryPurchaserBean;
import com.hll_sc_app.bean.order.summary.SummaryShopBean;
import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/1/7
 */

public class OrderSummaryAdapter extends BaseSectionQuickAdapter<OrderSummaryWrapper, BaseViewHolder> {
    public OrderSummaryAdapter() {
        super(R.layout.item_order_summary, R.layout.item_order_summary_header, null);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        boolean header = viewType == SECTION_HEADER_VIEW;
        helper.itemView.setTag(header);
        if (header) {
            helper.addOnClickListener(R.id.osh_root);
        } else {
            helper.addOnClickListener(R.id.ios_root);
        }
        return helper;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (getDefItemViewType(position) == SECTION_HEADER_VIEW) {
            setFullSpan(holder);
            convertHead(holder, getItem(position - getHeaderLayoutCount()));
        } else {
            super.onBindViewHolder(holder, position);
        }
    }

    @Override
    protected void convertHead(BaseViewHolder helper, @Nullable OrderSummaryWrapper item) {
        if (item == null) return;
        SummaryPurchaserBean purchaser = item.getPurchaser();
        if (purchaser == null) return;
        helper.itemView.setTag(purchaser);
        String source = String.format("%s家店  |  ¥%s", CommonUtils.formatNum(purchaser.getShopCount()), CommonUtils.formatMoney(purchaser.getTotalAmount()));
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(helper.itemView.getContext(), R.color.color_999999)),
                source.indexOf("家"), source.indexOf("¥"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        helper.setText(R.id.osh_name, purchaser.getPurchaserName())
                .setText(R.id.osh_info, ss);
        ((GlideImageView) helper.getView(R.id.osh_image)).setImageURL(purchaser.getPurchaserLogo());
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderSummaryWrapper item) {
        SummaryShopBean shop = item.t;
        if (shop == null) return;
        helper.itemView.setTag(shop);
        String source = String.format("%s种商品共%s件  |  ¥%s", CommonUtils.formatNum(shop.getProductCount()),
                CommonUtils.formatNum(shop.getProductNum()),
                CommonUtils.formatMoney(shop.getProductAmount()));
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(helper.itemView.getContext(), R.color.color_999999)),
                source.indexOf("种"), source.indexOf("共") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(helper.itemView.getContext(), R.color.color_999999)),
                source.indexOf("件"), source.indexOf("¥"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        helper.setText(R.id.ios_info, ss).setText(R.id.ios_name, shop.getShopName());
    }
}
