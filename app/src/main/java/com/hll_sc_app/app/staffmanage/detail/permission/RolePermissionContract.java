package com.hll_sc_app.app.staffmanage.detail.permission;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.staff.RolePermissionResp;

import java.util.List;

/**
 * 岗位权限简介
 *
 * @author zhuyingsong
 * @date 2018/7/26
 */
public interface RolePermissionContract {

    interface IRolePermissionView extends ILoadView {
        /**
         * 展示列表
         *
         * @param list 列表数据
         */
        void showList(List<RolePermissionResp> list);
    }

    interface IRolePermissionPresenter extends IPresenter<IRolePermissionView> {
        /**
         * 获取员工岗位权限列表
         */
        void queryRolesPermission();
    }
}
