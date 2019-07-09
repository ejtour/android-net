package com.hll_sc_app.app.agreementprice.quotation.add;

import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * 协议价管理-添加报价单
 *
 * @author zhuyingsong
 * @date 2019/7/9
 */
public class QuotationAddPresenter implements QuotationAddContract.IPurchasePresenter {
    private QuotationAddContract.IPurchaseView mView;


    static QuotationAddPresenter newInstance() {
        return new QuotationAddPresenter();
    }

    @Override
    public void start() {
    }

    @Override
    public void register(QuotationAddContract.IPurchaseView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }
}
