package com.hll_sc_app.app.search;

import com.hll_sc_app.app.search.stratery.AfterSalesGoodsSearch;
import com.hll_sc_app.app.search.stratery.AptitudeGoodsSearch;
import com.hll_sc_app.app.search.stratery.BrandSearch;
import com.hll_sc_app.app.search.stratery.CommonSearch;
import com.hll_sc_app.app.search.stratery.CrmOrderShopSearch;
import com.hll_sc_app.app.search.stratery.CustomerNameSearch;
import com.hll_sc_app.app.search.stratery.DiscountSearch;
import com.hll_sc_app.app.search.stratery.EmployeeSearch;
import com.hll_sc_app.app.search.stratery.GoodsDemandSearch;
import com.hll_sc_app.app.search.stratery.GoodsNameSearch;
import com.hll_sc_app.app.search.stratery.GoodsSearch;
import com.hll_sc_app.app.search.stratery.IntentCustomerSearch;
import com.hll_sc_app.app.search.stratery.NameSearch;
import com.hll_sc_app.app.search.stratery.PartnerSearch;
import com.hll_sc_app.app.search.stratery.ProductNameSearch;
import com.hll_sc_app.app.search.stratery.ProductSearch;
import com.hll_sc_app.app.search.stratery.PurchaserNameSearch;
import com.hll_sc_app.app.search.stratery.PurchaserSearch;
import com.hll_sc_app.app.search.stratery.PurchaserShopSearch;
import com.hll_sc_app.app.search.stratery.QueryPurchaserSearch;
import com.hll_sc_app.app.search.stratery.SelectShopSearch;
import com.hll_sc_app.app.search.stratery.ShopAssociationSearch;
import com.hll_sc_app.app.search.stratery.ShopNameSearch;
import com.hll_sc_app.app.search.stratery.ShopSearch;
import com.hll_sc_app.app.search.stratery.SimpleSearch;
import com.hll_sc_app.app.search.stratery.SpecialDemandSearch;
import com.hll_sc_app.app.search.stratery.StaffDepartSearch;
import com.hll_sc_app.app.search.stratery.VisitPlanSearch;
import com.hll_sc_app.app.search.stratery.VisitRecordSearch;
import com.hll_sc_app.app.search.stratery.WarehouseSearch;

import java.util.HashMap;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/25
 */

class SearchFactory {
    private static HashMap<String, Class<? extends ISearchContract.ISearchStrategy>> ITEM_MAP = new HashMap<>();

    static {
        ITEM_MAP.put(AfterSalesGoodsSearch.class.getSimpleName(), AfterSalesGoodsSearch.class);
        ITEM_MAP.put(AptitudeGoodsSearch.class.getSimpleName(), AptitudeGoodsSearch.class);
        ITEM_MAP.put(BrandSearch.class.getSimpleName(), BrandSearch.class);
        ITEM_MAP.put(CrmOrderShopSearch.class.getSimpleName(), CrmOrderShopSearch.class);
        ITEM_MAP.put(CustomerNameSearch.class.getSimpleName(), CustomerNameSearch.class);
        ITEM_MAP.put(CommonSearch.class.getSimpleName(), CommonSearch.class);
        ITEM_MAP.put(DiscountSearch.class.getSimpleName(), DiscountSearch.class);
        ITEM_MAP.put(EmployeeSearch.class.getSimpleName(), EmployeeSearch.class);
        ITEM_MAP.put(GoodsDemandSearch.class.getSimpleName(), GoodsDemandSearch.class);
        ITEM_MAP.put(GoodsNameSearch.class.getSimpleName(), GoodsNameSearch.class);
        ITEM_MAP.put(GoodsSearch.class.getSimpleName(), GoodsSearch.class);
        ITEM_MAP.put(IntentCustomerSearch.class.getSimpleName(), IntentCustomerSearch.class);
        ITEM_MAP.put(NameSearch.class.getSimpleName(), NameSearch.class);
        ITEM_MAP.put(PartnerSearch.class.getSimpleName(), PartnerSearch.class);
        ITEM_MAP.put(ProductNameSearch.class.getSimpleName(), ProductNameSearch.class);
        ITEM_MAP.put(ProductSearch.class.getSimpleName(), ProductSearch.class);
        ITEM_MAP.put(PurchaserNameSearch.class.getSimpleName(), PurchaserNameSearch.class);
        ITEM_MAP.put(PurchaserSearch.class.getSimpleName(), PurchaserSearch.class);
        ITEM_MAP.put(PurchaserShopSearch.class.getSimpleName(), PurchaserShopSearch.class);
        ITEM_MAP.put(SelectShopSearch.class.getSimpleName(), SelectShopSearch.class);
        ITEM_MAP.put(ShopAssociationSearch.class.getSimpleName(), ShopAssociationSearch.class);
        ITEM_MAP.put(ShopNameSearch.class.getSimpleName(), ShopNameSearch.class);
        ITEM_MAP.put(ShopSearch.class.getSimpleName(), ShopSearch.class);
        ITEM_MAP.put(SimpleSearch.class.getSimpleName(), SimpleSearch.class);
        ITEM_MAP.put(SpecialDemandSearch.class.getSimpleName(), SpecialDemandSearch.class);
        ITEM_MAP.put(StaffDepartSearch.class.getSimpleName(), StaffDepartSearch.class);
        ITEM_MAP.put(VisitPlanSearch.class.getSimpleName(), VisitPlanSearch.class);
        ITEM_MAP.put(VisitRecordSearch.class.getSimpleName(), VisitRecordSearch.class);
        ITEM_MAP.put(WarehouseSearch.class.getSimpleName(), WarehouseSearch.class);
        ITEM_MAP.put(QueryPurchaserSearch.class.getSimpleName(), QueryPurchaserSearch.class);
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
