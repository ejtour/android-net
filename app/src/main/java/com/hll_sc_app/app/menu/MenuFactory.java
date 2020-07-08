package com.hll_sc_app.app.menu;

import com.hll_sc_app.app.menu.stratery.AccountMenu;
import com.hll_sc_app.app.menu.stratery.DeliveryMenu;
import com.hll_sc_app.app.menu.stratery.MarketingMenu;
import com.hll_sc_app.app.menu.stratery.PriceMenu;
import com.hll_sc_app.app.menu.stratery.ReconcileMenu;
import com.hll_sc_app.app.menu.stratery.ReportMenu;
import com.hll_sc_app.app.menu.stratery.SettingMenu;
import com.hll_sc_app.app.menu.stratery.StockMenu;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/12
 */

public class MenuFactory {
    private static final Map<String, Class<? extends IMenuStrategy>> ITEM_MAP = new HashMap<>();

    static {
        ITEM_MAP.put(AccountMenu.class.getSimpleName(), AccountMenu.class);
        ITEM_MAP.put(DeliveryMenu.class.getSimpleName(), DeliveryMenu.class);
        ITEM_MAP.put(MarketingMenu.class.getSimpleName(), MarketingMenu.class);
        ITEM_MAP.put(PriceMenu.class.getSimpleName(), PriceMenu.class);
        ITEM_MAP.put(ReconcileMenu.class.getSimpleName(), ReconcileMenu.class);
        ITEM_MAP.put(ReportMenu.class.getSimpleName(), ReportMenu.class);
        ITEM_MAP.put(SettingMenu.class.getSimpleName(), SettingMenu.class);
        ITEM_MAP.put(StockMenu.class.getSimpleName(), StockMenu.class);
    }

    static IMenuStrategy getMenuStrategy(String key) {
        Class<? extends IMenuStrategy> clazz = ITEM_MAP.get(key);
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
