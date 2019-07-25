package com.hll_sc_app.app.cooperation.application.platform;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.PurchaserBean;

import java.util.List;

/**
 * 合作采购商-我收到的申请-平台申请
 *
 * @author zhuyingsong
 * @date 2019/7/24
 */
interface CooperationPlatformContract {

    interface ICooperationPlatformView extends ILoadView {
        /**
         * 展示列表
         *
         * @param list   列表数据源
         * @param append true-追加
         */
        void showCooperationPlatformList(List<PurchaserBean> list, boolean append);

        /**
         * 获取搜索词
         *
         * @return 搜索词
         */
        String getSearchParam();
    }

    interface ICooperationPlatformPresenter extends IPresenter<ICooperationPlatformView> {
        /**
         * 加载平台申请数据列表
         *
         * @param showLoading true-显示加载
         */
        void queryCooperationPlatformList(boolean showLoading);

        /**
         * 加载更多平台申请数据列表
         */
        void queryMoreCooperationPlatformList();
    }
}
