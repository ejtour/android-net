package com.hll_sc_app.app.stockmanage.depot.category;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.goods.CustomCategoryBean;
import com.hll_sc_app.bean.goods.CustomCategoryResp;
import com.hll_sc_app.bean.stockmanage.DepotCategoryReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Stock;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/5/15
 */

class DepotCategoryPresenter implements IDepotCategoryContract.IDepotCategoryPresenter {
    private IDepotCategoryContract.IDepotCategoryView mView;


    public static DepotCategoryPresenter newInstance() {
        return new DepotCategoryPresenter();
    }

    private DepotCategoryPresenter() {
    }

    @Override
    public void save(DepotCategoryReq req) {
        Stock.setDepotCategory(req, new SimpleObserver<MsgWrapper<Object>>(true,mView) {
            @Override
            public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                mView.success();
            }
        });
    }

    @Override
    public void start() {
        SimpleObserver<CustomCategoryResp> observer = new SimpleObserver<CustomCategoryResp>(mView) {
            @Override
            public void onSuccess(CustomCategoryResp customCategoryResp) {
                customCategoryResp.processList();
                List<CustomCategoryBean> list = customCategoryResp.getList2();
                List<String> idList = mView.getSelectedIDList();
                if (!CommonUtils.isEmpty(idList) && !CommonUtils.isEmpty(list)) {
                    for (CustomCategoryBean bean : list) {
                        for (CustomCategoryBean subBean : bean.getSubList()) {
                            subBean.setChecked(idList.contains(subBean.getId()));
                        }
                    }
                }
                mView.setData(list);
            }
        };
        GoodsService.INSTANCE
                .queryCustomCategory(BaseMapReq.newBuilder()
                        .put("groupID", UserConfig.getGroupID())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    @Override
    public void register(IDepotCategoryContract.IDepotCategoryView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
