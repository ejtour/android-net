package com.hll_sc_app.app.agreementprice.quotation.add;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationReq;

/**
 * 协议价管理-添加报价单
 *
 * @author zhuyingsong
 * @date 2019/7/9
 */
public interface QuotationAddContract {

    interface IPurchaseView extends ILoadView {

        /**
         * 新增成功
         */
        void addSuccess();

    }

    interface IPurchasePresenter extends IPresenter<IPurchaseView> {
        /**
         * 添加报价单
         *
         * @param req 报价单
         */
        void addQuotation(QuotationReq req);
    }
}
