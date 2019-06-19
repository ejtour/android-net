package com.hll_sc_app.bean.goods;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * 售卖单位
 *
 * @author zhuyingsong
 * @date 2019-06-19
 */
public class SaleUnitNameWrapper extends SectionEntity<SaleUnitNameBean> {

    public SaleUnitNameWrapper(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public SaleUnitNameWrapper(SaleUnitNameBean saleUnitNameBean) {
        super(saleUnitNameBean);
    }
}
