package com.hll_sc_app.app.goods.assign.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.GoodsAssignBean;
import com.hll_sc_app.bean.goods.GoodsAssignDetailBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/8/25
 */
public interface IGoodsAssignDetailContract {
    interface IGoodsAssignDetailView extends ILoadView {
        void setList(List<GoodsAssignDetailBean> list);

        void cacheGroupUrl(String url);

        void saveSuccess();
    }

    interface IGoodsAssignDetailPresenter extends IPresenter<IGoodsAssignDetailView> {
        void save(GoodsAssignBean bean);
    }
}
