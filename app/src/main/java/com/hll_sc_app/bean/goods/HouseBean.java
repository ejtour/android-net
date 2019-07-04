package com.hll_sc_app.bean.goods;

/**
 * 获取仓库下拉列表-100121
 *
 * @author zhuyingsong
 * @date 2019-07-02
 */
public class HouseBean {
    private String houseName;
    private String houseCode;
    private String id;
    private boolean select;

    public HouseBean() {
    }

    public HouseBean(String houseName, String id) {
        this.houseName = houseName;
        this.id = id;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
