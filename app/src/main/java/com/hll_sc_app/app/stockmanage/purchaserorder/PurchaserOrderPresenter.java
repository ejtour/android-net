package com.hll_sc_app.app.stockmanage.purchaserorder;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Stock;

/**
 *
 * 采购单查询
 * @author 初坤
 * @date 2019/7/20
 */
public class PurchaserOrderPresenter implements IPurchaserOrderContract.IPurchaserOrderPresenter {
    private IPurchaserOrderContract.IPurchaserOrderView mView;
    private int mPageNum;

    static PurchaserOrderPresenter newInstance() {
        return new PurchaserOrderPresenter();
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    @Override
    public void register(IPurchaserOrderContract.IPurchaserOrderView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        load(false);
    }

    @Override
    public void loadMore() {
        load(false);
    }

    private void load(boolean showLoading) {
        Stock.queryPurchaserOrderList(mView.getReq()
                .put("pageNo", String.valueOf(mPageNum))
                .put("pageSize", "20")
                .create(), new SimpleObserver<SingleListResp<PurchaserOrderBean>>(mView, showLoading) {
            @Override
            public void onSuccess(SingleListResp<PurchaserOrderBean> purchaserOrderRecordSingleListResp) {
                mView.setData(purchaserOrderRecordSingleListResp.getRecords(), mPageNum > 1);
                if (CommonUtils.isEmpty(purchaserOrderRecordSingleListResp.getRecords())) return;
                mPageNum++;
            }
        });
    }

}
