package com.hll_sc_app.app.goodsdemand.search;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.IntentionCustomerBean;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserResp;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Common;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/21
 */

public class PurchaserSearchPresenter implements IPurchaserSearchContract.IPurchaserSearchPresenter {
    private IPurchaserSearchContract.IPurchaserSearchView mView;
    private int mPageNum;
    private int mType;

    private PurchaserSearchPresenter(int type) {
        mType = type;
    }

    public static PurchaserSearchPresenter newInstance(int type) {
        return new PurchaserSearchPresenter(type);
    }

    private void load(boolean showLoading) {
        UserBean user = GreenDaoUtils.getUser();
        if (mType == 0) {
            SimpleObserver<CooperationPurchaserResp> observer = new SimpleObserver<CooperationPurchaserResp>(mView, showLoading) {
                @Override
                public void onSuccess(CooperationPurchaserResp cooperationPurchaserResp) {
                    List<NameValue> list = new ArrayList<>();
                    if (!CommonUtils.isEmpty(cooperationPurchaserResp.getRecords())) {
                        for (PurchaserBean record : cooperationPurchaserResp.getRecords()) {
                            list.add(new NameValue(record.getPurchaserName(), record.getPurchaserID()));
                        }
                    }
                    mView.setData(list, mPageNum > 1);
                    if (list.size() == 0) return;
                    mPageNum++;
                }
            };
            CooperationPurchaserService.INSTANCE
                    .queryCooperationPurchaserList(BaseMapReq.newBuilder()
                            .put("actionType", "formalCooperation")
                            .put("groupID", user.getGroupID())
                            .put("originator", "1")
                            .put("pageNo", String.valueOf(mPageNum))
                            .put("pageSize", "20")
                            .put("name", mView.getSearchWords())
                            .put("salesmanID", user.getEmployeeID())
                            .create())
                    .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                    .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                    .subscribe(observer);
        } else {
            Common.searchIntentionCustomer(mPageNum, mView.getSearchWords(),
                    new SimpleObserver<SingleListResp<IntentionCustomerBean>>(mView, showLoading) {
                        @Override
                        public void onSuccess(SingleListResp<IntentionCustomerBean> intentionCustomerBeanSingleListResp) {
                            List<NameValue> list = new ArrayList<>();
                            if (!CommonUtils.isEmpty(intentionCustomerBeanSingleListResp.getRecords())) {
                                for (IntentionCustomerBean record : intentionCustomerBeanSingleListResp.getRecords()) {
                                    list.add(new NameValue(record.getCustomerName(), record.getId()));
                                }
                            }
                            mView.setData(list, mPageNum > 1);
                            if (list.size() == 0) return;
                            mPageNum++;
                        }
                    });
        }
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
    public void start() {
        mPageNum = 1;
        load(true);
    }

    @Override
    public void register(IPurchaserSearchContract.IPurchaserSearchView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
