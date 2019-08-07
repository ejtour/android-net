package com.hll_sc_app.app.warehouse.invite;

import com.hll_sc_app.api.WarehouseService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.warehouse.WarehouseListResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 代仓公司-我的申请
 *
 * @author zhuyingsong
 * @date 2019/8/5
 */
public class WarehouseInvitePresenter implements WarehouseInviteContract.IWarehouseInvitePresenter {
    private WarehouseInviteContract.IWarehouseInviteView mView;
    private int mPageNum;
    private int mTempPageNum;

    static WarehouseInvitePresenter newInstance() {
        return new WarehouseInvitePresenter();
    }

    @Override
    public void start() {
        queryWarehouseList(true);
    }

    @Override
    public void register(WarehouseInviteContract.IWarehouseInviteView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryWarehouseList(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toQueryPurchaserList(showLoading);
    }

    @Override
    public void queryMoreWarehouseList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryPurchaserList(false);
    }

    private void toQueryPurchaserList(boolean showLoading) {
        BaseMapReq.Builder builder = BaseMapReq.newBuilder();
        builder
            .put("actionType", "myApplication")
            .put("name", mView.getSearchParam())
            .put("pageNum", String.valueOf(mTempPageNum))
            .put("pageSize", "20")
            .put("source", "app");
        if (UserConfig.isSelfOperated()) {
            // 如果为自营则是代仓公司
            builder
                .put("groupID", UserConfig.getGroupID())
                .put("originator", "1");
        } else {
            // 非自营则是货主
            builder
                .put("purchaserID", UserConfig.getGroupID())
                .put("originator", "0");
        }
        WarehouseService.INSTANCE
            .queryWarehouseList(builder.create())
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> {
                if (showLoading) {
                    mView.showLoading();
                }
            })
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<WarehouseListResp>() {
                @Override
                public void onSuccess(WarehouseListResp resp) {
                    mPageNum = mTempPageNum;
                    mView.showWarehouseList(resp.getGroupInfos(), mPageNum != 1, resp.getTotalNum());
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }
}
