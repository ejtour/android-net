package com.hll_sc_app.app.inquiry;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.inquiry.InquiryBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/18
 */

interface IInquiryContract {
    interface IInquiryView extends ILoadView {
        void setData(List<InquiryBean> list, boolean append);

        int getStatus();
    }

    interface IInquiryPresenter extends IPresenter<IInquiryView>{
        void refresh();

        void loadMore();
    }
}
