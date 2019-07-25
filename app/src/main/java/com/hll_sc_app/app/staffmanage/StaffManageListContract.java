package com.hll_sc_app.app.staffmanage;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.staff.EmployeeBean;

import java.util.List;

/**
 * 员工列表
 *
 * @author zhuyingsong
 * @date 2019/7/25
 */
public interface StaffManageListContract {

    interface IStaffListView extends ILoadView {
        /**
         * 获取检索字段
         *
         * @return 搜索词
         */
        String getSearchParam();

        /**
         * 展示员工列表
         *
         * @param list   list
         * @param append true-追加
         */
        void showStaffList(List<EmployeeBean> list, boolean append);

        /**
         * 显示员工数量
         *
         * @param num 员工数量
         */
        void showStaffNum(String num);
    }

    interface IStaffListPresenter extends IPresenter<IStaffListView> {
        /**
         * 查询员工数量
         */
        void queryStaffNum();

        /**
         * 查询员工列表
         *
         * @param showLoading true-显示 loading
         */
        void queryStaffList(boolean showLoading);

        /**
         * 查询下一页员工列表
         */
        void queryMoreStaffList();

        /**
         * 删除员工
         *
         * @param employeeId 员工ID
         */
        void delStaff(String employeeId);
    }
}
