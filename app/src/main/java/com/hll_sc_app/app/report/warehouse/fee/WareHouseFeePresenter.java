package com.hll_sc_app.app.report.warehouse.fee;

import android.text.TextUtils;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.common.WareHouseShipperBean;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.report.warehouse.WareHouseFeeBean;
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

public class WareHouseFeePresenter implements IWareHouseFeeContract.IWareHouseFeePresenter {

    private int mPageNum;
    private IWareHouseFeeContract.IWareHouseFeeView mView;

    public static WareHouseFeePresenter newInstance() {
        return new WareHouseFeePresenter();
    }

    private WareHouseFeePresenter() {
    }

    @Override
    public void start() {
        getShipperList();
        loadList();
    }

    private void load(boolean showLoading) {
        Report.queryWareHouseFee(mView.getReq()
                .put("pageNo", String.valueOf(mPageNum))
                .put("pageSize", "20")
                .create(), new SimpleObserver<SingleListResp<WareHouseFeeBean>>(mView, showLoading) {
            @Override
            public void onSuccess(SingleListResp<WareHouseFeeBean> wareHouseFeeBeanSingleListResp) {
                mView.setData(wareHouseFeeBeanSingleListResp.getRecords(), mPageNum > 1);
                if (!CommonUtils.isEmpty(wareHouseFeeBeanSingleListResp.getRecords())) {
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
                .put("pageNo", "")
                .put("pageSize", "")
                .create().getData(), "111093", email, Utils.getExportObserver(mView, "shopmall-supplier"));
    }

    @Override
    public void register(IWareHouseFeeContract.IWareHouseFeeView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
