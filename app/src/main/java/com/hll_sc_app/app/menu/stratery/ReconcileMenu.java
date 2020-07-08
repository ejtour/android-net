package com.hll_sc_app.app.menu.stratery;

import com.hll_sc_app.R;
import com.hll_sc_app.app.menu.IMenuStrategy;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.menu.MenuBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/12
 */

public class ReconcileMenu implements IMenuStrategy {
    @Override
    public String getTitle() {
        return "对账结算";
    }

    @Override
    public List<MenuBean> getList() {
        List<MenuBean> list = new ArrayList<>();
        list.add(new MenuBean(R.drawable.ic_report_customer_receive, "客户收货查询", RouterConfig.REPORT_CUSTOMER_RECEIVE));
        list.add(new MenuBean(R.drawable.ic_report_customer_settle, "客户结算查询", RouterConfig.REPORT_CUSTOMER_SETTLE));
        list.add(new MenuBean(R.drawable.ic_report_credit_customer, "对账单", RouterConfig.BILL_LIST));
        return list;
    }
}
