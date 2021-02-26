package com.hll_sc_app.bean.report.marketing;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;

import com.hll_sc_app.app.order.details.OrderDetailActivity;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/7/28
 */

public class MarketingOrderBean implements IStringArrayGenerator {
    private int sequenceNo;
    private String subBillNo;
    private String createTime;
    private String shopName;
    private double totalAmount;
    private double actualAmount;
    private double discountAmount;

    @Override
    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(String.valueOf(sequenceNo));
        list.add(createText());
        list.add(DateUtil.getReadableTime(createTime, Constants.SLASH_YYYY_MM_DD_HH_MM));
        list.add(shopName);
        list.add("¥" + CommonUtils.formatMoney(totalAmount));
        list.add("¥" + CommonUtils.formatMoney(actualAmount));
        list.add("¥" + CommonUtils.formatMoney(discountAmount));
        return list;
    }

    private SpannableString createText() {
        SpannableString ss = new SpannableString(subBillNo);
        ss.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                if (widget.getTag() instanceof MarketingOrderBean) {
                    OrderDetailActivity.startByBillNo(((MarketingOrderBean) widget.getTag()).getSubBillNo());
                }
            }
        }, 0, subBillNo.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    public int getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(int sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getSubBillNo() {
        return subBillNo;
    }

    public void setSubBillNo(String subBillNo) {
        this.subBillNo = subBillNo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(double actualAmount) {
        this.actualAmount = actualAmount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }
}
