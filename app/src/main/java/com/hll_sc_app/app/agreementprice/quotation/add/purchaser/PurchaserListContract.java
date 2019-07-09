package com.hll_sc_app.app.agreementprice.quotation.add.purchaser;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationBean;

import java.util.List;

/**
 * 选择合作采购商
 *
 * @author zhuyingsong
 * @date 2019/7/9
 */
public interface PurchaserListContract {

    interface IPurchaserListView extends ILoadView {
        /**
         * 展示合作采购商列表
         *
         * @param list   list
         * @param append 追加
         * @param total  总个数
         */
        void showPurchaserList(List<QuotationBean> list, boolean append, int total);

        /**
         * 获取搜索词
         *
         * @return 搜索词
         */
        String getSearchParam();
    }

    interface IPurchaserListPresenter extends IPresenter<IPurchaserListView> {
        /**
         * 查询合作采购商列表
         */
        void queryCooperationPurchaserList();

        /**
         * 查询代仓合作集团
         *
         * @param showLoading 展示 loading
         */
        void queryCooperationGroupList(boolean showLoading);

        /**
         * 查询更多代仓合作集团
         */
        void queryMoreCooperationGroupList();
    }
}
