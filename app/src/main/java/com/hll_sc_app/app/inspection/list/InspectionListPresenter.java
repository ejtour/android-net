package com.hll_sc_app.app.inspection.list;

import android.text.TextUtils;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.PurchaserBean;
import com.hll_sc_app.bean.common.PurchaserShopBean;
import com.hll_sc_app.bean.inspection.InspectionResp;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsParam;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Common;
import com.hll_sc_app.rest.Inspection;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/26
 */

public class InspectionListPresenter implements IInspectionListContract.IInspectionListPresenter {
    private OrderGoodsParam mParam;
    private IInspectionListContract.IInspectionListView mView;
    private int mPageNum;

    public static InspectionListPresenter newInstance(OrderGoodsParam param) {
        InspectionListPresenter presenter = new InspectionListPresenter();
        presenter.mParam = param;
        return presenter;
    }

    private InspectionListPresenter() {
    }

    @Override
    public void start() {
        getPurchaserList("");
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

    @Override
    public void getPurchaserList(String searchWords) {
        Common.queryPurchaserList("customerOrder", searchWords, new SimpleObserver<List<PurchaserBean>>(mView) {
            @Override
            public void onSuccess(List<PurchaserBean> purchaserBeans) {
                mView.refreshPurchaserList(purchaserBeans);
            }
        });
    }

    @Override
    public void getShopList(String purchaseID, String searchWords) {
        if (TextUtils.isEmpty(purchaseID)) return;
        Common.queryPurchaserShopList(purchaseID, "customerOrder", searchWords, new SimpleObserver<List<PurchaserShopBean>>(mView) {
            @Override
            public void onSuccess(List<PurchaserShopBean> purchaserShopBeans) {
                mView.refreshShopList(purchaserShopBeans);
            }
        });
    }
}
