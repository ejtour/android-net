package com.hll_sc_app.app.rank.org;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.rank.OrgRankBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/10
 */

public interface IOrgRankContract {
    interface IOrgRankView extends ILoadView {
        void setData(List<OrgRankBean> list, boolean append);
    }

    interface IOrgRankPresenter extends IPresenter<IOrgRankView> {
        void refresh();

        void loadMore();
    }
}
