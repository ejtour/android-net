package com.hll_sc_app.app.wallet.details.list;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.wallet.details.DetailsListResp;
import com.hll_sc_app.bean.wallet.details.DetailsRecord;
import com.hll_sc_app.impl.IExportView;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/5/6
 */
public interface IDetailsListContract {
    interface IDetailsListView extends IExportView {
        /**
         * 设置明细列表
         */
        void setDetailsList(DetailsListResp resp, boolean refresh);
    }

    interface IDetailsListPresenter extends IPresenter<IDetailsListView> {

        /**
         * 刷新
         */
        void refresh();

        /**
         * 加载
         */
        void loadMore();

        /**
         * 导出明细
         */
        void export(String email);
    }
}
