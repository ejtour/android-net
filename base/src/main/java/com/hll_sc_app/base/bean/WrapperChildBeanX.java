package com.hll_sc_app.base.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * 地区包装类
 *
 * @author zhuyingsong
 * @date 2019-06-25
 */
public class WrapperChildBeanX extends SectionEntity<AreaBean.ChildBeanX> {
    public WrapperChildBeanX(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public WrapperChildBeanX(AreaBean.ChildBeanX childBeanX) {
        super(childBeanX);
    }
}
