package com.hll_sc_app.app.report.search;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.resp.group.PurchaserGroupBean;
import com.hll_sc_app.bean.report.resp.product.ProductSaleResp;
import com.hll_sc_app.bean.report.resp.product.ProductSaleTop10Bean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/24
 */

public interface IReportSearchContract {
    interface IReportSearchView extends ILoadView {
        void showSearchList(List<PurchaserGroupBean> list);

        String getSearchWord();

    }

    interface IReportSearchPresenter extends IPresenter<IReportSearchView> {
        void querySearchList(String groupType,String searchWord,Integer pageNo,Integer pageSize);
    }
}
