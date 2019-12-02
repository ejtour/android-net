package com.hll_sc_app.base.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/2
 */
@Entity
public class AuthBean {
    /**
     * 权限编码
     */
    private String authCode;
    /**
     * 权限ID
     */
    @Id
    private String authID;
    /**
     * 权限名称
     */
    private String authName;

    /**
     * 1-菜单2-功能
     */
    private String authType;
    private String interfaceType;

    /**
     * 父节点
     */
    private String parentID;

    @Generated(hash = 2080490359)
    public AuthBean(String authCode, String authID, String authName,
                    String authType, String interfaceType, String parentID) {
        this.authCode = authCode;
        this.authID = authID;
        this.authName = authName;
        this.authType = authType;
        this.interfaceType = interfaceType;
        this.parentID = parentID;
    }

    @Generated(hash = 367698588)
    public AuthBean() {
    }

    public String getAuthCode() {
        return this.authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getAuthID() {
        return this.authID;
    }

    public void setAuthID(String authID) {
        this.authID = authID;
    }

    public String getAuthName() {
        return this.authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getAuthType() {
        return this.authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getInterfaceType() {
        return this.interfaceType;
    }

    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType;
    }

    public String getParentID() {
        return this.parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }
}
