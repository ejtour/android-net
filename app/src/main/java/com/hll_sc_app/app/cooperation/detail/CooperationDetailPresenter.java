package com.hll_sc_app.app.cooperation.detail;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserDetail;
import com.hll_sc_app.bean.cooperation.CooperationShopReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 合作采购商详情
 *
 * @author zhuyingsong
 * @date 2019/7/16
 */
public class CooperationDetailPresenter implements CooperationDetailContract.ICooperationDetailPresenter {
    private int mPageNum;
    private CooperationDetailContract.ICooperationDetailView mView;

    static CooperationDetailPresenter newInstance() {
        return new CooperationDetailPresenter();
    }

    @Override
    public void start() {
        queryPurchaserDetail(false);
    }

    @Override
    public void register(CooperationDetailContract.ICooperationDetailView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryPurchaserDetail(boolean showLoading) {
        mPageNum = 1;
        toQueryPurchaserDetail(showLoading);
    }

    @Override
    public void queryMorePurchaserDetail() {
        toQueryPurchaserDetail(false);
    }

    @Override
    public void editCooperationPurchaserShop(CooperationShopReq req) {
        if (req == null) {
            return;
        }
        SimpleObserver<MsgWrapper<Object>> observer = new SimpleObserver<MsgWrapper<Object>>(true, mView) {
            @Override
            public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                if ("delete".equals(req.getActionType())) {
                    mView.delSuccess();
                } else {
                    queryPurchaserDetail(true);
                }
            }
        };
        BaseReq<CooperationShopReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        CooperationPurchaserService.INSTANCE.addCooperationShop(baseReq)
                .compose(ApiScheduler.getMsgLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(observer);
    }

    private void toQueryPurchaserDetail(boolean showLoading) {
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("pageNo", String.valueOf(mPageNum))
                .put("pageSize", "20")
                .put("originator", "1")
                .put("ignoreGroupActive", "1")
                .put("groupID", UserConfig.getGroupID())
                .put("purchaserID", mView.getPurchaserId())
                .put("searchParams", mView.getSearchWords())
                .create();
        SimpleObserver<CooperationPurchaserDetail> observer = new SimpleObserver<CooperationPurchaserDetail>(mView, showLoading) {
            @Override
            public void onSuccess(CooperationPurchaserDetail cooperationPurchaserDetail) {
                mView.showPurchaserDetail(cooperationPurchaserDetail, mPageNum != 1);
                if (CommonUtils.isEmpty(cooperationPurchaserDetail.getShopDetailList())) return;
                mPageNum++;
            }
        };
        CooperationPurchaserService.INSTANCE.queryCooperationPurchaserDetail(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(observer);
    }
}
