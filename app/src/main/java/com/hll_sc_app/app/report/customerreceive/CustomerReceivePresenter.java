package com.hll_sc_app.app.report.customerreceive;

import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.customerreive.ReceiveCustomerBean;
import com.hll_sc_app.bean.report.customerreive.ReceiveCustomerResp;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/30
 */

public class CustomerReceivePresenter implements ICustomerReceiveContract.ICustomerReceivePresenter {
    private ICustomerReceiveContract.ICustomerReceiveView mView;
    private int mPageNum;

    private CustomerReceivePresenter() {
    }

    public static CustomerReceivePresenter newInstance() {
        return new CustomerReceivePresenter();
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

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    private void load(boolean showLoading) {
        SimpleObserver<ReceiveCustomerResp> observer = new SimpleObserver<ReceiveCustomerResp>(mView, showLoading) {
            @Override
            public void onSuccess(ReceiveCustomerResp customReceiveListResp) {
                mView.setData(customReceiveListResp, mPageNum > 1);
                if (CommonUtils.isEmpty(customReceiveListResp.getRecords())) return;
                mPageNum++;
            }
        };
        Observable.just(mPageNum)
                .map(integer -> {
                    List<ReceiveCustomerBean> list = new ArrayList<>();
                    for (int i = 0; i < 20; i++) {
                        ReceiveCustomerBean bean = new ReceiveCustomerBean();
                        bean.setInAmount(10);
                        bean.setOutAmount(10);
                        bean.setName("item " + i);
                        list.add(bean);
                    }
                    ReceiveCustomerResp resp = new ReceiveCustomerResp();
                    resp.setRecords(list);
                    return resp;
                })
                .compose(ApiScheduler.getObservableScheduler())
                .doOnSubscribe(disposable -> observer.startReq())
                .doFinally(observer::reqOver)
                .subscribe(observer);
    }

    @Override
    public void register(ICustomerReceiveContract.ICustomerReceiveView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
