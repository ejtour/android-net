package com.hll_sc_app.bean.goods;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 查询自定义分类列表数据
 *
 * @author zhuyingsong
 * @date 2019-06-18
 */
public class CustomCategoryResp {
    @SerializedName("2")
    private List<CustomCategoryBean> list2;
    @SerializedName("3")
    private List<CustomCategoryBean> list3;

    public List<CustomCategoryBean> getList2() {
        return list2;
    }

    public void setList2(List<CustomCategoryBean> list2) {
        this.list2 = list2;
    }

    public List<CustomCategoryBean> getList3() {
        return list3;
    }

    public void setList3(List<CustomCategoryBean> list3) {
        this.list3 = list3;
    }
}
