package com.hll_sc_app.app.order.statistic;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
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
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/5/19
 */

public class OrderStatisticAdapter extends BaseQuickAdapter<OrderStatisticBean, BaseViewHolder> {

    private boolean mNotOrder;
    private String mPrefix;

    public OrderStatisticAdapter() {
        super(R.layout.item_order_statistic);
    }

    public void setNewData(@Nullable List<OrderStatisticBean> data, boolean notOrder, String prefix) {
        mNotOrder = notOrder;
        mPrefix = prefix;
        super.setNewData(data);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        RecyclerView listView = helper.getView(R.id.ios_list_view);
        listView.setLayoutManager(new LinearLayoutManager(parent.getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        listView.setAdapter(new OrderShopStatisticAdapter());
        helper.addOnClickListener(R.id.ios_see_all);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderStatisticBean item) {
        String source = String.format("合作门店：%s  |  %s：%s",
                CommonUtils.formatNum(item.getCoopShopNum()),
                mNotOrder ? "未下单门店" : "已下单门店",
                CommonUtils.formatNum(item.getShopNum()));
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(helper.itemView.getContext(), R.color.color_999999)),
                0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(helper.itemView.getContext(), R.color.color_999999)),
                source.indexOf("|"), source.lastIndexOf("：") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((GlideImageView) helper.setText(R.id.ios_name, item.getPurchaserName())
                .setText(R.id.ios_info, ss)
                .setGone(R.id.ios_see_all, item.getShopNum() > 3)
                .getView(R.id.ios_image)).setImageURL(item.getGroupLogoUrl());
        OrderShopStatisticAdapter adapter = (OrderShopStatisticAdapter) ((RecyclerView) helper.getView(R.id.ios_list_view)).getAdapter();
        adapter.setNewData(item.getShopNum() > 3 ? item.getShopList().subList(0, 3) : item.getShopList(), mPrefix);
    }
}
