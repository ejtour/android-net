package com.hll_sc_app.app.wallet.bank;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.wallet.BankBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/2
 */

public interface IBankListContract {
    interface IBankListView extends ILoadView {
        void setBankList(List<BankBean> list, boolean append);
    }

    interface IBankListPresenter extends IPresenter<IBankListView> {
        void refresh();

        void loadMore();
    }
}
