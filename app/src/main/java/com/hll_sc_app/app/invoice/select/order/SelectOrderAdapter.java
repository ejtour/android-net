package com.hll_sc_app.app.invoice.select.order;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.aftersales.detail.AfterSalesDetailActivity;
import com.hll_sc_app.app.order.common.OrderHelper;
import com.hll_sc_app.app.order.details.OrderDetailActivity;
import com.hll_sc_app.bean.invoice.InvoiceOrderBean;
import com.hll_sc_app.utils.ColorStr;
import com.hll_sc_app.utils.DateUtil;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/12
 */

public class SelectOrderAdapter extends BaseQuickAdapter<InvoiceOrderBean, BaseViewHolder> {
    SelectOrderAdapter() {
        super(R.layout.item_invoice_select_order);
    }

    @Override
    protected void convert(BaseViewHolder helper, InvoiceOrderBean item) {
        String payMethod = null;
        String type = OrderHelper.getPayType(item.getPayType());
        if (!TextUtils.isEmpty(type))
            payMethod = String.format("%s(%s)", type, OrderHelper.getPaymentWay(item.getPaymentWay()));
        String orderNo;
        if (item.getBillType() == 1) orderNo = "订单号：" + item.getBillNo();
        else orderNo = "退款单号：" + item.getBillNo();
        TextView no = helper.setText(R.id.iso_amount, "¥" + item.getBillAmount())
                .setText(R.id.iso_time, DateUtil.getReadableTime(item.getBillCreateTime()))
                .setText(R.id.iso_pay_method, payMethod)
                .setText(R.id.iso_order_no, processOrderNo(orderNo, item.getBillNo()))
                .getView(R.id.iso_order_no);
        no.setTag(item);
        no.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private SpannableString processOrderNo(String source, String billNo) {
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                if (widget.getTag() instanceof InvoiceOrderBean) {
                    InvoiceOrderBean bean = (InvoiceOrderBean) widget.getTag();
                    if (bean.getBillType() == 1) OrderDetailActivity.start(bean.getBillID());
                    else
                        AfterSalesDetailActivity.start(((Activity) widget.getContext()), bean.getBillID());
                }
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor(ColorStr.COLOR_5695D2));
            }
        }, source.indexOf(billNo), source.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }
}
