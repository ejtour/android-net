package com.hll_sc_app.app.select.group.goodsassign;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserResp;
import com.hll_sc_app.bean.goods.GoodsAssignBean;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/8/27
 */
public class SelectGroupPresenter implements ISelectGroupContract.ISelectGroupPresenter {
    private ISelectGroupContract.ISelectGroupView mView;
    private int mPageNum;

    public static SelectGroupPresenter newInstance() {
        return new SelectGroupPresenter();
    }

    private SelectGroupPresenter() {
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    @Override
    public void register(ISelectGroupContract.ISelectGroupView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    private void load(boolean showLoading) {
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("actionType", "formalCooperation")
                .put("groupID", UserConfig.getGroupID())
                .put("name", mView.getSearchWords())
                .put("pageNo", String.valueOf(mPageNum))
                .put("pageSize", "20")
                .put("originator", "1")
                .create();
        SimpleObserver<CooperationPurchaserResp> observer = new SimpleObserver<CooperationPurchaserResp>(mView, showLoading) {
            @Override
            public void onSuccess(CooperationPurchaserResp cooperationPurchaserResp) {
                List<PurchaserBean> records = cooperationPurchaserResp.getRecords();
                List<GoodsAssignBean> list = new ArrayList<>();
                if (!CommonUtils.isEmpty(records)) {
                    for (PurchaserBean record : records) {
                        GoodsAssignBean bean = new GoodsAssignBean();
                        list.add(bean);
                        bean.setPurchaserID(record.getPurchaserID());
                        bean.setPurchaserName(record.getPurchaserName());
                    }
                }
                mView.setData(list, mPageNum > 1);
                if (list.size() == 0) return;
                mPageNum++;
            }
        };
        CooperationPurchaserService.INSTANCE.queryCooperationPurchaserList(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
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
}
