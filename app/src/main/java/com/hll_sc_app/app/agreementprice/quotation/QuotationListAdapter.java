package com.hll_sc_app.app.agreementprice.quotation;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationBean;
import com.hll_sc_app.citymall.util.CalendarUtils;

import java.util.Date;

/**
 * 协议价管理-报价单
 *
 * @author 朱英松
 * @date 2019/7/8
 */
public class QuotationListAdapter extends BaseQuickAdapter<QuotationBean, BaseViewHolder> {
    private static final String YYYY_MM_DD = "yyyy/MM/dd";
    private static final String YYYY_MM_DD_2 = "yy / MM / dd";

    QuotationListAdapter() {
        super(R.layout.list_item_agreement_price_quotation);
    }

    @Override
    protected void convert(BaseViewHolder helper, QuotationBean item) {
        helper.setText(R.id.txt_billNo, "报价单号：" + getString(item.getBillNo()))
            .setText(R.id.txt_purchaserName, "适用集团：" + getString(item.getPurchaserName()))
            .setText(R.id.txt_shopName, "适用门店：" + getString(item.getShopName()))
            .setText(R.id.txt_priceDate, "生效期限：" + getPriceDate(item.getPriceStartDate(), item.getPriceEndDate()))
            .setText(R.id.txt_billCreateBy, "创建人：" + getString(item.getBillCreateBy()))
            .setText(R.id.txt_billDate, CalendarUtils.format(CalendarUtils.parse(item.getBillDate(),
                CalendarUtils.FORMAT_SERVER_DATE), YYYY_MM_DD_2));
        if (item.getBillStatus() == QuotationBean.BILL_STATUS_AUDIT) {
            helper.setImageResource(R.id.img_logoUrl, R.drawable.ic_price_manager_audit);
        } else if (item.getBillStatus() == QuotationBean.BILL_STATUS_ABANDON) {
            helper.setImageResource(R.id.img_logoUrl, R.drawable.ic_price_manager_abandon);
        } else if (item.getBillStatus() == QuotationBean.BILL_STATUS_EXPIRE) {
            helper.setImageResource(R.id.img_logoUrl, R.drawable.ic_price_manager_expire);
        } else if (item.getBillStatus() == QuotationBean.BILL_STATUS_NO_AUDIT) {
            helper.setImageResource(R.id.img_logoUrl, R.drawable.ic_price_manager_no_audit);
        } else if (item.getBillStatus() == QuotationBean.BILL_STATUS_REJECT) {
            helper.setImageResource(R.id.img_logoUrl, R.drawable.ic_price_manager_reject);
        } else if (item.getBillStatus() == QuotationBean.BILL_STATUS_CANCEL) {
            helper.setImageResource(R.id.img_logoUrl, R.drawable.ic_price_manager_cancel);
        } else {
            helper.setImageResource(R.id.img_logoUrl, 0);
        }
    }

    private String getString(String str) {
        return TextUtils.isEmpty(str) ? "无" : str;
    }

    /**
     * 获取生效期限的显示方式
     *
     * @param priceStartDate 起始时间
     * @param priceEndDate   结束时间
     * @return 显示结果
     */
    public static String getPriceDate(String priceStartDate, String priceEndDate) {
        Date startDate = CalendarUtils.parse(priceStartDate, CalendarUtils.FORMAT_SERVER_DATE);
        String startStr = null;
        if (startDate != null) {
            startStr = CalendarUtils.format(startDate, QuotationListAdapter.YYYY_MM_DD);
        }
        Date endDate = CalendarUtils.parse(priceEndDate, CalendarUtils.FORMAT_SERVER_DATE);
        String endStr = null;
        if (endDate != null) {
            endStr = CalendarUtils.format(endDate, QuotationListAdapter.YYYY_MM_DD);
        }
        if (TextUtils.isEmpty(startStr)) {
            return "";
        } else if (TextUtils.isEmpty(endStr)) {
            return String.format("%s - %s", startStr, "2099/01/01");
        } else {
            return String.format("%s - %s", startStr, endStr);
        }
    }
}
