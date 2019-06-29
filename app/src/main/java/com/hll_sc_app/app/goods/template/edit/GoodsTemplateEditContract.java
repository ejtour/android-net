package com.hll_sc_app.app.goods.template.edit;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.GoodsBean;

import java.util.List;

/**
 * 从商品库导入-商品编辑
 *
 * @author zhuyingsong
 * @date 2019/6/29
 */
public interface GoodsTemplateEditContract {

    interface IGoodsTemplateEditView extends ILoadView {
        /**
         * 展示商品单列表
         *
         * @param list list
         */
        void showGoodsTemplateList(List<GoodsBean> list);
    }

    interface IGoodsTemplateEditPresenter extends IPresenter<IGoodsTemplateEditView> {

    }
}
