package com.hll_sc_app.base.bean;

import java.util.List;

/**
 * 供应商用户登录返回值
 *
 * @author zhuyingsong
 * @date 2019-6-4
 */
public class LoginResp {
    /**
     * token
     */
    private String accessToken;
    /**
     * 是否关联,0-未关联,1-已关联
     */
    private String relation;
    /**
     * 店铺信息列表
     */
    private List<UserShop> shops;
    /**
     * 用户信息
     */
    private UserBean user;

    /**
     * 是否绑定微信，1-绑定，0-未绑定
     */
    private int bindType;

    /**
     * 微信id
     */
    private String unionId;

    public int getBindType() {
        return bindType;
    }

    public void setBindType(int bindType) {
        this.bindType = bindType;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public List<UserShop> getShops() {
        return shops;
    }

    public void setShops(List<UserShop> shops) {
        this.shops = shops;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }
}
