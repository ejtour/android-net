package com.hll_sc_app.app.deliveryroute.detail;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.crm.order.list.CrmOrderListActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.other.RouteDetailBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.ColorStr;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/28
 */

public class RouteDetailAdapter extends BaseQuickAdapter<RouteDetailBean, BaseViewHolder> {
    RouteDetailAdapter() {
        super(R.layout.item_delivery_route_detail);
        setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.drd_order_btn) {
                RouteDetailBean item = getItem(position);
                if (item == null) return;
                CrmOrderListActivity.start(item.getShopID(), item.getShopName());
                return;
            }
            if (view.getTag() == null) return;
            UIUtils.callPhone(view.getContext(), view.getTag().toString());
        });
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        helper.addOnClickListener(R.id.drd_receiver)
                .addOnClickListener(R.id.drd_driver)
                .addOnClickListener(R.id.drd_order_btn)
                .addOnClickListener(R.id.drd_salesman);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, RouteDetailBean item) {
        ((GlideImageView) helper.setText(R.id.drd_shop_name, item.getShopName())
                .setText(R.id.drd_status, item.getStatus())
                .setText(R.id.drd_amount, CommonUtils.formatMoney(item.getPrice()))
                .setText(R.id.drd_bill_num, CommonUtils.formatNum(item.getOrderNum()))
                .setText(R.id.drd_goods_num, CommonUtils.formatNum(item.getGoodsNum()))
                .setTag(R.id.drd_receiver, item.getReceiverPhone())
                .setTag(R.id.drd_driver, item.getMobilePhone())
                .setTag(R.id.drd_salesman, item.getLinkPhone())
                .setText(R.id.drd_receiver, processText("收货人：", item.getReceiver()))
                .setText(R.id.drd_driver, processText("司机：", item.getDriverName()))
                .setText(R.id.drd_salesman, processText("销售代表：", item.getSalesManName()))
                .getView(R.id.drd_icon)).setImageURL(item.getImagePath());
    }

    private CharSequence processText(String prefix, String content) {
        if (TextUtils.isEmpty(content)) return prefix;
        String source = prefix + content;
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new ForegroundColorSpan(Color.parseColor(ColorStr.COLOR_5695D2)), source.indexOf("：") + 1, source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }
}
