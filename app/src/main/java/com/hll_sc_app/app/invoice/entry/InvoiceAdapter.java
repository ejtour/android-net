package com.hll_sc_app.app.invoice.entry;

import android.graphics.drawable.GradientDrawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.invoice.InvoiceBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/10
 */

public class InvoiceAdapter extends BaseQuickAdapter<InvoiceBean, BaseViewHolder> {
    InvoiceAdapter() {
        super(R.layout.item_invoice);
    }

    @Override
    protected void convert(BaseViewHolder helper, InvoiceBean item) {
        ((GlideImageView) helper.setText(R.id.ii_shop_name, item.getShopTotal() > 1 ?
                String.format("包含：%s个门店", item.getShopTotal()) : item.getPurchaserShopName())
                .setText(R.id.ii_group_name, item.getPurchaserName())
                .setText(R.id.ii_money, processMoney(item.getInvoicePrice()))
                .setText(R.id.ii_tag, item.getInvoiceTypeLabel())
                .setTextColor(R.id.ii_tag, getTextColor(item.getInvoiceType()))
                .setText(R.id.ii_time, String.format("申请日期：%s", DateUtil.getReadableTime(item.getCreateTime(), Constants.SLASH_YYYY_MM_DD_HH_MM)))
                .setText(R.id.ii_range, String.format("业务日期：%s - %s",
                        CommonUtils.getDouble(item.getBusinessBeginDate()) == 0 ? "0" :
                                DateUtil.getReadableTime(item.getBusinessBeginDate(), Constants.SLASH_YYYY_MM_DD),
                        CommonUtils.getDouble(item.getBusinessEndDate()) == 0 ? "0" :
                                DateUtil.getReadableTime(item.getBusinessEndDate(), Constants.SLASH_YYYY_MM_DD)))
                .getView(R.id.ii_icon)).setImageURL(item.getImagePath());
        ((GradientDrawable) helper.getView(R.id.ii_tag).getBackground()).setColor(getBgColor(item.getInvoiceType()));
    }

    private int getTextColor(int type) {
        switch (type) {
            case 1:
                return 0xFFFF7A45;
            case 2:
                return 0xFF85A5FF;
            case 3:
                return 0xFF4AAB83;
            default:
                return 0;
        }
    }

    private int getBgColor(int type) {
        switch (type) {
            case 1:
                return 0xFFFFF7E6;
            case 2:
                return 0xFFF0F5FF;
            case 3:
                return 0xFFF1FEF9;
            default:
                return 0;
        }
    }

    private SpannableString processMoney(double money) {
        String source = String.format("¥ %s", CommonUtils.formatMoney(money));
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new RelativeSizeSpan(1.3f), 2, source.indexOf("."), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private int getItemPosition(InvoiceBean item) {
        return item != null && mData != null && !mData.isEmpty() ? mData.indexOf(item) : -1;
    }

    void removeData(InvoiceBean data) {
        remove(getItemPosition(data));
    }
}
