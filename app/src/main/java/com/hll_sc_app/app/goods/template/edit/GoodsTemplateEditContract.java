package com.hll_sc_app.app.goods.template.edit;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.GoodsAddBatchReq;
import com.hll_sc_app.bean.goods.GoodsAddBatchResp;

/**
 * 从商品库导入-商品编辑
 *
 * @author zhuyingsong
 * @date 2019/6/29
 */
public interface GoodsTemplateEditContract {

    interface IGoodsTemplateEditView extends ILoadView {
        /**
         * 保存成功
         *
         * @param resp resp
         */
        void addSuccess(GoodsAddBatchResp resp);
    }

    interface IGoodsTemplateEditPresenter extends IPresenter<IGoodsTemplateEditView> {
        /**
         * 商品库批量新增商品
         *
         * @param req 请求参数
         */
        void batchAddGoods(GoodsAddBatchReq req);
    }
}
