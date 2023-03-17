package com.hll_sc_app.app.report.voucherconfirm.detail;

import static com.uber.autodispose.AutoDispose.autoDisposable;

import android.text.TextUtils;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.export.ExportReq;
import com.hll_sc_app.bean.report.customreceivequery.CustomReceiveListResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.hll_sc_app.utils.Utils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/9/21
 */
public class VoucherConfirmDetailPresenter implements IVoucherConfirmDetailContract.IVoucherConfirmDetailPresenter {
    private IVoucherConfirmDetailContract.IVoucherConfirmDetailView mView;
    private int mPageNum;

    public static VoucherConfirmDetailPresenter newInstance() {
        return new VoucherConfirmDetailPresenter();
    }

    private VoucherConfirmDetailPresenter() {
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    @Override
    public void register(IVoucherConfirmDetailContract.IVoucherConfirmDetailView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    private void load(boolean showLoading) {
        Report.queryVouchers(mView.getReq()
                .put("pageNo", String.valueOf(mPageNum))
                .put("pageSize", "20")
                .put("voucherIDs", "")
                .put("isAll", "")
                .create(), new SimpleObserver<SingleListResp<CustomReceiveListResp.RecordsBean>>(mView, showLoading) {
            @Override
            public void onSuccess(SingleListResp<CustomReceiveListResp.RecordsBean> recordsBeanSingleListResp) {
                mView.setData(recordsBeanSingleListResp.getRecords(), mPageNum > 1);
                if (CommonUtils.isEmpty(recordsBeanSingleListResp.getRecords())) return;
                mPageNum++;
            }
        });
    }

    @Override
    public void export(String email) {
        List<String> selectIds = mView.getSelectIds();
        /*if (selectIds.isEmpty()) {
            mView.exportFailure("导出数据为空");
            return;
        }*/
        UserBean userBean = GreenDaoUtils.getUser();
        ExportReq req = new ExportReq();
        req.setIsBindEmail(TextUtils.isEmpty(email) ? "0" : "1");
        req.setEmail(email);
        req.setTypeCode("confirm_voucher");
        req.setUserID(userBean.getEmployeeID());
        ExportReq.ParamsBean paramsBean = new ExportReq.ParamsBean();
        paramsBean.setConfirmVoucher(mView.getReq()
                .put("voucherIDs", selectIds)
                .put("isAll", "")
                .put("pageNo", "")
                .put("pageSize", "")
                .create().getData());
        req.setParams(paramsBean);
        BaseReq<ExportReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        GoodsService.INSTANCE.exportRecord(baseReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(Utils.getExportObserver(mView, "shopmall-supplier"));
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
    public void confirm() {
        Report.confirmVouchers(mView.getReq()
                .put("isAll", mView.isAllSelected() ? 1 : 0)
                .put("voucherIDs", TextUtils.join(",", mView.getSelectIds()))
                .put("pageNo", "")
                .put("pageSize", "")
                .create(), new SimpleObserver<MsgWrapper<Object>>(true, mView) {
            @Override
            public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                mView.success();
            }
        });
    }
}
