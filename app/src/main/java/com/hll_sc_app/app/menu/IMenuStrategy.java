package com.hll_sc_app.app.menu;

import com.hll_sc_app.bean.menu.MenuBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/12
 */

public interface IMenuStrategy {
    String getTitle();

    List<MenuBean> getList();
}
