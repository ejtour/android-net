package com.hll_sc_app.app.report.productsale;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.filter.ProductSalesParam;
import com.hll_sc_app.bean.report.resp.product.ProductSaleResp;
import com.hll_sc_app.bean.report.resp.product.ProductSaleTop10Resp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/24
 */

public class ProductSalesPresenter implements IProductSalesContract.IProductSalesPresenter {
    private ProductSalesParam mParam;

    private ProductSalesPresenter() {
    }

    public static ProductSalesPresenter newInstance(ProductSalesParam param) {
        ProductSalesPresenter presenter = new ProductSalesPresenter();
        presenter.mParam = param;
        return presenter;
    }

    private IProductSalesContract.IProductSalesView mView;

    @Override
    public void queryProductSales() {
        Report.queryProductSales(mParam.getDateFlag(), mParam.getFormatStartDate(),
                mParam.getFormatEndDate(), new SimpleObserver<ProductSaleResp>(mView) {
                    @Override
                    public void onSuccess(ProductSaleResp resp) {
                        mView.showProductSales(resp);
                    }
                });
    }

    @Override
    public void queryProductSalesTop10() {
        Report.queryProductSalesTop10(mParam.getDateFlag(), mParam.getFormatStartDate(),
                mParam.getFormatEndDate(), mParam.getType(), new SimpleObserver<ProductSaleTop10Resp>(mView) {
                    @Override
                    public void onSuccess(ProductSaleTop10Resp resp) {
                        mView.showTop10(resp.getRecords());
                    }
                });
    }

    @Override
    public void start() {
        queryProductSales();
        queryProductSalesTop10();
    }

    @Override
    public void register(IProductSalesContract.IProductSalesView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
