package com.hll_sc_app.base.bean;

import com.hll_sc_app.base.greendao.StringConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;

/**
 * 登录接口返回的用户信息
 *
 * @author zhuyingsong
 * @date 20190604
 */
@Entity
public class UserBean {
    /**
     * 角色类型(逗号分隔)1.业务型(销售)2.管理型3.司机
     */
    private String authType;
    /**
     * 当前角色类型
     */
    private String curRole;
    /**
     * token
     */
    private String accessToken;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 员工ID
     */
    @Id
    private String employeeID;

    /**
     * 员工工号
     */
    private String employeeCode;
    /**
     * 员工名称
     */
    private String employeeName;
    /**
     * 集团ID
     */
    private String groupID;
    /**
     * 集团logo
     */
    private String groupLogoUrl;
    /**
     * 集团名称
     */
    private String groupName;
    /**
     * 角色编码
     */
    @Convert(columnType = String.class, converter = StringConverter.class)
    private List<String> roleCode;
    /**
     * 角色名称(逗号分隔)
     */
    private String roleNames;
    /**
     * 角色ID(逗号分隔)
     */
    private String roles;
    /**
     * 是否自营
     */
    private String selfOperated;
    /**
     * 登录手机号
     */
    private String loginPhone;
    /**
     * 账号类型(0-主账号，1-子账号)
     */
    private String accountType;

    /**
     *  0：未开通代仓 1:已开通代仓
     */
    private int wareHourseStatus;

    public int getWareHourseStatus() {
        return wareHourseStatus;
    }

    public void setWareHourseStatus(int wareHourseStatus) {
        this.wareHourseStatus = wareHourseStatus;
    }

    @Generated(hash = 1947047870)
    public UserBean(String authType, String curRole, String accessToken,
            String email, String employeeID, String employeeCode,
            String employeeName, String groupID, String groupLogoUrl,
            String groupName, List<String> roleCode, String roleNames, String roles,
            String selfOperated, String loginPhone, String accountType,
            int wareHourseStatus) {
        this.authType = authType;
        this.curRole = curRole;
        this.accessToken = accessToken;
        this.email = email;
        this.employeeID = employeeID;
        this.employeeCode = employeeCode;
        this.employeeName = employeeName;
        this.groupID = groupID;
        this.groupLogoUrl = groupLogoUrl;
        this.groupName = groupName;
        this.roleCode = roleCode;
        this.roleNames = roleNames;
        this.roles = roles;
        this.selfOperated = selfOperated;
        this.loginPhone = loginPhone;
        this.accountType = accountType;
        this.wareHourseStatus = wareHourseStatus;
    }

    @Generated(hash = 1203313951)
    public UserBean() {
    }
    public String getAuthType() {
        return this.authType;
    }
    public void setAuthType(String authType) {
        this.authType = authType;
    }
    public String getCurRole() {
        return this.curRole;
    }
    public void setCurRole(String curRole) {
        this.curRole = curRole;
    }
    public String getAccessToken() {
        return this.accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmployeeID() {
        return this.employeeID;
    }
    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }
    public String getEmployeeName() {
        return this.employeeName;
    }
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    public String getGroupID() {
        return this.groupID;
    }
    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }
    public String getGroupLogoUrl() {
        return this.groupLogoUrl;
    }
    public void setGroupLogoUrl(String groupLogoUrl) {
        this.groupLogoUrl = groupLogoUrl;
    }
    public String getGroupName() {
        return this.groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public List<String> getRoleCode() {
        return this.roleCode;
    }
    public void setRoleCode(List<String> roleCode) {
        this.roleCode = roleCode;
    }
    public String getRoleNames() {
        return this.roleNames;
    }
    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }
    public String getRoles() {
        return this.roles;
    }
    public void setRoles(String roles) {
        this.roles = roles;
    }
    public String getSelfOperated() {
        return this.selfOperated;
    }
    public void setSelfOperated(String selfOperated) {
        this.selfOperated = selfOperated;
    }
    public String getLoginPhone() {
        return this.loginPhone;
    }
    public void setLoginPhone(String loginPhone) {
        this.loginPhone = loginPhone;
    }
    public String getAccountType() {
        return this.accountType;
    }
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    public String getEmployeeCode() {
        return this.employeeCode;
    }
    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }
}
