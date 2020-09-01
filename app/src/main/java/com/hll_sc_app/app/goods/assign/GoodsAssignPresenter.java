package com.hll_sc_app.app.goods.assign;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.goods.GoodsAssignBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/8/25
 */
public class GoodsAssignPresenter implements IGoodsAssignContract.IGoodsAssignPresenter {
    private IGoodsAssignContract.IGoodsAssignView mView;
    private int mPageNum;

    public static GoodsAssignPresenter newInstance() {
        return new GoodsAssignPresenter();
    }

    private GoodsAssignPresenter() {
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        load(false);
    }

    @Override
    public void loadMore() {
        load(false);
    }

    @Override
    public void del(String id) {
        SimpleObserver<Object> observer = new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.delSuccess();
            }
        };
        GoodsService.INSTANCE.cancelAssign(BaseMapReq.newBuilder()
                .put("mainID", id)
                .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(observer);
    }

    @Override
    public void register(IGoodsAssignContract.IGoodsAssignView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    private void load(boolean showLoading) {
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("pageNum", String.valueOf(mPageNum))
                .put("pageSize", "20")
                .put("type", String.valueOf(mView.getType()))
                .put("supplierID", UserConfig.getGroupID())
                .create();
        SimpleObserver<SingleListResp<GoodsAssignBean>> observer = new SimpleObserver<SingleListResp<GoodsAssignBean>>(mView, showLoading) {
            @Override
            public void onSuccess(SingleListResp<GoodsAssignBean> goodsAssignBeanSingleListResp) {
                mView.setData(goodsAssignBeanSingleListResp.getRecords(), mPageNum > 1);
                if (CommonUtils.isEmpty(goodsAssignBeanSingleListResp.getRecords())) return;
                mPageNum++;
            }
        };
        GoodsService.INSTANCE.getAssignList(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(observer);
    }
}
