package com.hll_sc_app.app.complainmanage.add;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONArray;
import com.hll_sc_app.api.AfterSalesService;
import com.hll_sc_app.api.ComplainManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.aftersales.GenerateCompainResp;
import com.hll_sc_app.bean.complain.DropMenuBean;
import com.hll_sc_app.rest.Upload;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.io.File;
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
                .put("name", "ComplaintTypeEnum")
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
        Upload.imageUpload(file, new SimpleObserver<String>(mView) {
            @Override
            public void onSuccess(String s) {
                mView.showImage(s);
            }
        });
    }

    @Override
    public void saveComplain() {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseMapReq.Builder builder = BaseMapReq.newBuilder()
                .put("actionType", "1")
                .put("complaintExplain", mView.getExplain())
                .put("imgUrls", mView.getImgs())
                .put("reason", mView.getReason())
                .put("type", mView.getType())
                .put("source", "2")
                .put("supplyID", userBean.getGroupID())
                .put("supplyName", userBean.getGroupName())
                .put("supplyName", "2")
                .put("billID", mView.getBillID())
                .put("purchaserContact", mView.getPhone())
                .put("purchaserID", mView.getPurchaserID())
                .put("purchaserName", mView.getPurchaserName())
                .put("purchaserShopID", mView.getShopID())
                .put("purchaserShopName", mView.getShopName())
                .put("sourceClient", "1");//todo：需判断用户角色类型：6/1
        if (TextUtils.equals(mView.getType(), "1")) {
            builder.put("products", JSONArray.toJSONString(mView.getProducts()));
        }
        AfterSalesService.INSTANCE
                .generateComplain(builder.create())
                .doOnSubscribe(disposable -> {
                    mView.showLoading();
                })
                .doFinally(() -> mView.hideLoading())
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
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
}
