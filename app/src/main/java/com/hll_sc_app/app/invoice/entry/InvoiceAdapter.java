package com.hll_sc_app.app.invoice.entry;

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
        ((GlideImageView) helper.setText(R.id.ii_shop_name, item.getPurchaserShopName())
                .setText(R.id.ii_group_name, item.getPurchaserName())
                .setText(R.id.ii_money, processMoney(item.getInvoicePrice()))
                .setText(R.id.ii_tag, item.getInvoiceType() == 2 ? "专用发票" : "普通发票")
                .setText(R.id.ii_time, DateUtil.getReadableTime(item.getCreateTime(), Constants.SIGNED_YYYY_MM_DD_HH_MM))
                .getView(R.id.ii_icon)).setImageURL(item.getImagePath());
    }

    private SpannableString processMoney(double money) {
        String source = String.format("¥ %s", CommonUtils.formatMoney(money));
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new RelativeSizeSpan(1.3f), 2, source.indexOf("."), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }
}
