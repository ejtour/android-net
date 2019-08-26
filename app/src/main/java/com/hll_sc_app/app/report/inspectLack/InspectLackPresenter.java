package com.hll_sc_app.app.report.inspectLack;

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
import com.hll_sc_app.bean.report.inspectLack.InspectLackResp;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsBean;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsDetailBean;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsDetailParam;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsResp;
import com.hll_sc_app.bean.report.req.BaseReportReqParam;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author chukun
 * @since 2019/8/15
 */

public class InspectLackPresenter implements IInspectLackContract.IInspectLackPresenter {

    private int mPageNum;
    private IInspectLackContract.IInspectLackView mView;

    public static InspectLackPresenter newInstance() {
        InspectLackPresenter presenter = new InspectLackPresenter();
        return presenter;
    }

    @Override
    public void start() {
        mPageNum = 1;
        queryList(true);
    }

    private void queryList(boolean showLoading) {
        BaseReportReqParam params = mView.getRequestParams();
        params.setGroupID(UserConfig.getGroupID());
        params.setPageNum(mPageNum);
        params.setPageSize(20);
        Report.queryInspectLack(params, new SimpleObserver<InspectLackResp>(mView, showLoading) {
                    @Override
                    public void onSuccess(InspectLackResp inspectLackResp) {
                        mView.setInspectList(inspectLackResp, mPageNum > 1);
                        if (!CommonUtils.isEmpty(inspectLackResp.getRecords())) {
                            mPageNum++;
                        }
                    }
                });
    }

    @Override
    public void loadInspectLackList() {
        mPageNum = 1;
        queryList(false);
    }

    @Override
    public void loadMore() {
        queryList(false);
    }

    @Override
    public void exportInspectLack(String email, String reqParams) {
        if (!TextUtils.isEmpty(email)) {
            bindEmail(email);
            return;
        }
        Report.exportReport(reqParams, "111007", email, new SimpleObserver<ExportResp>(mView) {
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
    public void register(IInspectLackContract.IInspectLackView view) {
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
                BaseReportReqParam params = mView.getRequestParams();
                params.setGroupID(UserConfig.getGroupID());
                params.setPageNum(mPageNum);
                params.setPageSize(20);
                Gson gson = new Gson();
                String reqParams = gson.toJson(params);
                exportInspectLack(null, reqParams);
            }
        };
        UserService.INSTANCE.bindEmail(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(observer);
    }
}
