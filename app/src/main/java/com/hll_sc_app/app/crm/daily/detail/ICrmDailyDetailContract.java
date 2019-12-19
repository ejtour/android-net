package com.hll_sc_app.app.crm.daily.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.daily.DailyBean;
import com.hll_sc_app.bean.daily.DailyReplyBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/13
 */

public interface ICrmDailyDetailContract {
    interface ICrmDailyDetailView extends ILoadView {
        void setData(DailyBean data);

        void setData(List<DailyReplyBean> list, boolean append);

        void success();

        boolean isSend();

        String getReplyID();

        String getID();
    }

    interface ICrmDailyDetailPresenter extends IPresenter<ICrmDailyDetailView> {
        void send(String msg);

        void refresh();

        void loadMore();
    }
}
