package com.hll_sc_app.app.order.summary;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.order.summary.SummaryPurchaserBean;
import com.hll_sc_app.bean.order.summary.SummaryShopBean;
import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/1/7
 */

public class OrderSummaryAdapter extends BaseQuickAdapter<SummaryPurchaserBean, BaseViewHolder> {


    private OnItemChildClickListener mListener;

    OrderSummaryAdapter() {
        super(R.layout.item_order_summary);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        RecyclerView listView = helper.getView(R.id.ios_list_view);
        OrderShopSummaryAdapter adapter = new OrderShopSummaryAdapter();
        adapter.setOnItemChildClickListener(mListener);
        listView.setAdapter(adapter);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, SummaryPurchaserBean item) {
        helper.itemView.setTag(item);
        String source = String.format("%s家店  |  ¥%s", CommonUtils.formatNum(item.getShopCount()), CommonUtils.formatMoney(item.getTotalAmount()));
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(helper.itemView.getContext(), R.color.color_999999)),
                source.indexOf("家"), source.indexOf("¥"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((GlideImageView) helper.setText(R.id.ios_name, item.getPurchaserName())
                .setText(R.id.ios_info, ss)
                .getView(R.id.ios_image)).setImageURL(item.getPurchaserLogo());
        ((OrderShopSummaryAdapter) ((RecyclerView) helper.getView(R.id.ios_list_view)).getAdapter()).setNewData(item.getShopList());
    }

    @Override
    public void setOnItemChildClickListener(OnItemChildClickListener listener) {
        mListener = listener;
    }

    static class OrderShopSummaryAdapter extends BaseQuickAdapter<SummaryShopBean, BaseViewHolder> {

        OrderShopSummaryAdapter() {
            super(R.layout.item_order_summary_shop);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
            helper.addOnClickListener(R.id.oss_stall)
                    .addOnClickListener(R.id.oss_goods)
                    .addOnClickListener(R.id.oss_search_shop);
            return helper;
        }

        @Override
        protected void convert(BaseViewHolder helper, SummaryShopBean item) {
            helper.itemView.setBackgroundResource(helper.getAdapterPosition() % 2 == 0 ? R.drawable.bg_fafafa_radius_5_solid : android.R.color.transparent);
            String info = String.format("%s种商品共%s件  |  ¥%s", CommonUtils.formatNum(item.getProductCount()),
                    CommonUtils.formatNum(item.getProductNum()),
                    CommonUtils.formatMoney(item.getProductAmount()));
            boolean hasStall = !CommonUtils.isEmpty(item.getStallList());
            helper.setText(R.id.oss_shop_name, item.getShopName())
                    .setTag(R.id.oss_search_shop, item)
                    .setTag(R.id.oss_stall, item)
                    .setTag(R.id.oss_goods, item)
                    .setText(R.id.oss_info, info)
                    .setGone(R.id.oss_tag, item.getSubbillCategory() == 2)
                    .setGone(R.id.oss_stall, hasStall)
                    .setGone(R.id.oss_div, hasStall);
        }
    }
}
