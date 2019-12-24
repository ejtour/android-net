package com.hll_sc_app.app.pricemanage.log;

import android.text.TextUtils;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.api.PriceManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.export.ExportReq;
import com.hll_sc_app.bean.pricemanage.PriceLogResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Utils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 售价设置-变更日志
 *
 * @author zhuyingsong
 * @date 2019/7/16
 */
public class PriceChangeLogPresenter implements PriceChangeLogContract.IPriceManagePresenter {
    private PriceChangeLogContract.IPriceManageView mView;
    private int mPageNum;
    private int mTempPageNum;

    static PriceChangeLogPresenter newInstance() {
        return new PriceChangeLogPresenter();
    }

    @Override
    public void start() {
        queryPriceLogList(true);
    }

    @Override
    public void register(PriceChangeLogContract.IPriceManageView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryPriceLogList(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toQueryPriceChangeLogList(showLoading);
    }

    @Override
    public void queryMorePriceLogList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryPriceChangeLogList(false);
    }

    @Override
    public void exportLog(String email) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        ExportReq req = new ExportReq();
        req.setIsBindEmail(TextUtils.isEmpty(email) ? "0" : "1");
        req.setEmail(email);
        req.setTypeCode("priceFlow");
        req.setUserID(userBean.getEmployeeID());

        ExportReq.ParamsBean paramsBean = new ExportReq.ParamsBean();
        ExportReq.ParamsBean.PriceFlowBean priceFlowBean = new ExportReq.ParamsBean.PriceFlowBean();
        priceFlowBean.setGroupID(userBean.getGroupID());
        priceFlowBean.setStartTime(mView.getStartTime());
        priceFlowBean.setEndTime(mView.getEndTime());
        priceFlowBean.setProductName(mView.getSearchParam());
        paramsBean.setPriceFlow(priceFlowBean);
        req.setParams(paramsBean);
        BaseReq<ExportReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        GoodsService.INSTANCE.exportRecord(baseReq)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(Utils.getExportObserver(mView));
    }

    private void toQueryPriceChangeLogList(boolean showLoading) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("groupID", UserConfig.getGroupID())
            .put("pageNum", String.valueOf(mTempPageNum))
            .put("pageSize", "20")
            .put("startTime", mView.getStartTime())
            .put("endTime", mView.getEndTime())
            .put("productName", mView.getSearchParam())
            .create();
        PriceManageService.INSTANCE.queryPriceChangeLogList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> {
                if (showLoading) {
                    mView.showLoading();
                }
            })
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<PriceLogResp>() {
                @Override
                public void onSuccess(PriceLogResp resp) {
                    mPageNum = mTempPageNum;
                    mView.showPriceLogList(resp.getList(), mPageNum != 1, resp.getTotal());
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }
}
