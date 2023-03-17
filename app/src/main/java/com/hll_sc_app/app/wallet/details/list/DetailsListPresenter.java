package com.hll_sc_app.app.wallet.details.list;

import android.text.TextUtils;

import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.export.ExportReq;
import com.hll_sc_app.bean.filter.WalletDetailsParam;
import com.hll_sc_app.bean.wallet.details.DetailsListResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Common;
import com.hll_sc_app.rest.Wallet;
import com.hll_sc_app.utils.Utils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/5/6
 */
public class DetailsListPresenter implements IDetailsListContract.IDetailsListPresenter {
    private IDetailsListContract.IDetailsListView mView;
    private int pageNum;
    private WalletDetailsParam mParam;

    public static DetailsListPresenter newInstance(WalletDetailsParam param) {
        DetailsListPresenter presenter = new DetailsListPresenter();
        presenter.mParam = param;
        return presenter;
    }

    @Override
    public void start() {
        pageNum = 1;
        getDetailsList(true);
    }

    private void getDetailsList(boolean showLoading) {
        Wallet.getWalletDetailsList(pageNum, mParam.getFormatStartDate(), mParam.getFormatEndDate(),
                mParam.getSettleUnitID(), mParam.getTransType(),
                new SimpleObserver<DetailsListResp>(mView, showLoading) {
                    @Override
                    public void onSuccess(DetailsListResp detailsListResp) {
                        mView.setDetailsList(detailsListResp, pageNum > 1);
                        if (CommonUtils.isEmpty(detailsListResp.getRecords())) return;
                        pageNum++;
                    }
                });
    }

    @Override
    public void refresh() {
        pageNum = 1;
        getDetailsList(false);
    }

    @Override
    public void loadMore() {
        getDetailsList(false);
    }

    @Override
    public void export(String email) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) return;
        ExportReq req = new ExportReq();
        req.setEmail(email);
        req.setTypeCode("fnancialDetail");
        req.setUserID(userBean.getEmployeeID());
        if (!TextUtils.isEmpty(email)) req.setIsBindEmail("1");
        ExportReq.ParamsBean.FinancialParams financialParams = new ExportReq.ParamsBean.FinancialParams();
        ExportReq.ParamsBean params = new ExportReq.ParamsBean();
        params.setFnancialDetail(financialParams);
        req.setParams(params);
        financialParams.setBeginTime(mParam.getFormatStartDate());
        financialParams.setEndTime(mParam.getFormatEndDate());
        financialParams.setGroupID(userBean.getGroupID());
        financialParams.setSettleUnitID(mParam.getSettleUnitID());
        financialParams.setTransType(mParam.getTransType());
        Common.exportExcel(req, Utils.getExportObserver(mView, "shopmall-supplier"),"shopmall-supplier");
    }

    @Override
    public void register(IDetailsListContract.IDetailsListView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
