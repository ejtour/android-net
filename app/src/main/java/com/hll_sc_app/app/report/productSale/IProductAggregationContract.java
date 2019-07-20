package com.hll_sc_app.app.report.productSale;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.order.search.OrderSearchBean;
import com.hll_sc_app.bean.report.req.ProductSaleAggregationReq;
import com.hll_sc_app.bean.report.req.ProductSaleTopReq;
import com.hll_sc_app.bean.report.resp.product.ProductSaleAggregationResp;
import com.hll_sc_app.bean.report.resp.product.ProductSaleTop10Resp;
import com.hll_sc_app.bean.report.resp.product.ProductSaleTopDetail;

import java.util.List;

/**
 * @author chukun
 */

public interface IProductAggregationContract {
    interface IProductAggregationView extends ILoadView {


        /**
         *
         * @param resp 返回数据
         */
        void setProductAggregation(ProductSaleAggregationResp resp);

        void setProductTopList(List<ProductSaleTopDetail> details);

    }

    interface IProductAggregationPresenter extends IPresenter<IProductAggregationView> {
        /**
         * 请求商品销售汇总
         */
        void queryProductSaleAggregation(ProductSaleAggregationReq req);

        /**
         *请求商品销售top10
         */
        void queryProductSaleTop(ProductSaleTopReq req);
    }
}
