package com.hll_sc_app.bean.goods;

import java.util.List;

/**
 * 商品库批量新增商品
 *
 * @author zhuyingsong
 * @date 2019-06-29
 */
public class GoodsAddBatchResp {
    /**
     * 成功条数
     */
    private String successTotal;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 失败列表或校验不通过的
     */
    private List<GoodsBean> failRecords;

    public String getSuccessTotal() {
        return successTotal;
    }

    public void setSuccessTotal(String successTotal) {
        this.successTotal = successTotal;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<GoodsBean> getFailRecords() {
        return failRecords;
    }

    public void setFailRecords(List<GoodsBean> failRecords) {
        this.failRecords = failRecords;
    }
}
