package com.hll_sc_app.app.report.search;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.resp.group.PurchaserGroupBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;

import java.util.List;

/**
 * @author chukun
 * @since 2019/7/24
 */

public class ReportSearchPresenter implements IReportSearchContract.IReportSearchPresenter {

    private int mPageNum = 1;
    private int mTempPageSize = 50;

    IReportSearchContract.IReportSearchView mView;

    static ReportSearchPresenter newInstance() {
        return new ReportSearchPresenter();
    }

    @Override
    public void start() {
        querySearchList("0","",mPageNum,mTempPageSize);
    }

    @Override
    public void register(IReportSearchContract.IReportSearchView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void querySearchList(String groupType, String searchWord, Integer pageNo, Integer pageSize) {
        Report.queryPurchaser(groupType, searchWord, pageNo, pageSize, new SimpleObserver<List<PurchaserGroupBean>>(mView) {
            @Override
            public void onSuccess(List<PurchaserGroupBean> list) {
                mView.showSearchList(list);
            }
        });
    }
}
