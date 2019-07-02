package com.hll_sc_app.app.goods.invwarn;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.GoodsAddBatchResp;

/**
 * 代仓商品库存预警
 *
 * @author zhuyingsong
 * @date 2019/7/2
 */
public interface GoodsInvWarnContract {

    interface IGoodsInvWarnView extends ILoadView {
        /**
         * 保存成功
         *
         * @param resp resp
         */
        void addSuccess(GoodsAddBatchResp resp);
    }

    interface IGoodsInvWarnPresenter extends IPresenter<IGoodsInvWarnView> {

    }
}
