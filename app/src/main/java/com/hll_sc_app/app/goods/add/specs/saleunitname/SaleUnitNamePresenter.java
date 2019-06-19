package com.hll_sc_app.app.goods.add.specs.saleunitname;

import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * 选择售卖单位
 *
 * @author zhuyingsong
 * @date 2019/6/19
 */
public class SaleUnitNamePresenter implements SaleUnitNameContract.ISaleUnitNameAddPresenter {
    private SaleUnitNameContract.ISaleUnitNameAddView mView;

    static SaleUnitNamePresenter newInstance() {
        return new SaleUnitNamePresenter();
    }

    @Override
    public void start() {
        querySaleUnitName();
    }

    @Override
    public void register(SaleUnitNameContract.ISaleUnitNameAddView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void querySaleUnitName() {

    }
}
