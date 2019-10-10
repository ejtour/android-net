package com.hll_sc_app.app.cooperation;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserResp;

/**
 * 合作采购商列表
 *
 * @author zhuyingsong
 * @date 2019/7/16
 */
public interface CooperationPurchaserContract {

    interface IGoodsRelevancePurchaserView extends ILoadView {
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

        /**
         * 导出成功
         *
         * @param email 邮箱地址
         */
        void exportSuccess(String email);

        /**
         * 导出失败
         *
         * @param tip 失败提示
         */
        void exportFailure(String tip);

        /**
         * 绑定邮箱
         */
        void bindEmail();


        /**
         * 查询 未解除 解除的合作采购商
         * @return
         */
        int getCooperationActive();
    }

    interface IGoodsRelevancePurchaserPresenter extends IPresenter<IGoodsRelevancePurchaserView> {
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

        /**
         * 删除合作餐企
         *
         * @param purchaserId 采购商集团ID
         */
        void delCooperationPurchaser(String purchaserId);

        /**
         * 导出合作采购商
         *
         * @param email 邮箱
         */
        void exportPurchaser(String email);
    }
}
