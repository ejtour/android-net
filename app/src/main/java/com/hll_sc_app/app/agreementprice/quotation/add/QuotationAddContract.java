package com.hll_sc_app.app.agreementprice.quotation.add;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationDetailResp;

/**
 * 协议价管理-添加报价单
 *
 * @author zhuyingsong
 * @date 2019/7/9
 */
public interface QuotationAddContract {

    interface IPurchaseView extends ILoadView {
        /**
         * 显示报价商品列表
         *
         * @param resp 报价商品数据
         */
        void showGoodsDetail(QuotationDetailResp resp);

    }

    interface IPurchasePresenter extends IPresenter<IPurchaseView> {

    }
}
