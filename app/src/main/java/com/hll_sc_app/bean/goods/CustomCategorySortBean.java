package com.hll_sc_app.bean.goods;

/**
 * 自定义分类排序
 *
 * @author zhuyingsong
 * @date 2019-06-19
 */
public class CustomCategorySortBean {
    /**
     * 分类ID
     */
    private String id;
    /**
     * 排序字段
     */
    private String sort;

    public CustomCategorySortBean(String id, String sort) {
        this.id = id;
        this.sort = sort;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
