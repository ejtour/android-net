package com.hll_sc_app.bean.staff;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * 岗位权限简介
 *
 * @author zhuyingsong
 * @date 2018/7/26
 */
public class WrapperAuthInfo extends SectionEntity<RolePermissionResp.AuthInfo> {
    public WrapperAuthInfo(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public WrapperAuthInfo(RolePermissionResp.AuthInfo authInfo) {
        super(authInfo);
    }
}
