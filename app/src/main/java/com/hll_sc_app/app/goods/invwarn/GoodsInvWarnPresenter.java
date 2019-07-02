package com.hll_sc_app.app.goods.invwarn;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.goods.HouseBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 代仓商品库存预警
 *
 * @author zhuyingsong
 * @date 2019/7/2
 */
public class GoodsInvWarnPresenter implements GoodsInvWarnContract.IGoodsInvWarnPresenter {
    private GoodsInvWarnContract.IGoodsInvWarnView mView;
    private List<HouseBean> mListHouseBean;

    static GoodsInvWarnPresenter newInstance() {
        return new GoodsInvWarnPresenter();
    }

    @Override
    public void start() {
        queryHouseList(false);
    }

    @Override
    public void register(GoodsInvWarnContract.IGoodsInvWarnView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryHouseList(boolean show) {
        if (show && !CommonUtils.isEmpty(mListHouseBean)) {
            mView.showHouseWindow(mListHouseBean);
            return;
        }
        BaseMapReq req = BaseMapReq.newBuilder().put("groupID", UserConfig.getGroupID()).create();
        GoodsService.INSTANCE.queryHouseList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<List<HouseBean>>() {
                @Override
                public void onSuccess(List<HouseBean> houseBeans) {
                    mListHouseBean = houseBeans;
                    if (show) {
                        mView.showHouseWindow(mListHouseBean);
                    } else {
                        if (CommonUtils.isEmpty(mListHouseBean)) {
                            mView.showToast("没有仓库数据");
                        } else {
                            mListHouseBean.get(0).setSelect(true);
                            mView.showSelectHouse(mListHouseBean.get(0));
                        }
                    }
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    @Override
    public void queryGoodsInvList(boolean showLoading) {

    }

    @Override
    public void queryMoreGoodsInvList() {

    }
}
