package com.hll_sc_app.app.stockmanage.stocklogquery;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.HouseBean;
import com.hll_sc_app.bean.stockmanage.BusinessTypeBean;
import com.hll_sc_app.bean.stockmanage.StockLogResp;

import java.util.List;

public interface IStockLogQueryContract {
    interface IView extends ILoadView {
        /*仓库列表*/
        void queryHouseListSuccess(List<HouseBean> houseBeans);

        /*交易类型*/
        void queryBusinessTypeSuccess(List<BusinessTypeBean> businessTypeBeans);

        /*日志*/
        void fetchStockLogsSuccess(StockLogResp resp, boolean isMore);


        String getHouseID();

        String getCreateTimeStart();

        String getCreateTimeEnd();

        String getBusinessType();

        String getSearchKey();
    }

    interface IPresent extends IPresenter<IView> {
        /*仓库列表*/
        void queryHouseList();

        /*交易类型*/
        void queryBusinessType();

        /*日志*/
        void fetchStockLogs(boolean isLoading);

        void fetchMore();

        void refresh();

        void export(String email);

        int getPageSize();
    }
}
