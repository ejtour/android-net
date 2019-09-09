package com.hll_sc_app.app.search;

import com.hll_sc_app.app.marketingsetting.product.MarketingProductSearch;
import com.hll_sc_app.app.marketingsetting.product.check.SelectProductListActivity;
import com.hll_sc_app.app.search.stratery.BrandSearch;
import com.hll_sc_app.app.search.stratery.CommonSearch;
import com.hll_sc_app.app.search.stratery.CooperationSearch;
import com.hll_sc_app.app.search.stratery.CrmOrderSearch;
import com.hll_sc_app.app.search.stratery.CustomerLackSearch;
import com.hll_sc_app.app.search.stratery.CustomerSendStockSearch;
import com.hll_sc_app.app.search.stratery.EmployeeSearch;
import com.hll_sc_app.app.search.stratery.GoodsInvWarnSearch;
import com.hll_sc_app.app.search.stratery.GoodsRelevanceListSearch;
import com.hll_sc_app.app.search.stratery.GoodsRelevanceSearch;
import com.hll_sc_app.app.search.stratery.GoodsSearch;
import com.hll_sc_app.app.search.stratery.GoodsTemplateSearch;
import com.hll_sc_app.app.search.stratery.GoodsTopSearch;
import com.hll_sc_app.app.search.stratery.InspectLackDetailSearch;
import com.hll_sc_app.app.search.stratery.OrderSearch;
import com.hll_sc_app.app.search.stratery.RefundSearch;
import com.hll_sc_app.app.search.stratery.SalesManSearch;
import com.hll_sc_app.app.search.stratery.SelectGroupSearch;
import com.hll_sc_app.app.search.stratery.SelectShopSearch;
import com.hll_sc_app.app.search.stratery.ShopSearch;
import com.hll_sc_app.app.search.stratery.StockCheckSearch;
import com.hll_sc_app.app.search.stratery.StockLogSearch;
import com.hll_sc_app.app.search.stratery.WarehouseSearch;

import java.util.HashMap;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/25
 */

class SearchFactory {
    private static HashMap<String, Class<? extends ISearchContract.ISearchStrategy>> ITEM_MAP = new HashMap<>();

    static {
        ITEM_MAP.put(BrandSearch.class.getSimpleName(), BrandSearch.class);
        ITEM_MAP.put(CommonSearch.class.getSimpleName(), CommonSearch.class);
        ITEM_MAP.put(CooperationSearch.class.getSimpleName(), CooperationSearch.class);
        ITEM_MAP.put(EmployeeSearch.class.getSimpleName(), EmployeeSearch.class);
        ITEM_MAP.put(GoodsInvWarnSearch.class.getSimpleName(), GoodsInvWarnSearch.class);
        ITEM_MAP.put(GoodsRelevanceListSearch.class.getSimpleName(), GoodsRelevanceListSearch.class);
        ITEM_MAP.put(GoodsRelevanceSearch.class.getSimpleName(), GoodsRelevanceSearch.class);
        ITEM_MAP.put(GoodsSearch.class.getSimpleName(), GoodsSearch.class);
        ITEM_MAP.put(GoodsTemplateSearch.class.getSimpleName(), GoodsTemplateSearch.class);
        ITEM_MAP.put(GoodsTopSearch.class.getSimpleName(), GoodsTopSearch.class);
        ITEM_MAP.put(OrderSearch.class.getSimpleName(), OrderSearch.class);
        ITEM_MAP.put(WarehouseSearch.class.getSimpleName(), WarehouseSearch.class);
        ITEM_MAP.put(SalesManSearch.class.getSimpleName(), SalesManSearch.class);
        ITEM_MAP.put(RefundSearch.class.getSimpleName(), RefundSearch.class);
        ITEM_MAP.put(MarketingProductSearch.class.getSimpleName(), MarketingProductSearch.class);
        ITEM_MAP.put(SelectProductListActivity.MarketingSelectProductSearch.class.getSimpleName(), SelectProductListActivity.MarketingSelectProductSearch.class);
        ITEM_MAP.put(SelectGroupSearch.class.getSimpleName(), SelectGroupSearch.class);
        ITEM_MAP.put(SelectShopSearch.class.getSimpleName(), SelectShopSearch.class);
        ITEM_MAP.put(CustomerLackSearch.class.getSimpleName(), CustomerLackSearch.class);
        ITEM_MAP.put(InspectLackDetailSearch.class.getSimpleName(), InspectLackDetailSearch.class);
        ITEM_MAP.put(ShopSearch.class.getSimpleName(), ShopSearch.class);
        ITEM_MAP.put(StockLogSearch.class.getSimpleName(), StockLogSearch.class);
        ITEM_MAP.put(CustomerSendStockSearch.class.getSimpleName(), CustomerSendStockSearch.class);
        ITEM_MAP.put(CrmOrderSearch.class.getSimpleName(), CrmOrderSearch.class);
        ITEM_MAP.put(StockCheckSearch.class.getSimpleName(), StockCheckSearch.class);
    }

    static ISearchContract.ISearchStrategy getSearchStrategy(String key) {
        Class<? extends ISearchContract.ISearchStrategy> clazz = ITEM_MAP.get(key);
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
