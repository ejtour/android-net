package com.hll_sc_app.app.agreementprice.quotation.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationDetailResp;

/**
 * 协议价管理-报价单详情
 *
 * @author zhuyingsong
 * @date 2019/2/18
 */
public interface QuotationDetailContract {

    interface IPurchaseView extends ILoadView {
        /**
         * 显示报价商品列表
         *
         * @param resp 报价商品数据
         */
        void showGoodsDetail(QuotationDetailResp resp);

        /**
         * 禁用报价单成功
         */
        void disableQuotationSuccess();
    }

    interface IPurchasePresenter extends IPresenter<IPurchaseView> {
        /**
         * 获取报价单详情
         */
        void getQuotationDetail();

        /**
         * 停用和失效报价单
         */
        void disableQuotation();
    }
}
