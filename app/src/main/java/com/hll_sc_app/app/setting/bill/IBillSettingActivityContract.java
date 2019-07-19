package com.hll_sc_app.app.setting.bill;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.user.GroupParame;
import com.kyleduo.switchbutton.SwitchButton;

import java.util.List;

/**
 * @author baitianqi baitianqi@hualala.com
 *
 * version 1.0.0
 * Copyright (C) 2017-2018 hualala
 *           This program is protected by copyright laws.
 *           Program Name:哗啦啦商城
 *
 * Description:
 *
 * CreateTime: 2019/7/12 18:11
 *
 * Change History:
 *
 *        Date             CR Number              Name              Description of change
 *
 */

public interface IBillSettingActivityContract {

    interface IBillSettingView extends ILoadView {
        /**
         * Description: 显示订单设置
         *
         * @author baitianqi
         *
         * @CreateTime:
         *
         * @param
         */
        void showBillSetting(List<GroupParame> groupParameList);

        /**
         * Description: 开关状态变更
         *
         * @author baitianqi
         *
         * @CreateTime:
         *
         * @param
         */
        void toggleBillSettingStatus(boolean isChecked, Integer type);

        /**
         * Description: 恢复状态
         *
         * @author baitianqi
         *
         * @CreateTime:
         *
         * @param
         */
        void returnCheckStatus(boolean oldChecked, Integer type);

        /**
         * Description:切换时 显示提示窗口
         *
         * @author baitianqi
         *
         * @CreateTime:
         *
         * @param
         */
        void showAlertDialog(boolean isChecked, Integer type);
    }

    interface IBillSettingPresentr extends IPresenter<IBillSettingView> {

        /**
         * Description:获取集团参数
         *
         * @author baitianqi
         *
         * @CreateTime:
         *
         * @param
         */
        void getBillSetting(String types);

        /**
         * Description: 修改集团参数
         *
         * @author baitianqi
         *
         * @CreateTime:
         *
         * @param
         */
        void changeBillSetting(boolean isChecked, Integer type);
    }
}
