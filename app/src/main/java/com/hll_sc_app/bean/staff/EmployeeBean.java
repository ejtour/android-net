package com.hll_sc_app.bean.staff;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 供应商员工
 *
 * @author zhuyingsong
 * @date 2019-07-18
 */
public class EmployeeBean implements Parcelable {
    public static final Parcelable.Creator<EmployeeBean> CREATOR = new Parcelable.Creator<EmployeeBean>() {
        @Override
        public EmployeeBean createFromParcel(Parcel source) {
            return new EmployeeBean(source);
        }

        @Override
        public EmployeeBean[] newArray(int size) {
            return new EmployeeBean[size];
        }
    };
    private String actionTime;
    private String loginPWD;
    private String purchaserOemToken;
    private String roleID;
    private String employeeID;
    private String adminCodeConfirmed;
    private String employeeImg;
    private String employeeCode;
    private String createby;
    private String loginTime;
    private String loginName;
    private String action;
    private String email;
    private String employeeName;
    private String actionBy;
    private String supplierToken;
    private String accountType;
    private String groupID;
    private String loginStatus;
    private String recommendCode;
    private String purchaserWebToken;
    private String supplierDeviceId;
    private String token;
    private String employeeType;
    private String recommendQRCode;
    private String codeConfirmed;
    private String createTime;
    private String loginPhone;
    private String purchaserToken;
    private String resourceType;
    private boolean select;
    private List<RolesBean> roles;
    private String employeeNum;

    public EmployeeBean() {
    }

    protected EmployeeBean(Parcel in) {
        this.actionTime = in.readString();
        this.loginPWD = in.readString();
        this.purchaserOemToken = in.readString();
        this.roleID = in.readString();
        this.employeeID = in.readString();
        this.adminCodeConfirmed = in.readString();
        this.employeeImg = in.readString();
        this.employeeCode = in.readString();
        this.createby = in.readString();
        this.loginTime = in.readString();
        this.loginName = in.readString();
        this.action = in.readString();
        this.email = in.readString();
        this.employeeName = in.readString();
        this.actionBy = in.readString();
        this.supplierToken = in.readString();
        this.accountType = in.readString();
        this.groupID = in.readString();
        this.loginStatus = in.readString();
        this.recommendCode = in.readString();
        this.purchaserWebToken = in.readString();
        this.supplierDeviceId = in.readString();
        this.token = in.readString();
        this.employeeType = in.readString();
        this.recommendQRCode = in.readString();
        this.codeConfirmed = in.readString();
        this.createTime = in.readString();
        this.loginPhone = in.readString();
        this.purchaserToken = in.readString();
        this.resourceType = in.readString();
        this.select = in.readByte() != 0;
        this.roles = new ArrayList<RolesBean>();
        in.readList(this.roles, RolesBean.class.getClassLoader());
        this.employeeNum = in.readString();
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getLoginPWD() {
        return loginPWD;
    }

    public void setLoginPWD(String loginPWD) {
        this.loginPWD = loginPWD;
    }

    public String getPurchaserOemToken() {
        return purchaserOemToken;
    }

    public void setPurchaserOemToken(String purchaserOemToken) {
        this.purchaserOemToken = purchaserOemToken;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getAdminCodeConfirmed() {
        return adminCodeConfirmed;
    }

    public void setAdminCodeConfirmed(String adminCodeConfirmed) {
        this.adminCodeConfirmed = adminCodeConfirmed;
    }

    public String getEmployeeImg() {
        return employeeImg;
    }

    public void setEmployeeImg(String employeeImg) {
        this.employeeImg = employeeImg;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public String getSupplierToken() {
        return supplierToken;
    }

    public void setSupplierToken(String supplierToken) {
        this.supplierToken = supplierToken;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getRecommendCode() {
        return recommendCode;
    }

    public void setRecommendCode(String recommendCode) {
        this.recommendCode = recommendCode;
    }

    public String getPurchaserWebToken() {
        return purchaserWebToken;
    }

    public void setPurchaserWebToken(String purchaserWebToken) {
        this.purchaserWebToken = purchaserWebToken;
    }

    public String getSupplierDeviceId() {
        return supplierDeviceId;
    }

    public void setSupplierDeviceId(String supplierDeviceId) {
        this.supplierDeviceId = supplierDeviceId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getRecommendQRCode() {
        return recommendQRCode;
    }

    public void setRecommendQRCode(String recommendQRCode) {
        this.recommendQRCode = recommendQRCode;
    }

    public String getCodeConfirmed() {
        return codeConfirmed;
    }

    public void setCodeConfirmed(String codeConfirmed) {
        this.codeConfirmed = codeConfirmed;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLoginPhone() {
        return loginPhone;
    }

    public void setLoginPhone(String loginPhone) {
        this.loginPhone = loginPhone;
    }

    public String getPurchaserToken() {
        return purchaserToken;
    }

    public void setPurchaserToken(String purchaserToken) {
        this.purchaserToken = purchaserToken;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public List<RolesBean> getRoles() {
        return roles;
    }

    public void setRoles(List<RolesBean> roles) {
        this.roles = roles;
    }

    public String getEmployeeNum() {
        return employeeNum;
    }

    public void setEmployeeNum(String employeeNum) {
        this.employeeNum = employeeNum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.actionTime);
        dest.writeString(this.loginPWD);
        dest.writeString(this.purchaserOemToken);
        dest.writeString(this.roleID);
        dest.writeString(this.employeeID);
        dest.writeString(this.adminCodeConfirmed);
        dest.writeString(this.employeeImg);
        dest.writeString(this.employeeCode);
        dest.writeString(this.createby);
        dest.writeString(this.loginTime);
        dest.writeString(this.loginName);
        dest.writeString(this.action);
        dest.writeString(this.email);
        dest.writeString(this.employeeName);
        dest.writeString(this.actionBy);
        dest.writeString(this.supplierToken);
        dest.writeString(this.accountType);
        dest.writeString(this.groupID);
        dest.writeString(this.loginStatus);
        dest.writeString(this.recommendCode);
        dest.writeString(this.purchaserWebToken);
        dest.writeString(this.supplierDeviceId);
        dest.writeString(this.token);
        dest.writeString(this.employeeType);
        dest.writeString(this.recommendQRCode);
        dest.writeString(this.codeConfirmed);
        dest.writeString(this.createTime);
        dest.writeString(this.loginPhone);
        dest.writeString(this.purchaserToken);
        dest.writeString(this.resourceType);
        dest.writeByte(this.select ? (byte) 1 : (byte) 0);
        dest.writeList(this.roles);
        dest.writeString(this.employeeNum);
    }

    public static class RolesBean implements Parcelable {
        public static final Creator<RolesBean> CREATOR = new Creator<RolesBean>() {
            @Override
            public RolesBean createFromParcel(Parcel source) {
                return new RolesBean(source);
            }

            @Override
            public RolesBean[] newArray(int size) {
                return new RolesBean[size];
            }
        };
        /**
         * roleID : 45
         * roleName : 超级管理员
         */
        private String roleID;
        private String roleName;

        public RolesBean() {
        }

        protected RolesBean(Parcel in) {
            this.roleID = in.readString();
            this.roleName = in.readString();
        }

        public String getRoleID() {
            return roleID;
        }

        public void setRoleID(String roleID) {
            this.roleID = roleID;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.roleID);
            dest.writeString(this.roleName);
        }
    }
}
