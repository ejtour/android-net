package com.hll_sc_app.bean.user;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 分类查询返回
 *
 * @author zhuyingsong
 * @date 2019-06-10
 */
public class CategoryResp {
    @SerializedName("1")
    private List<CategoryItem> list;
    @SerializedName("2")
    private List<CategoryItem> list2;
    @SerializedName("3")
    private List<CategoryItem> list3;

    public List<CategoryItem> getList() {
        return list;
    }

    public void setList(List<CategoryItem> list) {
        this.list = list;
    }

    public List<CategoryItem> getList2() {
        return list2;
    }

    public void setList2(List<CategoryItem> list2) {
        this.list2 = list2;
    }

    public List<CategoryItem> getList3() {
        return list3;
    }

    public void setList3(List<CategoryItem> list3) {
        this.list3 = list3;
    }
}
