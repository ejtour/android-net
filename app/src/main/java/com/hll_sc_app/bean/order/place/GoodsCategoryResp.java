package com.hll_sc_app.bean.order.place;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/16
 */

public class GoodsCategoryResp {
    @SerializedName("1")
    private List<GoodsCategoryBean> list1;
    @SerializedName("2")
    private List<GoodsCategoryBean> list2;
    @SerializedName("3")
    private List<GoodsCategoryBean> list3;

    public List<GoodsCategoryBean> getList1() {
        return list1;
    }

    public void setList1(List<GoodsCategoryBean> list1) {
        this.list1 = list1;
    }

    public List<GoodsCategoryBean> getList2() {
        return list2;
    }

    public void setList2(List<GoodsCategoryBean> list2) {
        this.list2 = list2;
    }

    public List<GoodsCategoryBean> getList3() {
        return list3;
    }

    public void setList3(List<GoodsCategoryBean> list3) {
        this.list3 = list3;
    }
}
