package com.hll_sc_app.app.report.warehouse.product;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.common.WareHouseShipperBean;
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.report.warehouse.WareHouseLackProductReq;
import com.hll_sc_app.bean.report.warehouse.WareHouseLackProductResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author chukun
 * @since 2019/8/15
 */

public class WareHouseProductDetailPresenter implements IWareHouseProductDetailContract.IWareHouseProductDetailPresenter {

    private int mPageNum;
    private IWareHouseProductDetailContract.IWareHouseProductDetailView mView;

    public static WareHouseProductDetailPresenter newInstance() {
        WareHouseProductDetailPresenter presenter = new WareHouseProductDetailPresenter();
        return presenter;
    }

    @Override
    public void start() {
        mPageNum = 1;
        queryList(true);
    }


    private void queryShipperList(String searchWord){
        Report.queryWareHouseShipperList(UserConfig.getGroupID(), 1, searchWord, new SimpleObserver<List<WareHouseShipperBean>>(mView, false) {
            @Override
            public void onSuccess(List<WareHouseShipperBean>  shipperBeans) {
                List<PurchaserBean> purchaserBeans = new ArrayList<>();
                if(shipperBeans!=null && shipperBeans.size()>0){
                    for(WareHouseShipperBean shipperBean:shipperBeans) {
                        PurchaserBean purchaserBean = new PurchaserBean();
                        purchaserBean.setPurchaserID(shipperBean.getPurchaserID()+"");
                        purchaserBean.setPurchaserName(shipperBean.getPurchaserName());
                        purchaserBeans.add(purchaserBean);
                    }
                }
                mView.showPurchaserWindow(purchaserBeans);
            }
        });
    }

    private void queryList(boolean showLoading) {
        WareHouseLackProductReq params = mView.getRequestParams();
        params.setGroupID(UserConfig.getGroupID());
        params.setShipperID(mView.getShipperID());
        params.setPageNum(mPageNum);
        params.setPageSize(20);
        Report.queryWareHouseProductLackDetail(params, new SimpleObserver<WareHouseLackProductResp>(mView, showLoading) {
                    @Override
                    public void onSuccess(WareHouseLackProductResp wareHouseLackProductResp) {
                        mView.setWareHouseProductDetailList(wareHouseLackProductResp, mPageNum > 1);
                        if (!CommonUtils.isEmpty(wareHouseLackProductResp.getRecords())) {
                            mPageNum++;
                        }
                    }
                });
    }

    @Override
    public void loadWareHouseProductDetailList() {
        mPageNum = 1;
        queryList(false);
    }

    @Override
    public void loadMore() {
        queryList(false);
    }

    @Override
    public void getShipperList(String searchWord) {
        queryShipperList(searchWord);
    }

    @Override
    public void exportWareHouseProductDetail(String email, String reqParams) {
        if (!TextUtils.isEmpty(email)) {
            bindEmail(email);
            return;
        }
        Report.exportReport(reqParams, "111012", email, new SimpleObserver<ExportResp>(mView) {
            @Override
            public void onSuccess(ExportResp exportResp) {
                if (!TextUtils.isEmpty(exportResp.getEmail()))
                    mView.exportSuccess(exportResp.getEmail());
                else mView.exportFailure("噢，服务器暂时开了小差\n攻城狮正在全力抢修");
            }

            @Override
            public void onFailure(UseCaseException e) {
                if ("00120112037".equals(e.getCode())) mView.bindEmail();
                else if ("00120112038".equals(e.getCode()))
                    mView.exportFailure("当前没有可导出的数据");
                else mView.exportFailure("噢，服务器暂时开了小差\n攻城狮正在全力抢修");
            }
        });
    }

    @Override
    public void register(IWareHouseProductDetailContract.IWareHouseProductDetailView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    private void bindEmail(String email) {
        UserBean user = GreenDaoUtils.getUser();
        if (user == null)
            return;
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("email", email)
                .put("employeeID", user.getEmployeeID())
                .create();
        SimpleObserver<Object> observer = new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                WareHouseLackProductReq params = mView.getRequestParams();
                params.setGroupID(UserConfig.getGroupID());
                params.setPageNum(mPageNum);
                params.setPageSize(20);
                params.setOrder(0);
                Gson gson = new Gson();
                String reqParams = gson.toJson(params);
                exportWareHouseProductDetail(null, reqParams);
            }
        };
        UserService.INSTANCE.bindEmail(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(observer);
    }
}
