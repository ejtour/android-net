package com.hll_sc_app.app.agreementprice.quotation;

import com.hll_sc_app.api.AgreementPriceService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationResp;
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
    public static final String CODE_UNBIND_EMAIL = "00120112037";
    public static final String CODE_NO_DATA = "00120112038";
    static final int PAGE_SIZE = 20;
    private QuotationFragmentContract.IHomeView mView;
    private int mPageNum;
    private int mTempNum;
    private List<PurchaserBean> mListPurchaser;

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

        getCooperationPurchaserObservable(null).doOnSubscribe(disposable -> mView.showLoading())
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
//        UserBean userBean = GreenDaoUtils.getUser();
//        if (userBean == null) {
//            return;
//        }
//        BaseReq<QuotationExportReq> req = new BaseReq<>();
//        QuotationExportReq exportReq = new QuotationExportReq();
//        exportReq.setPurchaserID(userBean.getPurchaserID());
//        if (TextUtils.isEmpty(email)) {
//            exportReq.setIsBindEmail(0);
//        } else {
//            exportReq.setIsBindEmail(1);
//            exportReq.setEmail(email);
//        }
//        QuotationExportReq.SearchParamsBean bean = new QuotationExportReq.SearchParamsBean();
//        bean.setEndDate(mView.getEndDate());
//        bean.setStartDate(mView.getStartDate());
//        bean.setGroupID(mView.getPurchaserId());
//        bean.setPriceStartDate(mView.getPriceStartDate());
//        bean.setPriceEndDate(mView.getPriceEndDate());
//        exportReq.setSearchParams(bean);
//        req.setData(exportReq);
//        PriceManagerService.INSTANCE
//            .exportQuotation(req)
//            .compose(ApiScheduler.getObservableScheduler())
//            .map(new Precondition<>())
//            .doOnSubscribe(disposable -> mView.showLoading())
//            .doFinally(() -> {
//                if (mView.isActive()) {
//                    mView.hideLoading();
//                }
//            })
//            .subscribe(new BaseCallback<QuotationExportResp>() {
//                @Override
//                public void onSuccess(QuotationExportResp result) {
//                    if (mView.isActive() && result != null) {
//                        mView.exportSuccess(result.getGroupMail());
//                    }
//                }
//
//                @Override
//                public void onFailure(UseCaseException e) {
//                    if (mView.isActive()) {
//                        if (TextUtils.equals(e.getCode(), CODE_UNBIND_EMAIL)) {
//                            // 员工未绑定邮箱
//                            mView.unbindEmail();
//                        } else if (TextUtils.equals(e.getCode(), QuotationFragmentPresenter.CODE_NO_DATA)) {
//                            mView.showToast("当前没有可导出的数据");
//                        } else {
//                            mView.exportFailure(e.getMessage());
//                        }
//                    }
//                }
//            });
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
