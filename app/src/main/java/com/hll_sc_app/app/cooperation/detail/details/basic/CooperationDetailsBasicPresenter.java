package com.hll_sc_app.app.cooperation.detail.details.basic;

import com.hll_sc_app.citymall.util.CommonUtils;


/**
 * 合作采购商详情-详细资料-基本信息
 *
 * @author zhuyingsong
 * @date 2019/7/19
 */
public class CooperationDetailsBasicPresenter implements CooperationDetailsBasicContract.IGoodsRelevanceListPresenter {
    private CooperationDetailsBasicContract.IGoodsRelevanceListView mView;

    static CooperationDetailsBasicPresenter newInstance() {
        return new CooperationDetailsBasicPresenter();
    }

    @Override
    public void register(CooperationDetailsBasicContract.IGoodsRelevanceListView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }
}
