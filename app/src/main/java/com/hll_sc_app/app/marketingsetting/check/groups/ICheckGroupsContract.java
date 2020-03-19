package com.hll_sc_app.app.marketingsetting.check.groups;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

import java.util.ArrayList;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/3/4
 */

public interface ICheckGroupsContract {
    interface ICheckGroupsView extends ILoadView {
        void handleData(ArrayList<String> shopNameList);
    }

    interface ICheckGroupsPresenter extends IPresenter<ICheckGroupsView> {
        void reqShopList(String id);
    }
}
