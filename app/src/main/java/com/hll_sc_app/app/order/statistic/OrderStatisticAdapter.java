package com.hll_sc_app.app.order.statistic;

import android.support.annotation.Nullable;
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
import com.hll_sc_app.bean.order.statistic.OrderStatisticBean;
import com.hll_sc_app.bean.order.statistic.OrderStatisticShopBean;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/5/19
 */

public class OrderStatisticAdapter extends BaseQuickAdapter<OrderStatisticBean, BaseViewHolder> {

    private boolean mNotOrder;

    public OrderStatisticAdapter() {
        super(R.layout.item_order_statistic);
    }

    public void setNewData(@Nullable List<OrderStatisticBean> data, boolean notOrder) {
        mNotOrder = notOrder;
        super.setNewData(data);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        RecyclerView listView = helper.getView(R.id.ios_list_view);
        listView.setAdapter(new OrderShopStatisticAdapter());
        helper.addOnClickListener(R.id.ios_toggle);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderStatisticBean item) {
        String source = String.format("合作门店：%s  |  %s：%s",
                CommonUtils.formatNum(item.getCoopShopNum()),
                mNotOrder ? "未下单" : "已下单",
                CommonUtils.formatNum(item.getShopNum()));
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(helper.itemView.getContext(), R.color.color_999999)),
                0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(helper.itemView.getContext(), R.color.color_999999)),
                source.indexOf("|"), source.lastIndexOf("：") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((GlideImageView) helper.setText(R.id.ios_name, item.getPurchaserName())
                .setText(R.id.ios_info, ss)
                .setGone(R.id.ios_toggle, item.getShopList().size() > 3)
                .setText(R.id.ios_label, item.isShowAll() ? "收起门店列表" : "展开查看全部门店")
                .setGone(R.id.ios_arrow_up, item.isShowAll())
                .setGone(R.id.ios_arrow_down, !item.isShowAll())
                .getView(R.id.ios_image)).setImageURL(item.getGroupLogoUrl());
        OrderShopStatisticAdapter adapter = (OrderShopStatisticAdapter) ((RecyclerView) helper.getView(R.id.ios_list_view)).getAdapter();
        adapter.setNewData(item.getShopList().size() > 3 && !item.isShowAll() ? item.getShopList().subList(0, 3) : item.getShopList());
    }

    static class OrderShopStatisticAdapter extends BaseQuickAdapter<OrderStatisticShopBean, BaseViewHolder> {

        public OrderShopStatisticAdapter() {
            super(R.layout.item_order_statistic_shop);
        }

        @Override
        protected void convert(BaseViewHolder helper, OrderStatisticShopBean item) {
            helper.itemView.setBackgroundResource(helper.getAdapterPosition() % 2 == 0 ? R.drawable.bg_fafafa_radius_5_solid : android.R.color.transparent);
            String info = String.format("订单数：%s  |  金额：¥%s", CommonUtils.formatNum(item.getTotalNum()),
                    CommonUtils.formatMoney(item.getTotalAmount()));
            helper.setText(R.id.oss_shop_name, item.getShopName())
                    .setText(R.id.oss_info, info);
        }
    }
}
