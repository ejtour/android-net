package com.hll_sc_app.app.crm.customer.search;

import android.os.Parcelable;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/27
 */

public interface ICustomerSearchContract {
    interface ICustomerSearchView extends ILoadView {
        void setData(List<? extends Parcelable> list, boolean append);

        String getSearchWords();

        /**
         * @return 0-意向客户 1-合作门店 2-合作集团
         */
        int getType();
    }

    interface ICustomerSearchPresenter extends IPresenter<ICustomerSearchView> {
        void refresh();

        void loadMore();
    }
}
