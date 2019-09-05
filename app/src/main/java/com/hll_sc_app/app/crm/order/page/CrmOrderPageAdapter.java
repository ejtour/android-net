package com.hll_sc_app.app.crm.order.page;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.order.shop.OrderShopBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.DateUtil;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/5
 */

public class CrmOrderPageAdapter extends BaseQuickAdapter<OrderShopBean, BaseViewHolder> {

    private final int mBillStatus;
    private String mActionType;
    private Drawable mToday;
    private Drawable mAll;
    private Drawable mArrow;

    CrmOrderPageAdapter(int billStatus) {
        super(R.layout.item_crm_order_page);
        mBillStatus = billStatus;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderShopBean item) {
        if (mArrow == null) {
            mArrow = ContextCompat.getDrawable(helper.itemView.getContext(), R.drawable.ic_arrow_gray);
            mArrow.setBounds(0, 0, mArrow.getIntrinsicWidth(), mArrow.getIntrinsicHeight());
        }
        boolean today = "1".equals(mActionType);
        TextView view = helper.setText(R.id.cop_shop_name, item.getShopName())
                .setText(R.id.cop_latest_visit, "最近一次拜访：" + ("0".equals(item.getLastVisitTime()) ? "— —"
                        : DateUtil.getReadableTime(item.getLastVisitTime(), CalendarUtils.FORMAT_DATE_TIME)))
                .setText(R.id.cop_all_orders, today ? processText(item.getTodayAmount(), item.getTodayBillNum()) :
                        processText(item.getAllAmount(), item.getAllBillNum()))
                .setText(R.id.cop_ave_order, today ? processText(item.getThirtyDaysAmount(), item.getThirtyDaysBillNum()) :
                        processText(item.getThirtyDaysAverageAmount(), item.getThirtyDaysAverageBillNum()))
                .setText(R.id.cop_ave_order_label, today ? "近30日订单" : "近30日日均下单")
                .setText(R.id.cop_all_orders_label, today ? "今日订单" : "全部订单").getView(R.id.cop_all_orders_label);
        if (today) {
            if (mToday == null) {
                mToday = ContextCompat.getDrawable(view.getContext(), R.drawable.ic_crm_order_date);
                mToday.setBounds(0, 0, mToday.getIntrinsicWidth(), mToday.getIntrinsicHeight());
            }
            view.setCompoundDrawables(mToday, null, mArrow, null);
        } else {
            if (mAll == null) {
                mAll = ContextCompat.getDrawable(view.getContext(), R.drawable.ic_crm_order_all);
                mAll.setBounds(0, 0, mAll.getIntrinsicWidth(), mAll.getIntrinsicHeight());
            }
            view.setCompoundDrawables(mAll, null, mArrow, null);
        }
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        helper.addOnClickListener(R.id.cop_shop_detail)
                .addOnClickListener(R.id.cop_all_orders_label)
                .addOnClickListener(R.id.cop_ave_order_label)
                .setGone(R.id.cop_make_order, mBillStatus == 0)
                .addOnClickListener(R.id.cop_make_order);
        return helper;
    }

    public void setNewData(@Nullable List<OrderShopBean> data, String actionType) {
        mActionType = actionType;
        super.setNewData(data);
    }

    private SpannableString processText(double amount, double billNum) {
        String source = CommonUtils.formatMoney(amount) + " / " + CommonUtils.formatNumber(billNum) + "笔";
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new RelativeSizeSpan(1.38f), 0, source.indexOf(" "), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }
}
