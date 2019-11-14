package com.hll_sc_app.app.crm.daily;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.daily.DailyBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/13
 */

public interface ICrmDailyContract {
    interface ICrmDailyView extends ILoadView {
        void setData(List<DailyBean> list, boolean append);
    }

    interface ICrmDailyPresenter extends IPresenter<ICrmDailyView> {
        void loadMore();
    }
}
