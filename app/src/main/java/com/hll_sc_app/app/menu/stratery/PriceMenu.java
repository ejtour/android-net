package com.hll_sc_app.app.menu.stratery;

import com.hll_sc_app.app.menu.IMenuStrategy;
import com.hll_sc_app.app.setting.group.GroupSettingActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.menu.MenuBean;

import java.util.ArrayList;
import java.util.List;

import static com.hll_sc_app.app.setting.priceratio.PriceRatioTemplateListActivity.TYPE_AGREEMENT_PRICE;
import static com.hll_sc_app.app.setting.priceratio.PriceRatioTemplateListActivity.TYPE_PRICE_MANAGE;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/12
 */

public class PriceMenu implements IMenuStrategy {
    @Override
    public String getTitle() {
        return "价格相关设置";
    }

    @Override
    public List<MenuBean> getList() {
        List<MenuBean> list = new ArrayList<>();
        list.add(new MenuBean("协议价比例模版设置") {
            @Override
            public boolean doFinally() {
                RouterUtil.goToActivity(RouterConfig.SETTING_PRICE_RATIO_LIST, TYPE_AGREEMENT_PRICE);
                return true;
            }
        });
        list.add(new MenuBean("售价比例模版设置") {
            @Override
            public boolean doFinally() {
                RouterUtil.goToActivity(RouterConfig.SETTING_PRICE_RATIO_LIST, TYPE_PRICE_MANAGE);
                return true;
            }
        });
        list.add(new MenuBean("价格根据转换率变价设置") {
            @Override
            public boolean doFinally() {
                GroupSettingActivity.start("价格根据转换率变价设置", null, 26);
                return true;
            }
        });
        list.add(new MenuBean("发货信息修改商品价格") {
            @Override
            public boolean doFinally() {
                GroupSettingActivity.start("发货信息修改商品价格", null, 10);
                return true;
            }
        });
        list.add(new MenuBean("报价单审核设置") {
            @Override
            public boolean doFinally() {
                GroupSettingActivity.start("报价单审核设置", null, 30);
                return true;
            }
        });
        return list;
    }

    @Override
    public String getViewName() {
        return null;
    }
}
