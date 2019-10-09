package com.hll_sc_app.base.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class AccountBean {
    @Id
    private String account;

    private String password;
    private long time;

    @Generated(hash = 130114151)
    public AccountBean(String account, String password, long time) {
        this.account = account;
        this.password = password;
        this.time = time;
    }

    @Generated(hash = 1267506976)
    public AccountBean() {
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}