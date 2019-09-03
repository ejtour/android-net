package com.hll_sc_app.app.report.refund.search;

import com.hll_sc_app.app.agreementprice.goods.GoodsPriceFragment;
import com.hll_sc_app.app.agreementprice.quotation.QuotationFragment;
import com.hll_sc_app.app.agreementprice.search.AgreementPriceSearchActivity;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.utils.Utils;

/**
 * 退货搜索的父fragment
 * @author chukun
 * @date 2019/9/3
 */
public abstract class BaseRefundSearchFragment extends BaseLazyFragment {

    /**
     * 触发搜索
     */
    public abstract void toSearch();

    public String getSearchParam() {
        String searchPram = "";
        if (getActivity() instanceof RefundSearchActivity) {
            searchPram = ((RefundSearchActivity) getActivity()).getSearchText();
        }
        return searchPram;
    }

}
