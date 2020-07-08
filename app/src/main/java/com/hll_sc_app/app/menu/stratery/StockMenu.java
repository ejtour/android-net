package com.hll_sc_app.app.menu.stratery;

import com.hll_sc_app.R;
import com.hll_sc_app.app.menu.IMenuStrategy;
import com.hll_sc_app.app.stockmanage.stockchecksetting.StockCheckSettingActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.menu.MenuBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/12
 */

public class StockMenu implements IMenuStrategy {

    @Override
    public String getTitle() {
        return "库存管理";
    }

    @Override
    public List<MenuBean> getList() {
        List<MenuBean> list = new ArrayList<>();
        list.add(new MenuBean(R.drawable.ic_house_blue, "仓库管理", RouterConfig.ACTIVITY_DEPOT));
        list.add(new MenuBean(R.drawable.ic_house_query_blue, "库存查询", RouterConfig.ACTIVITY_STOCK_QUERY_LIST));
        list.add(new MenuBean(R.drawable.ic_house_square_double_blue, "商品库存校验设置", null) {
            @Override
            public boolean doFinally() {
                StockCheckSettingActivity.start(StockCheckSettingActivity.ACTION_STOCK_CHECK);
                return true;
            }
        });
        list.add(new MenuBean(R.drawable.ic_book_blue, "库存日志查询", RouterConfig.ACTIVITY_STOCK_LOG_QUERY));
        list.add(new MenuBean(R.drawable.ic_square_arrow_blue, "客户发货仓库管理", RouterConfig.ACTIVITY_STOCK_CUSTOMER_SEND));
        list.add(new MenuBean(R.drawable.ic_square_query_blue, "采购单查询", RouterConfig.STOCK_PURCHASER_ORDER));
        return list;
    }
}
