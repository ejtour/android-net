package com.hll_sc_app.app.staffmanage.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.staff.EmployeeBean;

/**
 * 员工列表-编辑员工
 *
 * @author zhuyingsong
 * @date 2018/7/25
 */
public interface StaffManagerEditContract {

    interface IStaffManageEditView extends ILoadView {
        /**
         * 更新、保存员工信息成功
         */
        void updateSuccess();
    }

    interface IStaffManagerPresenter extends IPresenter<IStaffManageEditView> {
        /**
         * 更新员工信息
         *
         * @param resp 参数
         */
        void editStaff(EmployeeBean resp);

        /**
         * 新增员工信息
         *
         * @param resp 参数
         */
        void addStaff(EmployeeBean resp);
    }
}
