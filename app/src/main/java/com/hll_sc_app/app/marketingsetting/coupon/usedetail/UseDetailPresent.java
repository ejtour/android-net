package com.hll_sc_app.app.marketingsetting.coupon.usedetail;

import com.hll_sc_app.api.MarketingSettingService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.marketingsetting.CouponUseDetailListReq;
import com.hll_sc_app.bean.marketingsetting.CouponUseDetailListResp;

public class UseDetailPresent implements IUseDetailContract.IPresent {
    private IUseDetailContract.IView mView;

    private int pageNum = 1;
    private int pageTempNum = 1;
    private int pageSize = 20;

    public static UseDetailPresent newInstance() {
        return new UseDetailPresent();
    }

    @Override
    public void register(IUseDetailContract.IView view) {
        mView = view;
    }

    @Override
    public void getMarketingDetail(boolean isShowLoading) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        CouponUseDetailListReq req = new CouponUseDetailListReq();
        req.setPageNum(pageTempNum);
        req.setPageSize(pageSize);
        req.setActionType(1);
        req.setCouponStatus(mView.getCouponStatus());
        req.setGroupID(userBean.getGroupID());
        req.setDiscountID(mView.getDiscountID());

        getMarketingDetailObsevable(req, new SimpleObserver<CouponUseDetailListResp>(mView, isShowLoading) {
            @Override
            public void onSuccess(CouponUseDetailListResp resp) {
                mView.showDetail(resp, pageTempNum > 1);
                pageNum = pageTempNum;
            }

            @Override
            public void onFailure(UseCaseException e) {
                super.onFailure(e);
                pageTempNum = pageNum;
            }
        });
    }


    public static void getMarketingDetailObsevable(CouponUseDetailListReq req, SimpleObserver<CouponUseDetailListResp> observer) {
        BaseReq<CouponUseDetailListReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        MarketingSettingService.INSTANCE
                .getCouponUseDetailList(baseReq)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .subscribe(observer);
    }

    @Override
    public void refresh() {
        pageTempNum = 1;
        getMarketingDetail(false);
    }

    @Override
    public void getMore() {
        pageTempNum++;
        getMarketingDetail(false);
    }
}
