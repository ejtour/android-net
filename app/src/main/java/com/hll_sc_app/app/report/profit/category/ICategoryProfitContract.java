package com.hll_sc_app.app.report.profit.category;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.report.profit.ProfitResp;
import com.hll_sc_app.bean.user.CategoryResp;
import com.hll_sc_app.impl.IExportView;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/16
 */

public interface ICategoryProfitContract {
    interface ICategoryProfitView extends IExportView {
        void setData(ProfitResp resp, boolean append);

        BaseMapReq.Builder getReq();

        void setCategory(CategoryResp resp);
    }

    interface ICategoryProfitPresenter extends IPresenter<ICategoryProfitView> {
        void queryCategory();

        void loadList();

        void refresh();

        void loadMore();

        void export(String email);
    }
}
