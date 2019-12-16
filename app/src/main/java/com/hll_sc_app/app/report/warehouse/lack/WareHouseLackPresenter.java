package com.hll_sc_app.app.report.warehouse.lack;

import android.text.TextUtils;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.WareHouseShipperBean;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.report.lack.LackDetailsResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Common;
import com.hll_sc_app.rest.Report;
import com.hll_sc_app.rest.User;
import com.hll_sc_app.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chukun
 * @since 2019/8/15
 */

public class WareHouseLackPresenter implements IWareHouseLackContract.IWareHouseLackPresenter {

    private int mPageNum;
    private IWareHouseLackContract.IWareHouseLackView mView;

    public static WareHouseLackPresenter newInstance() {
        return new WareHouseLackPresenter();
    }

    @Override
    public void start() {
        getShipperList();
        loadList();
    }

    private void load(boolean showLoading) {
        Report.queryLackDetails(mView.getReq()
                .put("pageNum", String.valueOf(mPageNum))
                .put("pageSize", "20")
                .create(), new SimpleObserver<LackDetailsResp>(mView, showLoading) {
            @Override
            public void onSuccess(LackDetailsResp lackDetailsResp) {
                mView.setData(lackDetailsResp, mPageNum > 1);
                if (!CommonUtils.isEmpty(lackDetailsResp.getRecords())) {
                    mPageNum++;
                }
            }
        });
    }

    @Override
    public void loadList() {
        mPageNum = 1;
        load(true);
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
    public void getShipperList() {
        Common.searchShipperList(1, "", "", new SimpleObserver<List<WareHouseShipperBean>>(mView) {
            @Override
            public void onSuccess(List<WareHouseShipperBean> wareHouseShipperBeans) {
                List<PurchaserBean> purchaserBeans = new ArrayList<>();
                if (!CommonUtils.isEmpty(wareHouseShipperBeans)) {
                    for (WareHouseShipperBean shipperBean : wareHouseShipperBeans) {
                        PurchaserBean purchaserBean = new PurchaserBean();
                        purchaserBean.setPurchaserID(shipperBean.getPurchaserID() + "");
                        purchaserBean.setPurchaserName(shipperBean.getPurchaserName());
                        purchaserBeans.add(purchaserBean);
                    }
                }
                mView.cacheShipperList(purchaserBeans);
            }
        });
    }

    @Override
    public void export(String email) {
        if (!TextUtils.isEmpty(email)) {
            User.bindEmail(email, new SimpleObserver<Object>(mView) {
                @Override
                public void onSuccess(Object o) {
                    export(null);
                }
            });
            return;
        }
        Report.exportReport(mView.getReq()
                .put("pageNum", "")
                .put("pageSize", "").create().getData(), "111012", email, Utils.getExportObserver(mView));
    }

    @Override
    public void register(IWareHouseLackContract.IWareHouseLackView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
