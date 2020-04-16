package com.hll_sc_app.app.complainmanage.add;

import android.text.TextUtils;

import com.hll_sc_app.api.AfterSalesService;
import com.hll_sc_app.api.ComplainManageService;
import com.hll_sc_app.app.complainmanage.detail.ComplainMangeDetailActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.aftersales.GenerateCompainResp;
import com.hll_sc_app.bean.complain.ComplainAddReq;
import com.hll_sc_app.bean.complain.DropMenuBean;
import com.hll_sc_app.bean.order.detail.OrderDetailBean;
import com.hll_sc_app.rest.Upload;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class ComplainMangeAddPresent implements IComplainMangeAddContract.IPresent {
    private IComplainMangeAddContract.IView mView;

    public static ComplainMangeAddPresent newInstance() {
        return new ComplainMangeAddPresent();
    }

    @Override
    public void register(IComplainMangeAddContract.IView view) {
        mView = view;
    }

    @Override
    public void queryDropMenus() {
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("name", mView.getSource() == ComplainMangeDetailActivity.SOURCE.COMPLAIN_MANAGE ? "ComplaintTypeEnum" : "PlatformComplaintTypeEnum")
                .create();
        ComplainManageService.INSTANCE
                .queryDropMenus(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<List<DropMenuBean>>() {
                    @Override
                    public void onSuccess(List<DropMenuBean> dropMenuBeans) {
                        mView.queryMenuSuccess(dropMenuBeans);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });

    }


    @Override
    public void imageUpload(File file) {
        Upload.upload((BaseLoadActivity)mView,file.getAbsolutePath(),filepath -> {
            mView.showImage(filepath);
        });
    }

    @Override
    public void saveComplain() {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        ComplainAddReq req = new ComplainAddReq();
        req.setActionType(mView.isEditModal() ? 2 : 1);
        if (mView.isEditModal()) {
            req.setComplaintID(mView.getComplainID());
        }
        req.setSourceClient("1".equals(userBean.getCurRole()) ? 6 : 1);
        req.setSource(2);
        req.setTarget(mView.getSource() == ComplainMangeDetailActivity.SOURCE.COMPLAIN_MANAGE ? 2 : 3);
        req.setSupplyName(userBean.getGroupName());
        req.setSupplyID(userBean.getGroupID());
        req.setPurchaserName(mView.getPurchaserName());
        req.setPurchaserID(mView.getPurchaserID());
        req.setPurchaserShopName(mView.getShopName());
        req.setPurchaserShopID(mView.getShopID());
        req.setBillID(mView.getBillID());
        req.setType(mView.getType());
        req.setReason(mView.getReason());
        req.setComplaintExplain(mView.getExplain());
        req.setImgUrls(mView.getImgs());
        req.setPurchaserContact(mView.getPhone());

        if (TextUtils.equals(mView.getType(), "1")) {
            req.setProducts(transformProducts(mView.getProducts()));
        }
        BaseReq<ComplainAddReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        AfterSalesService.INSTANCE
                .generateComplain(baseReq)
                .map(new Precondition<>())
                .compose(ApiScheduler.getObservableScheduler())
                .doOnSubscribe(disposable -> {
                    mView.showLoading();
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<GenerateCompainResp>() {
                    @Override
                    public void onSuccess(GenerateCompainResp generateCompainResp) {
                        mView.saveSuccess();
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }


    private List<ComplainAddReq.ProductsBean> transformProducts(List<OrderDetailBean> orderDetailBeans) {
        ArrayList<ComplainAddReq.ProductsBean> productsBeans = new ArrayList<>();
        for (OrderDetailBean orderDetailBean : orderDetailBeans) {
            ComplainAddReq.ProductsBean productsBean = new ComplainAddReq.ProductsBean();
            productsBean.setImgUrl(orderDetailBean.getImgUrl());
            productsBean.setProductPrice(orderDetailBean.getProductPrice());
            productsBean.setProductName(orderDetailBean.getProductName());
            productsBean.setProductID(orderDetailBean.getProductID());
            productsBean.setDetailID(orderDetailBean.getDetailID());
            productsBean.setAdjustmentNum(orderDetailBean.getAdjustmentNum());
            productsBean.setSaleUnitName(orderDetailBean.getSaleUnitName());
            productsBean.setStandardNum(orderDetailBean.getStandardNum());
            productsBean.setProductSpec(orderDetailBean.getProductSpec());
            productsBeans.add(productsBean);
        }
        return productsBeans;
    }
}
