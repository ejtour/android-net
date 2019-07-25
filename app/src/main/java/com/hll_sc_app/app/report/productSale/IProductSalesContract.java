package com.hll_sc_app.app.report.productSale;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.resp.product.ProductSaleResp;
import com.hll_sc_app.bean.report.resp.product.ProductSaleTop10Bean;

import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/24
 */

public interface IProductSalesContract {
    interface IProductSalesView extends ILoadView {
        void showProductSales(ProductSaleResp resp);

        void showTop10(List<ProductSaleTop10Bean> resp);
    }

    interface IProductSalesPresenter extends IPresenter<IProductSalesView> {
        void queryProductSales();

        void queryProductSalesTop10();
    }
}
