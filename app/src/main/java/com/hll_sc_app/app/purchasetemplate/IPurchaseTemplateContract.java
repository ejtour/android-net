package com.hll_sc_app.app.purchasetemplate;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.user.PurchaseTemplateBean;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.impl.IExportView;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/4
 */

public interface IPurchaseTemplateContract {
    interface IPurchaseTemplateView extends IExportView {
        void setOrgData(int type, List<NameValue> list);

        void setData(List<PurchaseTemplateBean> list, boolean append);

        String getPurchaserID();

        String getShopID();

        String getPurchaserWords();

        String getShopWords();
    }

    interface IPurchaseTemplatePresenter extends IPresenter<IPurchaseTemplateView> {
        void searchPurchaser();

        void searchShop();

        void queryList();

        void refresh();

        void loadMore();

        void export(String email);
    }
}
