package com.hll_sc_app.app.report.daily;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.report.daily.SalesDailyBean;
import com.hll_sc_app.impl.IExportView;

import java.util.List;

/**
 * 销售日报
 *
 * @author chukun
 * @date 2019.09.09
 */
public interface SalesDailyContract {

    interface ISalesDailyView extends IExportView {
        /**
         */
        void setData(List<SalesDailyBean> list, boolean append);

        /**
         * 获取参数列表
         * @return 开始时间
         */
        BaseMapReq.Builder getReq();
    }

    interface ISalesDailyPresenter extends IPresenter<ISalesDailyView> {
        void refresh();

        void loadMore();

        void export(String email);
    }
}
