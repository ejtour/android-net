package com.hll_sc_app.app.staffmanage.detail;

import com.hll_sc_app.bean.staff.EmployeeBean;
import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * 员工列表-编辑员工
 *
 * @author zhuyingsong
 * @date 2018/7/25
 */
public class StaffManagerEditPresenter implements StaffManagerEditContract.IStaffManagerPresenter {
    private StaffManagerEditContract.IStaffManageEditView mView;

    static StaffManagerEditPresenter newInstance() {
        return new StaffManagerEditPresenter();
    }

    @Override
    public void start() {
        // no-op
    }

    @Override
    public void register(StaffManagerEditContract.IStaffManageEditView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void editStaff(EmployeeBean resp) {

    }

    @Override
    public void addStaff(EmployeeBean resp) {

    }
}
