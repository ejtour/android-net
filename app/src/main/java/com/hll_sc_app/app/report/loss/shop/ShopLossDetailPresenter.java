package com.hll_sc_app.app.report.loss.shop;

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
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.bean.report.loss.CustomerAndShopLossReq;
import com.hll_sc_app.bean.report.loss.CustomerAndShopLossResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 *
 * 门店流失明细
 * @author 初坤
 * @date 2019/7/20
 */
public class ShopLossDetailPresenter implements ShopLossDetailContract.IShopLossDetailPresenter {

    private ShopLossDetailContract.IShopLossDetailView mView;
    private int mPageNum;
    private int mTempPageNum;

    static ShopLossDetailPresenter newInstance() {
        return new ShopLossDetailPresenter();
    }

    @Override
    public void start() {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        queryShopLossDetail(true);
    }



    @Override
    public void register(ShopLossDetailContract.IShopLossDetailView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryShopLossDetail(boolean showLoading) {
        toQueryRefundProductDetail(showLoading);
    }

    @Override
    public void loadMoreShopLossDetail(){
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryRefundProductDetail(true);
    }

    private void toQueryRefundProductDetail(boolean showLoading) {
        CustomerAndShopLossReq requestParams = mView.getRequestParams();
        requestParams.setGroupID(UserConfig.getGroupID());
        requestParams.setPageNum(mTempPageNum);
        requestParams.setPageSize(20);
        Report.queryCustomerOrShopLossDetail(requestParams, new SimpleObserver<CustomerAndShopLossResp>(mView,showLoading) {
            @Override
            public void onSuccess(CustomerAndShopLossResp lossResp) {
                mPageNum = mTempPageNum;
                mView.showShopLossDetail(lossResp,mPageNum>1);
            }
            @Override
            public void onFailure(UseCaseException e) {
                mView.showError(e);
            }
        });
    }

    @Override
    public void exportShopLossDetail(String email, String reqParams) {
        if (!TextUtils.isEmpty(email)) {
            bindEmail(email);
            return;
        }
        Report.exportReport(reqParams, "111032", email, new SimpleObserver<ExportResp>(mView) {
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
                CustomerAndShopLossReq params = mView.getRequestParams();
                params.setGroupID(UserConfig.getGroupID());
                params.setPageNum(mPageNum);
                params.setPageSize(20);
                Gson gson = new Gson();
                String reqParams = gson.toJson(params);
                exportShopLossDetail(null, reqParams);
            }
        };
        UserService.INSTANCE.bindEmail(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(observer);
    }

}
