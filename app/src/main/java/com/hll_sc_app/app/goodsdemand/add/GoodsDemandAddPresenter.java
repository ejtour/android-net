package com.hll_sc_app.app.goodsdemand.add;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserResp;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.goodsdemand.GoodsDemandBean;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Other;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/6
 */

public class GoodsDemandAddPresenter implements IGoodsDemandAddContract.IGoodsDemandAddPresenter {
    private IGoodsDemandAddContract.IGoodsDemandAddView mView;

    private GoodsDemandAddPresenter() {
    }

    public static GoodsDemandAddPresenter newInstance() {
        return new GoodsDemandAddPresenter();
    }

    @Override
    public void start() {
        SimpleObserver<CooperationPurchaserResp> observer = new SimpleObserver<CooperationPurchaserResp>(mView) {
            @Override
            public void onSuccess(CooperationPurchaserResp cooperationPurchaserResp) {
                List<PurchaserBean> records = cooperationPurchaserResp.getRecords();
                if (records != null) {
                    if (records.size() == 1) {
                        PurchaserBean purchaserBean = records.get(0);
                        mView.setData(new NameValue(purchaserBean.getPurchaserName(), purchaserBean.getPurchaserID()));
                    } else if (records.size() > 1) {
                        load();
                    }
                }
            }
        };
        UserBean user = GreenDaoUtils.getUser();
        CooperationPurchaserService.INSTANCE
                .queryCooperationPurchaserList(BaseMapReq.newBuilder()
                        .put("actionType", "formalCooperation")
                        .put("groupID", user.getGroupID())
                        .put("originator", "1")
                        .put("pageNo", "1")
                        .put("pageSize", "2")
                        .put("salesmanID", user.getEmployeeID())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    private void load() {
        Other.queryGoodsDemand(1, 1, 0, null, new SimpleObserver<SingleListResp<GoodsDemandBean>>(mView) {
            @Override
            public void onSuccess(SingleListResp<GoodsDemandBean> goodsDemandBeanSingleListResp) {
                List<GoodsDemandBean> records = goodsDemandBeanSingleListResp.getRecords();
                if (records != null && records.size() == 1) {
                    GoodsDemandBean bean = records.get(0);
                    mView.setData(new NameValue(bean.getPurchaserName(), bean.getPurchaserID()));
                }
            }
        });
    }

    @Override
    public void register(IGoodsDemandAddContract.IGoodsDemandAddView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
