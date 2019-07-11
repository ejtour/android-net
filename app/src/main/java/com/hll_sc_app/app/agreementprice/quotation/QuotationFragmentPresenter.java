package com.hll_sc_app.app.agreementprice.quotation;

import android.text.TextUtils;

import com.hll_sc_app.api.AgreementPriceService;
import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationResp;
import com.hll_sc_app.bean.export.ExportReq;
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import io.reactivex.Observable;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 协议价管理-报价单
 *
 * @author 朱英松
 * @date 2019/2/13
 */
public class QuotationFragmentPresenter implements QuotationFragmentContract.IHomePresenter {
    static final int PAGE_SIZE = 20;
    private int mPageNum;
    private int mTempNum;
    private List<PurchaserBean> mListPurchaser;
    private QuotationFragmentContract.IHomeView mView;

    public static QuotationFragmentPresenter newInstance() {
        return new QuotationFragmentPresenter();
    }

    @Override
    public void start() {
        queryQuotationList(true);
    }

    @Override
    public void register(QuotationFragmentContract.IHomeView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryQuotationList(boolean showLoading) {
        mPageNum = 1;
        mTempNum = mPageNum;
        toQueryQuotationList(showLoading);
    }

    @Override
    public void queryMoreQuotationList() {
        mTempNum = mPageNum;
        mTempNum++;
        toQueryQuotationList(false);
    }

    @Override
    public void queryCooperationPurchaserList() {
        if (!CommonUtils.isEmpty(mListPurchaser)) {
            mView.showPurchaserWindow(mListPurchaser);
            return;
        }
        getCooperationPurchaserObservable(null)
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<List<PurchaserBean>>() {
                @Override
                public void onSuccess(List<PurchaserBean> result) {
                    mListPurchaser = result;
                    PurchaserBean bean = new PurchaserBean();
                    bean.setPurchaserID("");
                    bean.setPurchaserName("全部");
                    mListPurchaser.add(0, bean);
                    mView.showPurchaserWindow(mListPurchaser);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    if (mView.isActive()) {
                        mView.showToast(e.getMessage());
                    }
                }
            });
    }

    public static Observable<List<PurchaserBean>> getCooperationPurchaserObservable(String searchParam) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("searchParam", searchParam)
            .put("groupID", UserConfig.getGroupID())
            .put("actionType", "quotation")
            .create();
        return AgreementPriceService.INSTANCE.queryCooperationPurchaserList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>());
    }

    @Override
    public void exportQuotation(String email) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        ExportReq req = new ExportReq();
        req.setIsBindEmail(TextUtils.isEmpty(email) ? "0" : "1");
        req.setTypeCode("quotation");
        req.setUserID(userBean.getEmployeeID());
        ExportReq.ParamsBean paramsBean = new ExportReq.ParamsBean();
        ExportReq.ParamsBean.SupplierQuotationBean quotationBean = new ExportReq.ParamsBean.SupplierQuotationBean();
        quotationBean.setGroupID(userBean.getGroupID());
        quotationBean.setBillNos(mView.getBillNos());
        paramsBean.setSupplierQuotation(quotationBean);
        req.setParams(paramsBean);
        BaseReq<ExportReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        GoodsService.INSTANCE.exportRecord(baseReq)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<ExportResp>() {
                @Override
                public void onSuccess(ExportResp resp) {
                    if (!TextUtils.isEmpty(email)) {
                        mView.exportSuccess(email);
                    } else {
                        mView.exportFailure("噢，服务器暂时开了小差\n攻城狮正在全力抢修");
                    }
                }

                @Override
                public void onFailure(UseCaseException e) {
                    if (TextUtils.equals("00120112037", e.getCode())) {
                        mView.bindEmail();
                    } else if (TextUtils.equals("00120112038", e.getCode())) {
                        mView.exportFailure("当前没有可导出的数据");
                    } else {
                        mView.exportFailure("噢，服务器暂时开了小差\n攻城狮正在全力抢修");
                    }
                }
            });
    }


    private void toQueryQuotationList(boolean showLoading) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("startDate", mView.getStartDate())
            .put("endDate", mView.getEndDate())
            .put("billType", "1")
            .put("priceEndDate", mView.getPriceEndDate())
            .put("priceStartDate", mView.getPriceStartDate())
            .put("groupID", UserConfig.getGroupID())
            .put("billNo", mView.getSearchParam())
            .put("pageNum", String.valueOf(mTempNum))
            .put("pageSize", String.valueOf(PAGE_SIZE))
            .put("purchaserID", mView.getPurchaserId())
            .create();
        AgreementPriceService.INSTANCE.queryQuotationList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> {
                if (showLoading) {
                    mView.showLoading();
                }
            })
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<QuotationResp>() {
                @Override
                public void onSuccess(QuotationResp result) {
                    mPageNum = mTempNum;
                    mView.showQuotationList(result, mPageNum != 1);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    if (mView.isActive()) {
                        mView.showError(e);
                    }
                }
            });
    }
}
