package com.hll_sc_app.bean.staff;

import java.util.List;

/**
 * 员工岗位权限
 *
 * @author zhuyingsong
 * @date 2019/1/28
 */
public class RolePermissionResp {
    private RoleBean role;
    private List<AuthInfo> auths;
    private boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public RoleBean getRole() {
        return role;
    }

    public void setRole(RoleBean role) {
        this.role = role;
    }

    public List<AuthInfo> getAuths() {
        return auths;
    }

    public void setAuths(List<AuthInfo> auths) {
        this.auths = auths;
    }

    public static class AuthInfo {
        private String interfaceType;
        private String authCode;
        private String authName;
        private String authType;
        private String authDesc;
        private String authID;
        private String parentID;
        private List<AuthInfo> subAuth;

        public String getInterfaceType() {
            return interfaceType;
        }

        public void setInterfaceType(String interfaceType) {
            this.interfaceType = interfaceType;
        }

        public String getAuthCode() {
            return authCode;
        }

        public void setAuthCode(String authCode) {
            this.authCode = authCode;
        }

        public String getAuthName() {
            return authName;
        }

        public void setAuthName(String authName) {
            this.authName = authName;
        }

        public String getAuthType() {
            return authType;
        }

        public void setAuthType(String authType) {
            this.authType = authType;
        }

        public String getAuthDesc() {
            return authDesc;
        }

        public void setAuthDesc(String authDesc) {
            this.authDesc = authDesc;
        }

        public String getAuthID() {
            return authID;
        }

        public void setAuthID(String authID) {
            this.authID = authID;
        }

        public String getParentID() {
            return parentID;
        }

        public void setParentID(String parentID) {
            this.parentID = parentID;
        }

        public List<AuthInfo> getSubAuth() {
            return subAuth;
        }

        public void setSubAuth(List<AuthInfo> subAuth) {
            this.subAuth = subAuth;
        }
    }
}
