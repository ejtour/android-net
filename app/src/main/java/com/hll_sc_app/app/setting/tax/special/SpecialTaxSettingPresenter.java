package com.hll_sc_app.app.setting.tax.special;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.user.SpecialTaxBean;
import com.hll_sc_app.bean.user.SpecialTaxSaveReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/31
 */

public class SpecialTaxSettingPresenter implements ISpecialTaxSettingContract.ISpecialTaxSettingPresenter {
    private ISpecialTaxSettingContract.ISpecialTaxSettingView mView;

    private SpecialTaxSettingPresenter() {
    }

    public static SpecialTaxSettingPresenter newInstance() {
        return new SpecialTaxSettingPresenter();
    }

    @Override
    public void save(SpecialTaxSaveReq req) {
        User.saveSpecialTaxRate(req, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.showToast("保存成功");
                start();
            }
        });
    }

    @Override
    public void start() {
        User.querySpecialTax(new SimpleObserver<SingleListResp<SpecialTaxBean>>(mView) {
            @Override
            public void onSuccess(SingleListResp<SpecialTaxBean> specialTaxBeanSingleListResp) {
                mView.cacheData(specialTaxBeanSingleListResp.getRecords());
                mView.setData(specialTaxBeanSingleListResp.getRecords());
            }
        });
    }

    @Override
    public void register(ISpecialTaxSettingContract.ISpecialTaxSettingView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
