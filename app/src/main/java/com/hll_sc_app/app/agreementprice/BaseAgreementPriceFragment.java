package com.hll_sc_app.app.agreementprice;

import com.hll_sc_app.app.agreementprice.quotation.QuotationFragment;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.utils.Utils;

/**
 * 协议价管理-商品、报价单页面父Fragment
 *
 * @author zhuyingsong
 * @date 2019/7/8
 */
public abstract class BaseAgreementPriceFragment extends BaseLazyFragment {
    /**
     * 导出
     */
    public abstract void toExport();

    public void exportSuccess(String email) {
        Utils.exportSuccess(requireActivity(), email);
    }

    public void exportFailure(String tip) {
        Utils.exportFailure(requireActivity(), tip);
    }

    public void bindEmail() {
        Utils.bindEmail(requireActivity(), email -> {
            if (BaseAgreementPriceFragment.this instanceof QuotationFragment) {
                ((QuotationFragment) BaseAgreementPriceFragment.this).getPresenter().exportQuotation(email);
            }
        });
    }
}
