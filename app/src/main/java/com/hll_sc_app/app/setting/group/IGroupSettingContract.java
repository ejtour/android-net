package com.hll_sc_app.app.setting.group;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.user.GroupParamBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/3/12
 */

interface IGroupSettingContract {
    interface IGroupSettingView extends ILoadView {
        void setData(List<GroupParamBean> list);

        void success(boolean isChecked);

        void fallbackStatus();

        String getTypes();
    }

    interface IGroupSettingPresenter extends IPresenter<IGroupSettingView> {
        void toggle(int type, boolean isChecked);
    }
}
