package com.hll_sc_app.app.warehouse;

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
 * 代仓客户列表
 *
 * @author zhuyingsong
 * @date 2019/8/5
 */
public class WarehouseListPresenter implements WarehouseListContract.IWarehouseListPresenter {
    private WarehouseListContract.IWarehouseListView mView;
    private int mPageNum;
    private int mTempPageNum;

    static WarehouseListPresenter newInstance() {
        return new WarehouseListPresenter();
    }

    @Override
    public void start() {
        queryWarehouseList(true);
    }

    @Override
    public void register(WarehouseListContract.IWarehouseListView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryWarehouseList(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toQueryWarehouseList(showLoading);
    }

    @Override
    public void queryMoreWarehouseList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryWarehouseList(false);
    }

    @Override
    public void delWarehouseList(String groupId) {
        BaseMapReq.Builder builder = BaseMapReq.newBuilder();
        builder.put("type", "1");
        if (UserConfig.isSelfOperated()) {
            builder
                .put("originator", "1")
                .put("groupID", UserConfig.getGroupID())
                .put("purchaserID", groupId);
        } else {
            builder
                .put("originator", "0")
                .put("groupID", groupId)
                .put("purchaserID", UserConfig.getGroupID());
        }
        WarehouseService.INSTANCE
            .delWarehouse(builder.create())
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object resp) {
                    queryWarehouseList(true);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    private void toQueryWarehouseList(boolean showLoading) {
        BaseMapReq.Builder builder = BaseMapReq.newBuilder();
        builder
            .put("actionType", "formalSigned")
            .put("name", mView.getSearchParam())
            .put("pageNum", String.valueOf(mTempPageNum))
            .put("pageSize", "20")
            .put("source", "app");
        if (UserConfig.isSelfOperated()) {
            builder
                .put("groupID", UserConfig.getGroupID())
                .put("originator", "1");
        } else {
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
