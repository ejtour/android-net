package com.hll_sc_app.bean.goods;

import java.util.List;

/**
 * 自定义分类排序
 *
 * @author zhuyingsong
 * @date 2019-06-19
 */
public class CustomCategorySortReq {
    /**
     * 分类
     */
    private List<CustomCategorySortBean> categorys;
    /**
     * 集团编号
     */
    private String groupID;

    public List<CustomCategorySortBean> getCategorys() {
        return categorys;
    }

    public void setCategorys(List<CustomCategorySortBean> categorys) {
        this.categorys = categorys;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }
}
