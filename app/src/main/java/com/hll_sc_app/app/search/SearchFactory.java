package com.hll_sc_app.app.search;

import com.hll_sc_app.app.search.stratery.BrandSearch;
import com.hll_sc_app.app.search.stratery.CommonSearch;
import com.hll_sc_app.app.search.stratery.CooperationSearch;
import com.hll_sc_app.app.search.stratery.EmployeeSearch;
import com.hll_sc_app.app.search.stratery.GoodsInvWarnSearch;
import com.hll_sc_app.app.search.stratery.GoodsRelevanceListSearch;
import com.hll_sc_app.app.search.stratery.GoodsRelevanceSearch;
import com.hll_sc_app.app.search.stratery.GoodsSearch;
import com.hll_sc_app.app.search.stratery.GoodsTemplateSearch;
import com.hll_sc_app.app.search.stratery.GoodsTopSearch;
import com.hll_sc_app.app.search.stratery.OrderSearch;

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
