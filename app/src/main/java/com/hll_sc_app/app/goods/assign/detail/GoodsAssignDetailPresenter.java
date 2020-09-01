package com.hll_sc_app.app.goods.assign.detail;

import android.text.TextUtils;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.goods.GoodsAssignBean;
import com.hll_sc_app.bean.goods.GoodsAssignDetailBean;
import com.hll_sc_app.bean.groupInfo.GroupInfoReq;
import com.hll_sc_app.bean.groupInfo.GroupInfoResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/8/25
 */
public class GoodsAssignDetailPresenter implements IGoodsAssignDetailContract.IGoodsAssignDetailPresenter {
    private final String mId;
    private final String mPurchaserID;
    private IGoodsAssignDetailContract.IGoodsAssignDetailView mView;

    public static GoodsAssignDetailPresenter newInstance(String id, String purchaserID) {
        return new GoodsAssignDetailPresenter(id, purchaserID);
    }

    private GoodsAssignDetailPresenter(String id, String purchaserID) {
        mId = id;
        mPurchaserID = purchaserID;
    }

    @Override
    public void save(GoodsAssignBean bean) {
        SimpleObserver<Object> observer = new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.saveSuccess();
            }
        };
        GoodsService.INSTANCE.saveAssignDetail(new BaseReq<>(bean.convertToReq()))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(observer);
    }

    @Override
    public void start() {
        loadPic();
        loadData();
    }

    private void loadPic() {
        BaseReq<GroupInfoReq> baseReq = new BaseReq<>();
        GroupInfoReq groupInfoReq = new GroupInfoReq();
        groupInfoReq.setGroupID(mPurchaserID);
        baseReq.setData(groupInfoReq);
        SimpleObserver<GroupInfoResp> observer = new SimpleObserver<GroupInfoResp>(mView) {
            @Override
            public void onSuccess(GroupInfoResp groupInfoResp) {
                mView.cacheGroupUrl(groupInfoResp.getGroupLogoUrl());
            }
        };
        UserService.INSTANCE.getGroupInfo(baseReq)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(observer);
    }

    private void loadData() {
        if (TextUtils.isEmpty(mId)) return;
        SimpleObserver<SingleListResp<GoodsAssignDetailBean>> observer = new SimpleObserver<SingleListResp<GoodsAssignDetailBean>>(mView) {
            @Override
            public void onSuccess(SingleListResp<GoodsAssignDetailBean> goodsAssignDetailBeanSingleListResp) {
                mView.setList(goodsAssignDetailBeanSingleListResp.getRecords());
            }
        };
        GoodsService.INSTANCE.getAssignDetail(BaseMapReq.newBuilder()
                .put("mainID", mId)
                .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(observer);
    }

    @Override
    public void register(IGoodsAssignDetailContract.IGoodsAssignDetailView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
