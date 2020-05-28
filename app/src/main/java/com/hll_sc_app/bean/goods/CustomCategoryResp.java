package com.hll_sc_app.bean.goods;

import com.google.gson.annotations.SerializedName;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.List;

/**
 * 查询自定义分类列表数据
 *
 * @author zhuyingsong
 * @date 2019-06-18
 */
public class CustomCategoryResp {
    @SerializedName("1")
    private List<CustomCategoryBean> list1;
    @SerializedName("2")
    private List<CustomCategoryBean> list2;
    @SerializedName("3")
    private List<CustomCategoryBean> list3;

    public List<CustomCategoryBean> getList1() {
        return list1;
    }

    public void setList1(List<CustomCategoryBean> list1) {
        this.list1 = list1;
    }

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

    public void processList() {
        if (CommonUtils.isEmpty(list2)) return;
        if (!CommonUtils.isEmpty(list3)) {
            for (CustomCategoryBean bean : list3) {
                for (CustomCategoryBean categoryBean : list2) {
                    if (bean.getShopCategoryPID().equals(categoryBean.getId())) {
                        categoryBean.getSubList().add(bean);
                        break;
                    }
                }
            }
        }
    }
}
