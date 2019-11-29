package com.hll_sc_app.app.aftersales.detail;

import android.text.TextUtils;

import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.aftersales.AfterSalesBean;
import com.hll_sc_app.bean.aftersales.GenerateCompainResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.AfterSales;

import java.util.List;

public class AfterSalesDetailPresenter implements IAfterSalesDetailContract.IAfterSalesDetailPresenter {
    private IAfterSalesDetailContract.IAfterSalesDetailView mView;
    private String billID;
    private String billNo;

    private AfterSalesDetailPresenter() {
    }

    static AfterSalesDetailPresenter newInstance(String detailID) {
        AfterSalesDetailPresenter presenter = new AfterSalesDetailPresenter();
        presenter.billID = detailID;
        return presenter;
    }

    static AfterSalesDetailPresenter newInstanceByNo(String detailNo) {
        AfterSalesDetailPresenter presenter = new AfterSalesDetailPresenter();
        presenter.billNo = detailNo;
        return presenter;
    }

    @Override
    public void doAction(int actionType, String payType, String msg) {
        AfterSales.afterSalesAction(actionType, billID, payType, msg, null,
                new SimpleObserver<MsgWrapper<Object>>(true, mView) {
                    @Override
                    public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                        mView.handleStatusChange();
                    }
                });
    }

    @Override
    public void modifyPrice(String price, String refundBillDetailID) {
        AfterSales.modifyUnitPrice(price, refundBillDetailID, billID, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.handleStatusChange();
            }
        });
    }

    @Override
    public void genereteComplain(AfterSalesBean data) {
        AfterSales.generateComplain(data, new SimpleObserver<GenerateCompainResp>(mView) {
            @Override
            public void onSuccess(GenerateCompainResp generateCompainResp) {
                mView.genereteComplainSuccess(generateCompainResp);
            }
        });
    }

    @Override
    public void start() {
        if(!TextUtils.isEmpty(billID)){
            AfterSales.requestAfterSalesDetail(
                    billID,
                    new SimpleObserver<List<AfterSalesBean>>(mView) {
                        @Override
                        public void onSuccess(List<AfterSalesBean> recordsBeans) {
                            if (!CommonUtils.isEmpty(recordsBeans)) {
                                mView.showDetail(recordsBeans.get(0));
                            } else {
                                mView.showToast("获取数据失败");
                            }
                        }
                    });
        }else if(!TextUtils.isEmpty(billNo)){
            AfterSales.requestAfterSalesDetailByNo(
                    billNo,
                    new SimpleObserver<List<AfterSalesBean>>(mView) {
                        @Override
                        public void onSuccess(List<AfterSalesBean> recordsBeans) {
                            if (!CommonUtils.isEmpty(recordsBeans)) {
                                mView.showDetail(recordsBeans.get(0));
                            } else {
                                mView.showToast("获取数据失败");
                            }
                        }
                    });
        }
    }

    @Override
    public void register(IAfterSalesDetailContract.IAfterSalesDetailView view) {
        mView = CommonUtils.checkNotNull(view);
    }
}
