package com.hll_sc_app.app.inquiry.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.inquiry.InquiryBean;
import com.hll_sc_app.bean.inquiry.InquiryBindResp;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/19
 */

interface IInquiryDetailContract {
    interface IInquiryDetailView extends ILoadView {
        void setData(InquiryBean bean);

        String getID();

        boolean isSubmit();

        void success();

        void toGenerate(InquiryBindResp resp);

        void toBind(InquiryBindResp resp);
    }

    interface IInquiryDetailPresenter extends IPresenter<IInquiryDetailView> {

        void submit(InquiryBean bean);

        void generate();
    }
}
