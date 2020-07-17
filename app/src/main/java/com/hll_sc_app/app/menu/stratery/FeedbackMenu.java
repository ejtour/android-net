package com.hll_sc_app.app.menu.stratery;

import com.hll_sc_app.app.menu.IMenuStrategy;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.menu.MenuBean;

import java.util.ArrayList;
import java.util.List;

public class FeedbackMenu implements IMenuStrategy {
    @Override
    public String getTitle() {
        return "反馈投诉";
    }

    @Override
    public List<MenuBean> getList() {
        List<MenuBean> list = new ArrayList<>();
        list.add(new MenuBean("意见反馈", RouterConfig.ACTIVITY_FEED_BACK_LIST));
        list.add(new MenuBean("向平台投诉", RouterConfig.ACTIVITY_PLATFORM_COMPLAIN_LIST));
        return list;
    }
}
