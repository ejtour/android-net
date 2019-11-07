package com.hll_sc_app.app.setting.tax;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.goods.CustomCategoryBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Utils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/30
 */

public class TaxSettingAdapter extends BaseQuickAdapter<CustomCategoryBean, BaseViewHolder> {
    TaxSettingAdapter() {
        super(R.layout.item_tax_setting);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        ((EditText) helper.getView(R.id.its_percent)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Utils.processMoney(s, false);
                CustomCategoryBean item = getItem(helper.getAdapterPosition());
                if (item != null)
                    item.setTaxRate(s.toString());
            }
        });
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, CustomCategoryBean item) {
        helper.setText(R.id.its_label, item.getCategoryName())
                .setText(R.id.its_percent, CommonUtils.formatNumber(item.getTaxRate()));
    }
}
