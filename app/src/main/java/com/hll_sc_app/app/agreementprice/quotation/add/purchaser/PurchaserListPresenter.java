package com.hll_sc_app.app.agreementprice.quotation.add.purchaser;

import com.hll_sc_app.api.AgreementPriceService;
import com.hll_sc_app.app.agreementprice.quotation.QuotationFragmentPresenter;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.agreementprice.quotation.GroupBean;
import com.hll_sc_app.bean.agreementprice.quotation.GroupInfoResp;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationBean;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 选择合作采购商
 *
 * @author zhuyingsong
 * @date 2019/7/9
 */
public class PurchaserListPresenter implements PurchaserListContract.IPurchaserListPresenter {
    private PurchaserListContract.IPurchaserListView mView;
    private int mPageNum;
    private int mTempPageNum;

    static PurchaserListPresenter newInstance() {
        return new PurchaserListPresenter();
    }

    @Override
    public void start() {
        // no-op
    }

    @Override
    public void register(PurchaserListContract.IPurchaserListView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryCooperationPurchaserList() {
        QuotationFragmentPresenter.getCooperationPurchaserObservable(mView.getSearchParam())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<List<PurchaserBean>>() {
                @Override
                public void onSuccess(List<PurchaserBean> result) {
                    mView.showPurchaserList(transformPurchaserBean(result), false, 0);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showToast(e.getMessage());
                }
            });
    }

    private List<QuotationBean> transformPurchaserBean(List<PurchaserBean> list) {
        List<QuotationBean> quotationBeans = new ArrayList<>();
        if (!CommonUtils.isEmpty(list)) {
            for (PurchaserBean bean : list) {
                QuotationBean quotationBean = new QuotationBean();
                quotationBean.setPurchaserID(bean.getPurchaserID());
                quotationBean.setPurchaserName(bean.getPurchaserName());
                quotationBeans.add(quotationBean);
            }
        }
        return quotationBeans;
    }

    @Override
    public void queryCooperationGroupList(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toCooperationGroupList(showLoading);
    }

    @Override
    public void queryMoreCooperationGroupList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toCooperationGroupList(false);
    }

    private void toCooperationGroupList(boolean showLoading) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("pageNum", String.valueOf(mTempPageNum))
            .put("pageSize", "20")
            .put("name", mView.getSearchParam())
            .put("source", "app")
            .put("originator", "1")
            .put("groupID", UserConfig.getGroupID())
            .put("actionType", "formalSigned")
            .create();
        AgreementPriceService.INSTANCE.queryCooperationGroupList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> {
                if (showLoading) {
                    mView.showLoading();
                }
            })
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<GroupInfoResp>() {
                @Override
                public void onSuccess(GroupInfoResp result) {
                    mPageNum = mTempPageNum;
                    mView.showPurchaserList(transformGroupBean(result.getGroupInfos()), mPageNum != 1,
                        result.getTotalNum());
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showToast(e.getMessage());
                }
            });

    }

    private List<QuotationBean> transformGroupBean(List<GroupBean> list) {
        List<QuotationBean> quotationBeans = new ArrayList<>();
        if (!CommonUtils.isEmpty(list)) {
            for (GroupBean bean : list) {
                QuotationBean quotationBean = new QuotationBean();
                quotationBean.setPurchaserID(bean.getGroupID());
                quotationBean.setPurchaserName(bean.getGroupName());
                quotationBeans.add(quotationBean);
            }
        }
        return quotationBeans;
    }

}
