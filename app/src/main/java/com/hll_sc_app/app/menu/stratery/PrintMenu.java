package com.hll_sc_app.app.menu.stratery;

import com.hll_sc_app.app.menu.IMenuStrategy;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.menu.MenuBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 2021/4/22
 */
public class PrintMenu implements IMenuStrategy {
    @Override
    public String getTitle() {
        return "打印设置";
    }

    @Override
    public List<MenuBean> getList() {
        List<MenuBean> list = new ArrayList<>();
        list.add(new MenuBean("打印机设置", RouterConfig.PRINTER_LIST));
        list.add(new MenuBean("打印模板设置", RouterConfig.PRINT_TEMPLATE));
        return list;
    }
}
