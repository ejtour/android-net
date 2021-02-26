package com.hll_sc_app.app.order.statistic;

import android.app.Activity;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.order.statistic.OrderStatisticShopBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.ContactDialog;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/8
 */
public class OrderShopStatisticAdapter extends BaseQuickAdapter<OrderStatisticShopBean, BaseViewHolder> {

    private boolean mNotOrder;
    private String mPrefix;

    public OrderShopStatisticAdapter() {
        super(R.layout.item_order_statistic_shop);
        setOnItemChildClickListener((adapter, view, position) -> {
            OrderStatisticShopBean item = getItem(position);
            if (item == null) return;
            new ContactDialog(((Activity) view.getContext()), item.getLinkman(), item.getMobile()).show();
        });
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        helper.addOnClickListener(R.id.oss_dial);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderStatisticShopBean item) {
        helper.itemView.setBackgroundResource(helper.getAdapterPosition() % 2 == 0 ? R.drawable.bg_fafafa_radius_5_solid : android.R.color.transparent);
        String info = String.format("%s订单数：%s  |  金额：¥%s", mPrefix, CommonUtils.formatNum(item.getTotalNum()),
                CommonUtils.formatMoney(item.getTotalAmount()));
        helper.setText(R.id.oss_shop_name, item.getShopName())
                .setText(R.id.oss_info, info)
                .setGone(R.id.oss_dial, mNotOrder);
        TextView dial = helper.getView(R.id.oss_dial);
        if (mNotOrder) {
            boolean noNum = TextUtils.isEmpty(item.getMobile());
            dial.setCompoundDrawablesWithIntrinsicBounds(noNum ? 0 : R.drawable.ic_dial, 0, 0, 0);
            dial.setText(noNum ? "无号码" : "");
            dial.setClickable(!noNum);
        }
    }

    public void setNewData(@Nullable List<OrderStatisticShopBean> data, String prefix, boolean notOrder) {
        mPrefix = prefix == null ? "" : prefix;
        mNotOrder = notOrder;
        super.setNewData(data);
    }
}
