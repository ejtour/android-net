package com.hll_sc_app.app.complainmanage.add;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.complain.DropMenuBean;

import java.util.List;

public interface IComplainMangeAddContract {
    interface IView extends ILoadView {
        void queryMenuSuccess(List<DropMenuBean> dropMenuBeans);
    }

    interface IPresent extends IPresenter<IView> {
        void queryDropMenus();
    }


}
