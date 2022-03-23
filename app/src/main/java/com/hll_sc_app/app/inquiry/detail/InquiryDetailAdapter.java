package com.hll_sc_app.app.inquiry.detail;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.inquiry.InquiryDetailBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Utils;

import java.text.DecimalFormat;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/19
 */

class InquiryDetailAdapter extends BaseQuickAdapter<InquiryDetailBean, BaseViewHolder> {

    private final boolean mEditable;
    private final boolean mShowNum;

    public InquiryDetailAdapter(boolean editable, boolean showNum) {
        super(editable ? R.layout.item_inquiry_detail_edit : R.layout.item_inquiry_detail_show);
        mEditable = editable;
        mShowNum = showNum;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        if (mEditable) {
            ((TextView) helper.getView(R.id.iid_price)).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    InquiryDetailBean bean = getItem(helper.getAdapterPosition());
                    if (bean == null) return;
                    Utils.processMoney(s, false);
                    bean.setEnquiryPrice(s.toString());
                }
            });
            ((TextView) helper.getView(R.id.iid_tax_rate)).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    InquiryDetailBean bean = getItem(helper.getAdapterPosition());
                    if (bean == null) return;
                    Utils.processMoney(s, false);
                    bean.setTaxRate(CommonUtils.getDouble(s.toString()));
                }
            });
        }
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, InquiryDetailBean item) {
        double price = CommonUtils.getDouble(item.getEnquiryPrice());
        helper.setText(R.id.iid_name, item.getGoodsName())
                .setText(R.id.iid_spec, String.format("%s  |  %s", item.getGoodsDesc(), item.getPurchaseUnit()))
                .setGone(R.id.iid_num, mShowNum)
                .setText(R.id.iid_num, String.format("数量：%s", CommonUtils.formatNum(item.getGoodsNum())))
                .setText(R.id.iid_price, mEditable ? formatMoney(price) :
                        ("¥ " + (CommonUtils.equalsZero(price) ? "— —" : CommonUtils.formatMoney(price))))
                .setText(R.id.iid_tax_rate, CommonUtils.formatNumber(item.getTaxRate()) + (mEditable ? "" : "%"));
    }

    private String formatMoney(double money) {
        return new DecimalFormat(".00").format(money);
    }
}
