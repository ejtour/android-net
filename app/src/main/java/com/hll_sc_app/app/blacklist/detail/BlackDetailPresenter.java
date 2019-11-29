package com.hll_sc_app.app.blacklist.detail;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.groupInfo.GroupInfoReq;
import com.hll_sc_app.bean.groupInfo.GroupInfoResp;
import com.hll_sc_app.bean.orientation.OrientationDetailBean;
import com.hll_sc_app.bean.orientation.OrientationDetailRes;
import com.hll_sc_app.bean.orientation.OrientationListBean;
import com.hll_sc_app.bean.orientation.OrientationProductSpecBean;
import com.hll_sc_app.bean.orientation.OrientationSetReq;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class BlackDetailPresenter implements IBlackDetailContract.IPresent {

    private IBlackDetailContract.IDetailView mView;

    @Override
    public void setOrientation(List<OrientationDetailBean> list, OrientationListBean bean) {
        BaseReq<OrientationSetReq> baseReq = new BaseReq<>();
        OrientationSetReq req = new OrientationSetReq();
        if(bean.getId() == null || bean.getId().equalsIgnoreCase("")) {
            req.setFlag(1);
        } else {
            req.setFlag(2);
        }
        req.setGroupID(UserConfig.getGroupID());
        req.setMainID(bean.getId());

        List<OrientationSetReq.Details> details = new ArrayList<>();

        for (OrientationDetailBean orientationDetailBean : list) {
            OrientationSetReq.Details detail = new OrientationSetReq.Details();
            detail.setProductID(orientationDetailBean.getProductID());
            StringBuilder specIdBuilder = new StringBuilder();
            for (OrientationProductSpecBean specBean : orientationDetailBean.getSpecs()) {
                specIdBuilder.append(specBean.getSpecID()).append(",");
            }
            detail.setSpecIDList(Arrays.asList(specIdBuilder.toString().split(",")));
            details.add(detail);
        }

        req.setPurchaserID(bean.getPurchaserID());
        req.setDetails(details);
        req.setPurchaserName(bean.getPurchaserName());
        req.setPurchaserShopIDs(Arrays.asList(bean.getPurchaserShopIDs().split(",")));
        req.setType(2);
        baseReq.setData(req);
        GoodsService.INSTANCE.setOrientation(baseReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<Object>() {
                    @Override
                    public void onSuccess(Object resp) {
                        mView.addSuccess();
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void getOrientation() {
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("mainID", mView.getMainID())
                .create();
        GoodsService.INSTANCE.getOrientationDetail(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<OrientationDetailRes>() {
                    @Override
                    public void onSuccess(OrientationDetailRes resp) {
                        mView.setView(resp.getProductList());
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void getGroupInfo(String purchaserID) {
        BaseReq<GroupInfoReq> baseReq = new BaseReq<>();
        GroupInfoReq groupInfoReq = new GroupInfoReq();
        groupInfoReq.setGroupID(purchaserID);
        baseReq.setData(groupInfoReq);
        UserService.INSTANCE.getGroupInfo(baseReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<GroupInfoResp>() {
                    @Override
                    public void onSuccess(GroupInfoResp resp) {
                        mView.setPurchaserUrl(resp.getGroupLogoUrl());
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }


    @Override
    public void register(IBlackDetailContract.IDetailView view) {
        this.mView = view;
    }

    static BlackDetailPresenter newInstance() {
        return new BlackDetailPresenter();
    }

}
