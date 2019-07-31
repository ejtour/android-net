package com.hll_sc_app.rest;

import com.alibaba.sdk.android.ams.common.util.StringUtil;
import com.hll_sc_app.api.ReportService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.req.BaseReportReqParam;
import com.hll_sc_app.bean.report.req.CustomerSaleReq;
import com.hll_sc_app.bean.report.req.ProductDetailReq;
import com.hll_sc_app.bean.report.resp.bill.CustomerSalesResp;
import com.hll_sc_app.bean.report.resp.bill.DateSaleAmountResp;
import com.hll_sc_app.bean.report.resp.group.PurchaserGroupBean;
import com.hll_sc_app.bean.report.resp.product.OrderDetailTotalResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author chukun
 * 报表请求实现
 */
public class ReportRest {

    /**
     * 商品统计（明细）
     *
     * @param req
     * @return
     */
    public static void queryProductDetailList(ProductDetailReq req, SimpleObserver<OrderDetailTotalResp> observer) {
        UserBean user = GreenDaoUtils.getUser();
        if (null == user) {
            return;
        }
        ReportService.INSTANCE
                .queryProductDetailList(
                        new BaseReq<>(req))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }


    /**
     * 日销售额查询
     *
     * @param req
     * @return
     */
    public static void queryDateSaleAmount(BaseReportReqParam req, SimpleObserver<DateSaleAmountResp> observer) {
        UserBean user = GreenDaoUtils.getUser();
        if (null == user) {
            return;
        }
        ReportService.INSTANCE
                .queryDateSaleAmount(
                        new BaseReq<>(req))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 客户销售/门店汇总查询
     *
     * @param req
     * @return
     */
    public static void queryCustomerSales(CustomerSaleReq req, SimpleObserver<CustomerSalesResp> observer) {
        UserBean user = GreenDaoUtils.getUser();
        if (null == user) {
            return;
        }
        ReportService.INSTANCE
                .queryCustomerSales(
                        new BaseReq<>(req))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 客户明细，门店汇总搜索
     * @param groupType
     * @param searchParam
     * @param pageNo
     * @param pageSize
     * @param observer
     */
    public static void queryPurchaser(String groupType,String searchParam,Integer pageNo,Integer pageSize, SimpleObserver<List<PurchaserGroupBean>> observer){
        UserBean user = GreenDaoUtils.getUser();
        if(null == user){
            return;
        }
        ReportService.INSTANCE.queryPurchaser(
                       BaseMapReq.newBuilder()
                                 .put("groupType",groupType)
                                 .put("searchParam", StringUtil.isEmpty(searchParam)?"":searchParam)
                                 .put("pageNo",pageNo==null?"1":pageNo+"")
                                 .put("pageSize",pageSize==null?"50":pageSize+"")
                                 .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
