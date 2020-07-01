package com.hll_sc_app.app.shop;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.shop.CategoryBean;
import com.hll_sc_app.bean.shop.ShopAreaBean;
import com.hll_sc_app.bean.shop.SupplierShopBean;
import com.hll_sc_app.bean.shop.SupplierShopUpdateReq;
import com.hll_sc_app.bean.user.CategoryItem;
import com.hll_sc_app.bean.user.CategoryResp;
import com.hll_sc_app.rest.Upload;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class SupplierShopPresenter implements ISupplierShopContract.ISupplierShopPresenter {

    private ISupplierShopContract.ISupplierShopView mView;
    private List<CategoryItem> mCategoryList;

    @Override
    public void listSupplierShop() {
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("supplierID", UserConfig.getGroupID())
                .create();
        CooperationPurchaserService.INSTANCE.listSupplierShop(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<List<SupplierShopBean>>() {
                    @Override
                    public void onSuccess(List<SupplierShopBean> resp) {
                        mView.show(resp.get(0));
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void updateSupplierShop(SupplierShopBean bean) {
        BaseReq<SupplierShopUpdateReq> baseReq = new BaseReq<>();
        SupplierShopUpdateReq req = new SupplierShopUpdateReq();
        req.setBusinessEndTime(bean.getBusinessEndTime());
        req.setBusinessStartTime(bean.getBusinessStartTime());
        req.setIsActive(bean.getIsActive());
        req.setLogoUrl(bean.getLogoUrl());
        req.setShopAddress(bean.getShopAddress());
        req.setShopAdmin(bean.getShopAdmin());
        req.setShopID(bean.getShopID());
        req.setShopName(bean.getShopName());
        req.setShopPhone(bean.getShopPhone());
        List<CategoryBean> categoryBeanList = new ArrayList<>();
        String[] categoryIDs = bean.getCategoryIDList().split(",");
        String[] categoryNames = bean.getCategoryNameList().split("、");
        for (int i = 0; i < categoryIDs.length; i++) {
            CategoryBean categoryBean = new CategoryBean();
            categoryBean.setCategoryID(categoryIDs[i]);
            categoryBean.setCategoryName(categoryNames[i]);
            categoryBeanList.add(categoryBean);
        }
        req.setCategory(categoryBeanList);
        ShopAreaBean shopAreaBean = new ShopAreaBean();
        shopAreaBean.setShopCity(bean.getShopCity());
        shopAreaBean.setShopCityCode(bean.getShopCityCode());
        shopAreaBean.setShopProvince(bean.getShopProvince());
        shopAreaBean.setShopProvinceCode(bean.getShopProvinceCode());
        shopAreaBean.setShopDistrict(bean.getShopDistrict());
        shopAreaBean.setShopDistrictCode(bean.getShopDistrictCode());
        req.setShopArea(shopAreaBean);
        baseReq.setData(req);
        CooperationPurchaserService.INSTANCE.updateSupplierShop(baseReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<Object>() {
                    @Override
                    public void onSuccess(Object resp) {
                        mView.success();
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void showCategory() {
        if (mCategoryList != null && mCategoryList.size() != 0) {
            mView.showCategoryWindow(mCategoryList);
            return;
        }

        getCategoryObservable().doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<CategoryResp>() {
                    @Override
                    public void onSuccess(CategoryResp resp) {
                        mCategoryList = resp.getList();
                        mView.showCategoryWindow(mCategoryList);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void imageUpload(String path) {
        Upload.upload(mView, path, mView::showPhoto);
    }

    public static Observable<CategoryResp> getCategoryObservable() {
        return UserService.INSTANCE.queryCategory(BaseMapReq.newBuilder().create())
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>());
    }

    @Override
    public void register(ISupplierShopContract.ISupplierShopView view) {
        this.mView = view;
    }

    static SupplierShopPresenter newInstance() {
        return new SupplierShopPresenter();
    }
}
