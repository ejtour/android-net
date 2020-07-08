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

public class DeliveryMenu implements IMenuStrategy {
    @Override
    public String getTitle() {
        return "配送管理";
    }

    @Override
    public List<MenuBean> getList() {
        List<MenuBean> list = new ArrayList<>();
        list.add(new MenuBean(R.drawable.ic_delivery_range, "配送范围设置", RouterConfig.DELIVERY_RANGE));
        list.add(new MenuBean(R.drawable.ic_delivery_minimum, "起送金额设置", RouterConfig.DELIVERY_MINIMUM));
        list.add(new MenuBean(R.drawable.ic_delivery_ageing, "配送时效设置", RouterConfig.DELIVERY_AGEING_MANAGE));
        list.add(new MenuBean(R.drawable.ic_delivery_next, "隔日配送商品管理", null) {
            @Override
            public boolean doFinally() {
                StockCheckSettingActivity.start(StockCheckSettingActivity.ACTION_NEXT_DAY);
                return true;
            }
        });
        list.add(new MenuBean(R.drawable.ic_delivery_type, "配送方式设置", RouterConfig.DELIVERY_TYPE_SET));
        return list;
    }
}
