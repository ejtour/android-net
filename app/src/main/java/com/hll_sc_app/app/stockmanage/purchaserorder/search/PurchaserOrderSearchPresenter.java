package com.hll_sc_app.app.stockmanage.purchaserorder.search;

import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderDetailResp;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderSearchResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Stock;

import java.util.List;

/**
 *
 * 采购单详情查询
 * @author 初坤
 * @date 2019/7/20
 */
public class PurchaserOrderSearchPresenter implements PurchaserOrderSearchContract.IPurchaserOrderSearchPresenter {
    private PurchaserOrderSearchContract.IPurchaserOrderSearchView mView;

    private int pageNum = 1;
    private int pageNumTemp = 1;
    private int pageSize = 30;

    static PurchaserOrderSearchPresenter newInstance() {
        return new PurchaserOrderSearchPresenter();
    }

    @Override
    public void start() {
        queryPurchaserOrderSearchList(true);
    }

    @Override
    public void register(PurchaserOrderSearchContract.IPurchaserOrderSearchView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryPurchaserOrderSearchList(boolean showLoading) {
        toPurchaserOrderList(showLoading);
    }

    @Override
    public void queryMorePurchaserOrderSearchList(boolean showLoading) {
        pageNumTemp++;
        toPurchaserOrderList(showLoading);
    }

    private void toPurchaserOrderList(boolean showLoading) {
        String searchKey = mView.getSearchKey();
        Stock.querySupplyChainGroupList(UserConfig.getGroupID(),pageNumTemp,pageSize,searchKey, new SimpleObserver<PurchaserOrderSearchResp>(mView,showLoading) {
            @Override
            public void onSuccess(PurchaserOrderSearchResp purchaserOrderSearchResp) {
                mView.showPurchaserOrderSearchList(purchaserOrderSearchResp,pageNumTemp>1);
                pageNum = pageNumTemp;
            }
            @Override
            public void onFailure(UseCaseException e) {
                mView.showError(e);
                pageNumTemp = pageNum;
            }
        });
    }

}
