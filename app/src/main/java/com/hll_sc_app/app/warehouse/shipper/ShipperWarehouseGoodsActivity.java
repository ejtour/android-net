package com.hll_sc_app.app.warehouse.shipper;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.StatusBarUtil;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;

/**
 * 代仓商品管理
 *
 * @author zhuyingsong
 * @date 2019-08-08
 */
@Route(path = RouterConfig.WAREHOUSE_SHIPPER_GOODS, extras = Constant.LOGIN_EXTRA)
public class ShipperWarehouseGoodsActivity extends BaseLoadActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_shipper_goods);
        getSupportFragmentManager().beginTransaction().replace(R.id.content,
                RouterUtil.getFragment(RouterConfig.ROOT_HOME_GOODS)).commitAllowingStateLoss();
    }

    @Override
    protected void initSystemBar() {
        StatusBarUtil.setTranslucent(this);
    }
}
