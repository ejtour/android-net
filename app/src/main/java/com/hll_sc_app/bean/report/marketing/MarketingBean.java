package com.hll_sc_app.bean.report.marketing;

import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.view.View;

import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/7/27
 */

public class MarketingBean implements IStringArrayGenerator {
    private String discountID;
    private int sequenceNo;
    private int discountType;
    private String discountName;
    private int productNum;
    private int shopNum;
    private int billNum;
    private double totalAmount;
    private double actualAmount;
    private double discountAmount;
    private double discountRate;
    private transient String startDate;
    private transient String endDate;

    @Override
    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(String.valueOf(sequenceNo));
        list.add(discountType == 1 ? "订单活动" : discountType == 2 ? "商品活动" : discountType == 3 ? "优惠券活动" : "");
        list.add(discountName);
        list.add(discountType != 2 ? new SpannableString("- -") : createClickNum(productNum, RouterConfig.REPORT_MARKETING_PRODUCT));
        list.add(createClickNum(shopNum, RouterConfig.REPORT_MARKETING_SHOP));
        list.add(createClickNum(billNum, RouterConfig.REPORT_MARKETING_ORDER));
        list.add("¥" + CommonUtils.formatMoney(totalAmount));
        list.add("¥" + CommonUtils.formatMoney(actualAmount));
        list.add("¥" + CommonUtils.formatMoney(discountAmount));
        list.add(CommonUtils.formatNum(discountRate) + "%");
        return list;
    }

    private SpannableString createClickNum(int num, String path) {
        String source = CommonUtils.formatNum(num);
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                if (widget.getTag() instanceof MarketingBean) {
                    MarketingBean bean = (MarketingBean) widget.getTag();
                    RouterUtil.goToActivity(path, bean.getDiscountID(), bean.getDiscountName(), bean.getStartDate(), bean.getEndDate());
                }
            }
        }, 0, source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDiscountID() {
        return discountID;
    }

    public void setDiscountID(String discountID) {
        this.discountID = discountID;
    }

    public int getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(int sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public double getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(double actualAmount) {
        this.actualAmount = actualAmount;
    }

    public int getBillNum() {
        return billNum;
    }

    public void setBillNum(int billNum) {
        this.billNum = billNum;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public int getDiscountType() {
        return discountType;
    }

    public void setDiscountType(int discountType) {
        this.discountType = discountType;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public int getShopNum() {
        return shopNum;
    }

    public void setShopNum(int shopNum) {
        this.shopNum = shopNum;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
