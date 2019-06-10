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

    public List<CategoryItem> getList() {
        return list;
    }

    public void setList(List<CategoryItem> list) {
        this.list = list;
    }
}
