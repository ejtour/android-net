package com.hll_sc_app.app.setting.tax.special;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.user.SpecialTaxBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Utils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/31
 */

public class SpecialTaxSettingAdapter extends BaseQuickAdapter<SpecialTaxBean, BaseViewHolder> {

    private boolean mEditable;

    SpecialTaxSettingAdapter() {
        super(R.layout.item_special_tax_setting);
    }

    void setEditable(boolean editable) {
        if (mEditable) {
            for (SpecialTaxBean bean : mData) {
                bean.setSelected(false);
            }
        }
        mEditable = editable;
        notifyDataSetChanged();
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        ((EditText) helper.getView(R.id.sts_number)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Utils.processMoney(s, false);
                SpecialTaxBean item = getItem(helper.getAdapterPosition());
                if (item != null) item.setTaxRate(s.toString());
            }
        });
        helper.addOnClickListener(R.id.sts_check);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, SpecialTaxBean item) {
        helper.setGone(R.id.sts_check, mEditable)
                .setText(R.id.sts_name, item.getProductName())
                .setText(R.id.sts_number, TextUtils.isEmpty(item.getTaxRate()) ? "" :
                        CommonUtils.formatNumber(item.getTaxRate()))
                .setText(R.id.sts_spec, String.format("规格：%s种", item.getSaleSpecNum()));
        ((GlideImageView) helper.getView(R.id.sts_image)).setImageURL(item.getImgUrl());
        helper.getView(R.id.sts_check).setSelected(item.isSelected());
        helper.getView(R.id.sts_number).setEnabled(!mEditable);
    }
}
