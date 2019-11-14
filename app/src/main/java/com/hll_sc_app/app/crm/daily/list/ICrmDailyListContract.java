package com.hll_sc_app.app.crm.daily.list;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.daily.DailyBean;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/13
 */

public interface ICrmDailyListContract {
    interface ICrmDailyListView extends ILoadView {
        void setData(SingleListResp<DailyBean> resp, boolean append);

        String getSearchWords();
    }

    interface ICrmDailyListPresenter extends IPresenter<ICrmDailyListView> {
        void refresh();

        void loadMore();
    }
}
