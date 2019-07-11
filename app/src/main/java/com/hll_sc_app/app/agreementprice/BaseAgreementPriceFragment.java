package com.hll_sc_app.app.agreementprice;

import com.hll_sc_app.app.agreementprice.goods.GoodsPriceFragment;
import com.hll_sc_app.app.agreementprice.quotation.QuotationFragment;
import com.hll_sc_app.app.agreementprice.search.AgreementPriceSearchActivity;
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

    /**
     * 触发搜索
     */
    public abstract void toSearch();

    /**
     * 获取搜索词
     *
     * @return 搜索词
     */
    public String getSearchParam() {
        if (getActivity() instanceof AgreementPriceSearchActivity) {
            return ((AgreementPriceSearchActivity) getActivity()).getSearchText();
        }
        return "";
    }

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
            } else if (BaseAgreementPriceFragment.this instanceof GoodsPriceFragment) {
                ((GoodsPriceFragment) BaseAgreementPriceFragment.this).getPresenter().exportQuotation(email);
            }
        });
    }
}
