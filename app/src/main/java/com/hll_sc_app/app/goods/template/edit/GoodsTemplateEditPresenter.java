package com.hll_sc_app.app.goods.template.edit;

import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * 从商品库导入-商品编辑
 *
 * @author zhuyingsong
 * @date 2019/6/29
 */
public class GoodsTemplateEditPresenter implements GoodsTemplateEditContract.IGoodsTemplateEditPresenter {
    private GoodsTemplateEditContract.IGoodsTemplateEditView mView;

    static GoodsTemplateEditPresenter newInstance() {
        return new GoodsTemplateEditPresenter();
    }

    @Override
    public void start() {
    }

    @Override
    public void register(GoodsTemplateEditContract.IGoodsTemplateEditView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }
}
