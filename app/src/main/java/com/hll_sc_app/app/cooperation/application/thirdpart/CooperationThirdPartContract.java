package com.hll_sc_app.app.cooperation.application.thirdpart;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.cooperation.ThirdPartyPurchaserBean;

import java.util.List;

/**
 * 合作采购商-我收到的申请-第三方申请
 *
 * @author zhuyingsong
 * @date 2019/7/24
 */
interface CooperationThirdPartContract {

    interface ICooperationThirdPartView extends ILoadView {
        /**
         * 展示列表
         *
         * @param list   列表数据源
         * @param append true-追加
         * @param total  总量
         */
        void showCooperationThirdPartList(List<ThirdPartyPurchaserBean> list, boolean append, int total);

        /**
         * 获取搜索词
         *
         * @return 搜索词
         */
        String getSearchParam();
    }

    interface ICooperationThirdPartPresenter extends IPresenter<ICooperationThirdPartView> {
        /**
         * 加载第三方申请数据列表
         *
         * @param showLoading true-显示加载
         */
        void queryCooperationThirdPartList(boolean showLoading);

        /**
         * 加载更多第三方申请数据列表
         */
        void queryMoreThirdPartList();
    }
}
