package com.hll_sc_app.app.cooperation.invite;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserResp;

/**
 * 合作采购商-我发出的申请
 *
 * @author zhuyingsong
 * @date 2019/7/23
 */
public interface CooperationInviteContract {

    interface ICooperationInviteView extends ILoadView {
        /**
         * 采购商集团名称检索字段
         *
         * @return 搜索词
         */
        String getSearchParam();

        /**
         * 展示采购商集团列表
         *
         * @param resp   list
         * @param append true-追加
         */
        void showPurchaserList(CooperationPurchaserResp resp, boolean append);
    }

    interface ICooperationInvitePresenter extends IPresenter<ICooperationInviteView> {
        /**
         * 查询商品关联的采购商列表
         *
         * @param showLoading true-显示 loading
         */
        void queryPurchaserList(boolean showLoading);

        /**
         * 查询下一页商品关联的采购商列表
         */
        void queryMorePurchaserList();
    }
}
