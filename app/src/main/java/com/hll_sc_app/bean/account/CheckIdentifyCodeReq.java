package com.hll_sc_app.bean.account;

/**
 * 校验验证码的请求
 * 用在更改集团绑定手机号的操作中
 *
 * @author zc
 */
public class CheckIdentifyCodeReq {

    private String loginPhone;
    private String checkCode;
    private int flag;

    public String getLoginPhone() {
        return loginPhone;
    }

    public void setLoginPhone(String loginPhone) {
        this.loginPhone = loginPhone;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public enum FLAG {
        //
        NULL("空",0),
        STEP_ONE("主账号修改绑定原账号校验",1),
        STEP_TWO("主账号修改绑定新账号校验",2),
        STEP_THREE("子账号解绑校验",3),;

        private String name;
        private int index;

        FLAG(String name, int index) {
            this.name = name;
            this.index = index;
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
