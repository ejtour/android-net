package com.hll_sc_app.app.agreementprice.goods;

import android.text.TextUtils;

import com.hll_sc_app.api.AgreementPriceService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationDetailBean;
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.bean.export.GoodsPriceExportReq;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import io.reactivex.Observable;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 协议价管理-商品
 *
 * @author 朱英松
 * @date 2019/7/11
 */
public class GoodsPricePresenter implements GoodsPriceContract.IGoodsPricePresenter {
    private List<PurchaserBean> mListPurchaser;
    private GoodsPriceContract.IGoodsPriceView mView;

    public static GoodsPricePresenter newInstance() {
        return new GoodsPricePresenter();
    }

    @Override
    public void start() {
        queryGoodsPriceList(true);
    }

    @Override
    public void register(GoodsPriceContract.IGoodsPriceView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryGoodsPriceList(boolean showLoading) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("groupID", UserConfig.getGroupID())
            .create();
        AgreementPriceService.INSTANCE.queryGoodsPriceList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<List<QuotationDetailBean>>() {
                @Override
                public void onSuccess(List<QuotationDetailBean> list) {
                    mView.showGoodsPriceList(list);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
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
        GoodsPriceExportReq req = new GoodsPriceExportReq();
        req.setIsBindEmail(TextUtils.isEmpty(email) ? "0" : "1");
        req.setEmail(email);
        req.setGroupID(UserConfig.getGroupID());
        GoodsPriceExportReq.SearchParamsBean bean = new GoodsPriceExportReq.SearchParamsBean();
        bean.setCategoryID(mView.getCategoryId());
        bean.setEndDate(mView.getEndDate());
        bean.setStartDate(mView.getStartDate());
        bean.setPriceStartDate(mView.getPriceStartDate());
        bean.setPriceEndDate(mView.getPriceEndDate());
        bean.setPurchaserID(mView.getPurchaserId());
        bean.setShopIDs(mView.getShopIds());
        req.setSearchParams(bean);
        BaseReq<GoodsPriceExportReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        AgreementPriceService.INSTANCE.exportGoodsPriceList(baseReq)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<ExportResp>() {
                @Override
                public void onSuccess(ExportResp resp) {
                    if (!TextUtils.isEmpty(resp.getEmail())) {
                        mView.exportSuccess(resp.getEmail());
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
}
