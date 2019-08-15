package com.hll_sc_app.app.marketingsetting.selectarea;

import com.hll_sc_app.base.bean.AreaBean;

import java.util.List;

public interface ISelectAreaConfig {

    /**
     * 页面数据
     *
     * @param object
     * @return
     */
    List<AreaBean> getAreaList(Object object);

    /**
     * 获取选择的地区数据
     */
    default void getSelectedAreas(List<AreaBean> areaBeans) {

    }

    /**
     * 是否能勾选
     *
     * @return
     */
    default boolean isCheck() {
        return true;
    }


}
