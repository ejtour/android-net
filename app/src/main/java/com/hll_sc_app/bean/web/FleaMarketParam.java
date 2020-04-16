package com.hll_sc_app.bean.web;

import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.bean.UserShop;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/3/31
 */

public class FleaMarketParam extends WebParam {
    private String token;
    private UserShop selectShop;

    public FleaMarketParam() {
        UserBean user = GreenDaoUtils.getUser();
        if (user != null) {
            token = user.getAccessToken();
            List<UserShop> shopList = GreenDaoUtils.getShopList();
            if (!CommonUtils.isEmpty(shopList)) {
                selectShop = shopList.get(0);
            }
        }
    }

    public String getToken() {
        return token;
    }

    public UserShop getSelectShop() {
        return selectShop;
    }
}
