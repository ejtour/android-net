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
     * 角色 ID
     */
    private String roleID;

    @Generated(hash = 392085566)
    public UserBean(String authType, String accessToken, String email, String employeeID,
                    String employeeName, String groupID, String groupLogoUrl, String groupName,
                    List<String> roleCode, String roleNames, String roles, String selfOperated, String roleID) {
        this.authType = authType;
        this.accessToken = accessToken;
        this.email = email;
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.groupID = groupID;
        this.groupLogoUrl = groupLogoUrl;
        this.groupName = groupName;
        this.roleCode = roleCode;
        this.roleNames = roleNames;
        this.roles = roles;
        this.selfOperated = selfOperated;
        this.roleID = roleID;
    }

    @Generated(hash = 1203313951)
    public UserBean() {
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getGroupLogoUrl() {
        return groupLogoUrl;
    }

    public void setGroupLogoUrl(String groupLogoUrl) {
        this.groupLogoUrl = groupLogoUrl;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<String> getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(List<String> roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getSelfOperated() {
        return selfOperated;
    }

    public void setSelfOperated(String selfOperated) {
        this.selfOperated = selfOperated;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRoleID() {
        return this.roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }
}
