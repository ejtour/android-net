package com.hll_sc_app.app.aftersales.goodsoperation;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.aftersales.AfterSalesActionReq;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/15
 */

public interface IGoodsOperationContract {
    interface IGoodsOperationView extends ILoadView{
        void handleStatusChange();
    }

    interface IGoodsOperationPresenter extends IPresenter<IGoodsOperationView>{
        void doAction(List<AfterSalesActionReq.ActionBean> list);
    }
}
