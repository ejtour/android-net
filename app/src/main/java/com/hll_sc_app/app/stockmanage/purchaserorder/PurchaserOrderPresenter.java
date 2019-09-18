package com.hll_sc_app.app.stockmanage.purchaserorder;

import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderReq;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Stock;

/**
 *
 * 采购单查询
 * @author 初坤
 * @date 2019/7/20
 */
public class PurchaserOrderPresenter implements PurchaserOrderContract.IPurchaserOrderPresenter {
    private PurchaserOrderContract.IPurchaserOrderView mView;
    private int mPageNum;
    private int mTempPageNum;

    static PurchaserOrderPresenter newInstance() {
        return new PurchaserOrderPresenter();
    }

    @Override
    public void start() {
        queryPurchaserOrderList(true);
    }

    @Override
    public void register(PurchaserOrderContract.IPurchaserOrderView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryPurchaserOrderList(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toPurchaserOrderList(showLoading);
    }

    @Override
    public void queryMorePurchaserOrderList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toPurchaserOrderList(false);
    }

    private void toPurchaserOrderList(boolean showLoading) {
        PurchaserOrderReq requestParams = mView.getRequestParams();
        requestParams.setGroupID(UserConfig.getGroupID());
        requestParams.setPageNo(mTempPageNum);
        requestParams.setPageSize(20);
        Stock.querySupplyChainPurchaserOrderList(requestParams, new SimpleObserver<PurchaserOrderResp>(mView,showLoading) {
            @Override
            public void onSuccess(PurchaserOrderResp purchaserOrderResp) {
                mPageNum = mTempPageNum;
                mView.showPurchaserOrderList(purchaserOrderResp,mPageNum!=1,(int)purchaserOrderResp.getPageInfo().getTotal());
            }
            @Override
            public void onFailure(UseCaseException e) {
                mView.showError(e);
            }
        });
    }

}
