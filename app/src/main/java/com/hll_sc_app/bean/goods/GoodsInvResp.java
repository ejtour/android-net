package com.hll_sc_app.bean.goods;

import java.util.List;

/**
 * 获取商品库存列表
 *
 * @author zhuyingsong
 * @date 2019-07-02
 */
public class GoodsInvResp {
    private int totalSize;
    private List<GoodsBean> list;

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<GoodsBean> getList() {
        return list;
    }

    public void setList(List<GoodsBean> list) {
        this.list = list;
    }
}
