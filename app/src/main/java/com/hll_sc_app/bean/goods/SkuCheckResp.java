package com.hll_sc_app.bean.goods;

/**
 * 商品sku条码校验
 *
 * @author zhuyingsong
 * @date 2019-06-20
 */
public class SkuCheckResp {
    private boolean flag;
    private String message;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
