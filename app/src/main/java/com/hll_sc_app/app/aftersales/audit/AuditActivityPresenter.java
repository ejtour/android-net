package com.hll_sc_app.app.aftersales.audit;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.aftersales.PurchaserListResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.AfterSales;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/9
 */

public class AuditActivityPresenter implements IAuditActivityContract.IAuditActivityPresenter {
    private IAuditActivityContract.IAuditActivityView mView;

    public static AuditActivityPresenter newInstance() {
        return new AuditActivityPresenter();
    }

    private AuditActivityPresenter() {
    }

    @Override
    public void start() {
        AfterSales.getPurchaserList(new SimpleObserver<PurchaserListResp>(mView) {
            @Override
            public void onSuccess(PurchaserListResp resp) {
                mView.cachePurchaserShopList(resp);
            }
        });
    }

    @Override
    public void register(IAuditActivityContract.IAuditActivityView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
