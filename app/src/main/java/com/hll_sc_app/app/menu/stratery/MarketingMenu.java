package com.hll_sc_app.app.menu.stratery;

import com.hll_sc_app.R;
import com.hll_sc_app.app.marketingsetting.product.ProductMarketingListActivity;
import com.hll_sc_app.app.menu.IMenuStrategy;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.menu.MenuBean;

import java.util.ArrayList;
import java.util.List;

import static com.hll_sc_app.app.marketingsetting.product.ProductMarketingListActivity.TYPE_ORDER;
import static com.hll_sc_app.app.marketingsetting.product.ProductMarketingListActivity.TYPE_PRODUCT;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/12
 */

public class MarketingMenu implements IMenuStrategy {
    @Override
    public String getTitle() {
        return "营销中心";
    }

    @Override
    public List<MenuBean> getList() {
        List<MenuBean> list = new ArrayList<>();
        list.add(new MenuBean(R.drawable.ic_division_label, "商品促销", null) {
            @Override
            public boolean doFinally() {
                ProductMarketingListActivity.start(TYPE_PRODUCT);
                return true;
            }
        });
        list.add(new MenuBean(R.drawable.ic_order_book, "订单促销", null) {
            @Override
            public boolean doFinally() {
                ProductMarketingListActivity.start(TYPE_ORDER);
                return true;
            }
        });
        list.add(new MenuBean(R.drawable.ic_coupon, "优惠券", RouterConfig.ACTIVITY_MARKETING_COUPON_LIST));
        return list;
    }
}
