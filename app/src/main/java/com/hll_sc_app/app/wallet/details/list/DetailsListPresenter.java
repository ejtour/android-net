package com.hll_sc_app.app.wallet.details.list;

import android.text.TextUtils;
import android.util.SparseArray;

import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.export.ExportReq;
import com.hll_sc_app.bean.wallet.details.DetailsListResp;
import com.hll_sc_app.bean.wallet.details.DetailsRecord;
import com.hll_sc_app.bean.wallet.details.DetailsRecordWrapper;
import com.hll_sc_app.bean.wallet.details.WalletDetailsParam;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Common;
import com.hll_sc_app.rest.Wallet;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/5/6
 */
public class DetailsListPresenter implements IDetailsListContract.IDetailsListPresenter {
    private IDetailsListContract.IDetailsListView mView;
    private int pageNum;
    /**
     * 缓存请求到的数据
     */
    private SparseArray<List<DetailsRecord>> array = new SparseArray<>();
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
        Wallet.getWalletDetailsList(pageNum, mParam.getFormatBeginTime(), mParam.getFormatEndTime(),
                mParam.getSettleUnitID(), mParam.getTransType(),
                new SimpleObserver<DetailsListResp>(mView, showLoading) {
                    @Override
                    public void onSuccess(DetailsListResp detailsListResp) {
                        if (pageNum == 1) { // 第一页时清空列表
                            array.clear();
                        }
                        mView.setDetailsList(processDetailsList(detailsListResp.getRecords()));
                        mView.setEnableLoadMore(detailsListResp.getRecords() != null && detailsListResp.getRecords().size() == 20);
                        if (detailsListResp.getPageNo() == 0 || CommonUtils.isEmpty(detailsListResp.getRecords())) {
                            return;
                        }
                        pageNum = detailsListResp.getPageNo() + 1;
                    }
                });
    }

    /**
     * 处理获取的明细数据
     */
    private List<DetailsRecordWrapper> processDetailsList(List<DetailsRecord> records) {
        // 添加数据到数组中
        for (DetailsRecord record : records) {
            Date date = CalendarUtils.parse(record.getAccountTime());
            int yyyyMM = Integer.parseInt(CalendarUtils.format(date, Constants.UNSIGNED_YYYY_MM));
            List<DetailsRecord> list = array.get(yyyyMM);
            if (list == null) {
                list = new ArrayList<>();
                list.add(record);
                array.put(yyyyMM, list);
            } else {
                list.add(record);
            }
        }
        // 从数组中取值，添加进集合
        List<DetailsRecordWrapper> wrappers = new ArrayList<>();
        if (mParam.isFilter()) {
            String header;
            if (mParam.isRange()) {
                header = CalendarUtils.format(mParam.getBeginTime(), Constants.SIGNED_YYYY_MM_DD) + " - " +
                        CalendarUtils.format(mParam.getEndTime(), Constants.SIGNED_YYYY_MM_DD);
            } else {
                header = CalendarUtils.format(mParam.getBeginTime(), Constants.SIGNED_YYYY_MM);
            }
            DetailsRecordWrapper wrapper = new DetailsRecordWrapper(true, header);
            wrappers.add(wrapper);
        } else if (array.size() == 0) {
            wrappers.add(new DetailsRecordWrapper(true, CalendarUtils.format(mParam.getEndTime(), Constants.SIGNED_YYYY_MM)));
        }
        // SparseArray 中的 key 默认会升序排列
        for (int i = array.size() - 1; i >= 0; i--) {
            if (!mParam.isFilter()) {
                DetailsRecordWrapper wrapper = new DetailsRecordWrapper(true,
                        CalendarUtils.getDateFormatString(String.valueOf(array.keyAt(i)), Constants.UNSIGNED_YYYY_MM, Constants.SIGNED_YYYY_MM));
                wrappers.add(wrapper);
            }
            for (DetailsRecord record : array.valueAt(i)) {
                wrappers.add(new DetailsRecordWrapper(record));
            }
        }
        return wrappers;
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
        financialParams.setBeginTime(mParam.getFormatBeginTime());
        financialParams.setEndTime(mParam.getFormatEndTime());
        financialParams.setGroupID(userBean.getGroupID());
        financialParams.setSettleUnitID(mParam.getSettleUnitID());
        financialParams.setTransType(mParam.getTransType());
        Common.exportExcel(req, Utils.getExportObserver(mView));
    }

    @Override
    public void register(IDetailsListContract.IDetailsListView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
