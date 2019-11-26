package com.hll_sc_app.app.agreementprice.goods;

import android.text.TextUtils;

import com.hll_sc_app.api.AgreementPriceService;
import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.app.agreementprice.quotation.QuotationFragmentPresenter;
import com.hll_sc_app.app.user.register.RegisterComplementPresenter;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationDetailBean;
import com.hll_sc_app.bean.export.ExportReq;
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.user.CategoryResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.Arrays;
import java.util.List;

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
    private CategoryResp mCategoryResp;

    public static GoodsPricePresenter newInstance() {
        return new GoodsPricePresenter();
    }

    @Override
    public void start() {
        queryGoodsPriceList();
    }

    @Override
    public void register(GoodsPriceContract.IGoodsPriceView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryGoodsPriceList() {
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("groupID", UserConfig.getGroupID())
                .put("startDate", mView.getPriceStartDate())
                .put("endDate", mView.getPriceEndDate())
                .put("categoryID", mView.getCategoryId())
                .put("shopIDs", mView.getShopIds())
                .put("productName", mView.getSearchParam())
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
        QuotationFragmentPresenter.getCooperationPurchaserObservable(null)
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<List<PurchaserBean>>() {
                    @Override
                    public void onSuccess(List<PurchaserBean> result) {
                        mListPurchaser = result;
                        PurchaserBean bean = new PurchaserBean();
                        bean.setPurchaserName(GoodsPriceShopSelectWindow.STRING_SELECT_ALL);
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

    @Override
    public void queryCategory() {
        if (mCategoryResp != null) {
            mView.showCategoryWindow(mCategoryResp);
            return;
        }
        RegisterComplementPresenter.getCategoryObservable()
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<CategoryResp>() {
                    @Override
                    public void onSuccess(CategoryResp resp) {
                        mCategoryResp = resp;
                        mView.showCategoryWindow(mCategoryResp);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void exportQuotation(String email) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        ExportReq req = new ExportReq();
        req.setActionType("2");
        req.setEmail(email);
        req.setIsBindEmail(TextUtils.isEmpty(email) ? "0" : "1");
        req.setTypeCode("common_quotation");
        req.setUserID(userBean.getEmployeeID());

        ExportReq.ParamsBean paramsBean = new ExportReq.ParamsBean();
        ExportReq.ParamsBean.CommonQuotation commonQuotation = new ExportReq.ParamsBean.CommonQuotation();
        if (!TextUtils.isEmpty(mView.getShopIds())) {
            commonQuotation.setShopIDs(Arrays.asList(mView.getShopIds().split(",")));
        }

        if (!TextUtils.isEmpty(mView.getCategoryId())){
            commonQuotation.setCategoryIDs(Arrays.asList(mView.getCategoryId().split(",")));
        }
        commonQuotation.setPriceStartDate(mView.getPriceStartDate());
        commonQuotation.setPriceEndDate(mView.getPriceEndDate());

        paramsBean.setCommonQuotation(commonQuotation);
        req.setParams(paramsBean);
        BaseReq<ExportReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        GoodsService.INSTANCE
                .exportRecord(baseReq)
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

    @Override
    public void queryPurchaserShopList(String purchaserId) {
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("groupID", UserConfig.getGroupID())
                .put("actionType", "quotation")
                .put("purchaserID", purchaserId)
                .put("searchParam", mView.getSearchParam())
                .create();
        AgreementPriceService.INSTANCE.queryCooperationPurchaserShopList(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<List<PurchaserShopBean>>() {
                    @Override
                    public void onSuccess(List<PurchaserShopBean> result) {
                        if (!CommonUtils.isEmpty(result)) {
                            PurchaserShopBean shopBean = new PurchaserShopBean();
                            shopBean.setShopName(GoodsPriceShopSelectWindow.STRING_SELECT_ALL);
                            result.add(0, shopBean);
                        }
                        mView.showPurchaserShopList(result);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showToast(e.getMessage());
                    }
                });
    }
}
