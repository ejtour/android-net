package com.hll_sc_app.bean.account;

/**
 * 得到验证码的请求参数
 *
 * @author zc
 */
public class GetIdentifyCodeReq {

    private int flag;
    private String loginPhone;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getLoginPhone() {

        return loginPhone;
    }

    public void setLoginPhone(String loginPhone) {
        this.loginPhone = loginPhone;
    }

    public enum FLAG {
        //
        NULL("空", 0),
        LOGIN("登录", 1),
        REGISTER("注册", 2),
        MAIN_ACCOUNT_UNBIND_ORIGIN_PHONE("主账号解绑验证原手机号", 3),
        MAIN_ACCOUNT_UNBIND_NEW_PHONE("主账号解绑验证新手机号", 4),
        SUB_ACCOUNT_UNBIND("子账号解绑", 5);

        private String name;
        private int index;

        private FLAG(String name, int index) {
            this.index = index;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}
