package com.hll_sc_app.app.stockmanage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 库存管理首页
 */
@Route(path = RouterConfig.ACTIVITY_STOCK_MANAGE_MENU)
public class MenuListActivity extends AppCompatActivity {
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_manage_menu);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_stock_manage, R.id.ll_stock_query, R.id.ll_product_stock_check, R.id.ll_stock_log_query, R.id.ll_customer_send_manage, R.id.ll_purchaser_query})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_stock_manage:
                RouterUtil.goToActivity(RouterConfig.ACTIVITY_STORE_HOUSE_MANAGE);
                break;
            case R.id.ll_stock_query:
                RouterUtil.goToActivity(RouterConfig.ACTIVITY_STOCK_QUERY_LIST);
                break;
            case R.id.ll_product_stock_check:
                break;
            case R.id.ll_stock_log_query:
                RouterUtil.goToActivity(RouterConfig.ACTIVITY_STOCK_LOG_QUERY);
                break;
            case R.id.ll_customer_send_manage:
                RouterUtil.goToActivity(RouterConfig.ACTIVITY_STOCK_CUSTOMER_SEND);
                break;
            case R.id.ll_purchaser_query:
                break;
            default:
                break;
        }
    }

}
