package com.hll_sc_app.app.stockmanage.purchaserorder.search;

import android.text.TextUtils;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderSearchBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Stock;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 *
 * 采购单详情查询
 * @author 初坤
 * @date 2019/7/20
 */
public class PurchaserOrderSearchPresenter implements PurchaserOrderSearchContract.IPurchaserOrderSearchPresenter {
    private PurchaserOrderSearchContract.IPurchaserOrderSearchView mView;

    private int mPageNum;

    static PurchaserOrderSearchPresenter newInstance() {
        return new PurchaserOrderSearchPresenter();
    }

    private PurchaserOrderSearchPresenter() {
    }

    @Override
    public void start() {
        mPageNum = 1;
        loadMore();
    }

    @Override
    public void register(PurchaserOrderSearchContract.IPurchaserOrderSearchView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void loadMore() {
        String searchKey = mView.getSearchKey();
        if (TextUtils.isEmpty(searchKey)) {
            Observable.timer(0, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread()).subscribe(aLong -> mView.setData(new ArrayList<>(), false));
            return;
        }
        Stock.querySupplyChainGroupList(mPageNum, searchKey, new SimpleObserver<SingleListResp<PurchaserOrderSearchBean>>(mView, false) {
            @Override
            public void onSuccess(SingleListResp<PurchaserOrderSearchBean> purchaserOrderSearchBeanSingleListResp) {
                mView.setData(purchaserOrderSearchBeanSingleListResp.getRecords(), mPageNum > 1);
                if (CommonUtils.isEmpty(purchaserOrderSearchBeanSingleListResp.getRecords()))
                    return;
                mPageNum++;
            }
        });
    }
}
