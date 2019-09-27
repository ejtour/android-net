package com.hll_sc_app.app.search.stratery;

import com.hll_sc_app.app.search.ISearchContract;
import com.hll_sc_app.app.search.presenter.OrderSearchPresenter;
import com.hll_sc_app.bean.event.OrderEvent;
import com.hll_sc_app.bean.event.ShopSearchEvent;
import com.hll_sc_app.bean.window.NameValue;

import org.greenrobot.eventbus.EventBus;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/25
 */

public class ShopAssociationSearch implements ISearchContract.ISearchStrategy {
    @Override
    public ISearchContract.ISearchPresenter getSearchPresenter() {
        return new OrderSearchPresenter();
    }

    @Override
    public void onSearch(String searchWords) {
        ShopSearchEvent event = new ShopSearchEvent();
        event.setName(searchWords);
        EventBus.getDefault().post(event);
    }

    @Override
    public void onClick(NameValue nameValue) {
        ShopSearchEvent event = new ShopSearchEvent();
        event.setName(nameValue.getName());
        event.setShopMallId(nameValue.getValue());
        EventBus.getDefault().post(event);
    }

    @Override
    public String getEditHint() {
        return "请输入采购商公司名称";
    }

    @Override
    public String getEmptyTip() {
        return "您可以输入客户名称查找采购商门店";
    }
}
