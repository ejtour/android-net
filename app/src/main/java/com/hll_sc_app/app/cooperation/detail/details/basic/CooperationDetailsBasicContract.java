package com.hll_sc_app.app.cooperation.detail.details.basic;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;


/**
 * 合作采购商详情-详细资料-基本信息
 *
 * @author zhuyingsong
 * @date 2019/7/19
 */
public interface CooperationDetailsBasicContract {

    interface IGoodsRelevanceListView extends ILoadView {

    }

    interface IGoodsRelevanceListPresenter extends IPresenter<IGoodsRelevanceListView> {
    }
}
