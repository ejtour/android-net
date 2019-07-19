package com.hll_sc_app.app.setting.cooperation;

import com.hll_sc_app.app.setting.bill.IBillSettingActivityContract;
import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.user.GroupParame;

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
 * CreateTime: 2019/7/17 11:00
 *
 * Change History:
 *
 *        Date             CR Number              Name              Description of change
 * 
 */
public interface ICooperationSettingActivityContract {

    interface ICooperationSettingView extends ILoadView {
        /**
         * Description: 显示订单设置
         *
         * @author baitianqi
         *
         * @CreateTime:
         *
         * @param
         */
        void showCooperationSetting(List<GroupParame> groupParameList);

        /**
         * Description: 开关状态变更
         *
         * @author baitianqi
         *
         * @CreateTime:
         *
         * @param
         */
        void toggleCooperationSettingStatus(boolean isChecked, Integer type);

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

    interface ICooperationSettingPresentr extends IPresenter<ICooperationSettingActivityContract.ICooperationSettingView> {

        /**
         * Description:获取集团参数
         *
         * @author baitianqi
         *
         * @CreateTime:
         *
         * @param
         */
        void getCooperationSetting(String types);

        /**
         * Description: 修改集团参数
         *
         * @author baitianqi
         *
         * @CreateTime:
         *
         * @param
         */
        void changeCooperationSetting(boolean isChecked, Integer type);
    }
}
