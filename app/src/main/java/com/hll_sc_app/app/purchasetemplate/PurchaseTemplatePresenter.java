package com.hll_sc_app.app.purchasetemplate;

import android.text.TextUtils;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.common.PurchaserBean;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.cooperation.CooperationShopListResp;
import com.hll_sc_app.bean.export.ExportReq;
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.bean.user.PurchaseTemplateBean;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Common;
import com.hll_sc_app.rest.User;
import com.hll_sc_app.utils.Utils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/4
 */

public class PurchaseTemplatePresenter implements IPurchaseTemplateContract.IPurchaseTemplatePresenter {
    private IPurchaseTemplateContract.IPurchaseTemplateView mView;
    private int mPageNum;

    private PurchaseTemplatePresenter() {
    }

    public static PurchaseTemplatePresenter newInstance() {
        return new PurchaseTemplatePresenter();
    }

    @Override
    public void start() {
        searchPurchaser();
    }

    @Override
    public void searchPurchaser() {
        Common.queryPurchaserList("default", mView.getPurchaserWords(), new SimpleObserver<List<PurchaserBean>>(mView) {
            @Override
            public void onSuccess(List<PurchaserBean> list) {
                List<NameValue> nameValues = new ArrayList<>();
                if (!CommonUtils.isEmpty(list)) {
                    for (PurchaserBean bean : list) {
                        nameValues.add(new NameValue(bean.getPurchaserName(), bean.getPurchaserID()));
                    }
                }
                mView.setOrgData(0, nameValues);
            }
        });
    }

    @Override
    public void searchShop() {
        UserBean user = GreenDaoUtils.getUser();
        Common.queryCooperationShop(
                BaseMapReq.newBuilder()
                        .put("actionType", "cooperationDefault")
                        .put("groupID", user.getGroupID())
                        .put("pageNo", "1")
                        .put("pageSize", "999")
                        .put("purchaserID", mView.getPurchaserID())
                        .put("searchParams", mView.getShopWords())
                        .create(),
                new SimpleObserver<CooperationShopListResp>(mView) {
                    @Override
                    public void onSuccess(CooperationShopListResp cooperationShopListResp) {
                        List<NameValue> nameValues = new ArrayList<>();
                        if (!CommonUtils.isEmpty(cooperationShopListResp.getShopList())) {
                            for (PurchaserShopBean bean : cooperationShopListResp.getShopList()) {
                                nameValues.add(new NameValue(bean.getShopName(), bean.getShopID()));
                            }
                        }
                        mView.setOrgData(1, nameValues);
                    }
                });
    }

    @Override
    public void queryList() {
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
    public void export(String email) {
        UserBean user = GreenDaoUtils.getUser();
        ExportReq req = new ExportReq();
        req.setUserID(user.getEmployeeID());
        req.setEmail(email);
        req.setIsBindEmail(TextUtils.isEmpty(email) ? "0" : "1");
        req.setTypeCode("supplier_common_export");
        ExportReq.ParamsBean.CommonExport export = new ExportReq.ParamsBean.CommonExport();
        export.setActionType("supplier");
        export.setFlag(2);
        export.setSupplyID(user.getGroupID());
        export.setPurchaserID(mView.getPurchaserID());
        export.setShopID(mView.getShopID());
        ExportReq.ParamsBean bean = new ExportReq.ParamsBean();
        bean.setSupplierCommonExport(export);
        req.setParams(bean);
        SimpleObserver<ExportResp> observer = Utils.getExportObserver(mView);
        GoodsService.INSTANCE
                .exportRecord(new BaseReq<>(req))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    private void load(boolean showLoading) {
        User.queryPurchaseTemplate(mPageNum, mView.getPurchaserID(), mView.getShopID(), new SimpleObserver<SingleListResp<PurchaseTemplateBean>>(mView, showLoading) {
            @Override
            public void onSuccess(SingleListResp<PurchaseTemplateBean> purchaseTemplateBeanSingleListResp) {
                mView.setData(purchaseTemplateBeanSingleListResp.getRecords(), mPageNum > 1);
                if (CommonUtils.isEmpty(purchaseTemplateBeanSingleListResp.getRecords())) return;
                mPageNum++;
            }

            @Override
            public void onFailure(UseCaseException e) {
                if ("1001".equals(e.getCode()))
                    onSuccess(new SingleListResp<>());
                else super.onFailure(e);
            }
        });
    }

    @Override
    public void register(IPurchaseTemplateContract.IPurchaseTemplateView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
