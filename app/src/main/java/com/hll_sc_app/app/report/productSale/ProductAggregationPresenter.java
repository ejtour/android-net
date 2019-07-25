package com.hll_sc_app.app.report.productSale;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.req.ProductSaleAggregationReq;
import com.hll_sc_app.bean.report.req.ProductSaleTopReq;
import com.hll_sc_app.bean.report.resp.product.ProductSaleAggregationResp;
import com.hll_sc_app.bean.report.resp.product.ProductSaleTop10Resp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.ReportRest;

import java.util.List;

public class ProductAggregationPresenter  implements  IProductAggregationContract.IProductAggregationPresenter{

    private  IProductAggregationContract.IProductAggregationView mView;

    private ProductAggregationPresenter(){}

    public static ProductAggregationPresenter newInstance() {
        return new ProductAggregationPresenter();
    }

    @Override
    public void queryProductSaleAggregation(ProductSaleAggregationReq req) {
        ReportRest.queryProductSaleAggregation(req, new SimpleObserver<ProductSaleAggregationResp>(mView,false) {
            @Override
            public void onSuccess(ProductSaleAggregationResp productSaleAggregationResp) {
                mView.setProductAggregation(productSaleAggregationResp);
            }
        });
    }

    @Override
    public void queryProductSaleTop(ProductSaleTopReq req) {
        ReportRest.queryProductSaleTop(req, new SimpleObserver<ProductSaleTop10Resp>(mView, false) {
            @Override
            public void onSuccess(ProductSaleTop10Resp result) {
                mView.setProductTopList(result.getRecords());
            }
        });
    }

    @Override
    public void register(IProductAggregationContract.IProductAggregationView view) {
       mView = CommonUtils.requireNonNull(view);
    }
}
