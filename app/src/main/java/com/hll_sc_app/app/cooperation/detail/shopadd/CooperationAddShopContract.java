package com.hll_sc_app.app.cooperation.detail.shopadd;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;

import java.util.List;

/**
 * 合作采购商详情- 新增门店
 *
 * @author zhuyingsong
 * @date 2019/7/17
 */
public interface CooperationAddShopContract {

    interface ICooperationAddShopView extends ILoadView {
        /**
         * 展示合作采购商门店列表
         *
         * @param list   list
         * @param append 追加
         */
        void showPurchaserShopList(List<PurchaserShopBean> list, boolean append);

        /**
         * 获取搜索词
         *
         * @return 搜索词
         */
        String getSearchParam();

        /**
         * 获取采购商 Id
         *
         * @return 采购商 Id
         */
        String getPurchaserId();
    }

    interface ICooperationAddShopPresenter extends IPresenter<ICooperationAddShopView> {
        /**
         * 查询合作采购商列表
         *
         * @param show 是否显示 loading
         */
        void queryPurchaserShopList(boolean show);

        /*
         *加载下一页
         */
        void queryMorePurchaserShopList();
    }
}
