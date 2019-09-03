package com.hll_sc_app.app.inspection.list;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.PurchaserBean;
import com.hll_sc_app.bean.filter.DateShopParam;
import com.hll_sc_app.bean.inspection.InspectionResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Common;
import com.hll_sc_app.rest.Inspection;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/26
 */

public class InspectionListPresenter implements IInspectionListContract.IInspectionListPresenter {
    private DateShopParam mParam;
    private IInspectionListContract.IInspectionListView mView;
    private int mPageNum;

    public static InspectionListPresenter newInstance(DateShopParam param) {
        InspectionListPresenter presenter = new InspectionListPresenter();
        presenter.mParam = param;
        return presenter;
    }

    private InspectionListPresenter() {
    }

    @Override
    public void start() {
        getPurchaserList();
        reload();
    }

    @Override
    public void reload() {
        mPageNum = 1;
        load(true);
    }

    @Override
    public void loadMore() {
        load(false);
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        load(false);
    }

    @Override
    public void getPurchaserList() {
        Common.queryPurchaserList("inspectionBill", "", new SimpleObserver<List<PurchaserBean>>(mView) {
            @Override
            public void onSuccess(List<PurchaserBean> purchaserBeans) {
                List<com.hll_sc_app.bean.goods.PurchaserBean> beans = new ArrayList<>();
                if (!CommonUtils.isEmpty(purchaserBeans)) {
                    com.hll_sc_app.bean.goods.PurchaserBean all = new com.hll_sc_app.bean.goods.PurchaserBean();
                    all.setPurchaserName("全部");
                    beans.add(all);
                    for (PurchaserBean bean : purchaserBeans) {
                        com.hll_sc_app.bean.goods.PurchaserBean purchaserBean = new com.hll_sc_app.bean.goods.PurchaserBean();
                        purchaserBean.setPurchaserID(bean.getPurchaserID());
                        purchaserBean.setPurchaserName(bean.getPurchaserName());
                        beans.add(purchaserBean);
                    }
                }
                mView.cachePurchaserList(beans);
            }
        });
    }

    private void load(boolean showLoading) {
        Inspection.getInspectionList(mPageNum,
                mParam.getFormatStartDate(), mParam.getFormatEndDate(),
                mParam.getShopIDs(), new SimpleObserver<InspectionResp>(mView, showLoading) {
                    @Override
                    public void onSuccess(InspectionResp inspectionResp) {
                        mView.showList(inspectionResp.getList(), mPageNum > 1);
                        if (CommonUtils.isEmpty(inspectionResp.getList())) return;
                        mPageNum++;
                    }
                });
    }

    @Override
    public void register(IInspectionListContract.IInspectionListView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
