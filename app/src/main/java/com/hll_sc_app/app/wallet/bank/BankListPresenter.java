package com.hll_sc_app.app.wallet.bank;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.wallet.BankBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Wallet;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/2
 */

public class BankListPresenter implements IBankListContract.IBankListPresenter {
    private int mPageNum;
    private IBankListContract.IBankListView mView;

    public static BankListPresenter newInstance() {
        return new BankListPresenter();
    }

    private BankListPresenter() {
    }

    @Override
    public void start() {
        mPageNum = 1;
        getBankList(true);
    }

    private void getBankList(boolean isLoading) {
        Wallet.getBankList(mPageNum, new SimpleObserver<List<BankBean>>(mView, isLoading) {
            @Override
            public void onSuccess(List<BankBean> bankBeans) {
                mView.setBankList(bankBeans, mPageNum > 1);
                if (!CommonUtils.isEmpty(bankBeans)) mPageNum++;
            }
        });
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        getBankList(false);
    }

    @Override
    public void loadMore() {
        getBankList(false);
    }

    @Override
    public void register(IBankListContract.IBankListView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
