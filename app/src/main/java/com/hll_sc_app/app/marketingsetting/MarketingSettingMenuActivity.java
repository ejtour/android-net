package com.hll_sc_app.app.marketingsetting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.marketingsetting.product.ProductMarketingListActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.hll_sc_app.app.marketingsetting.product.ProductMarketingListActivity.TYPE_ORDER;
import static com.hll_sc_app.app.marketingsetting.product.ProductMarketingListActivity.TYPE_PRODUCT;

/**
 * 营销中心入口
 */
@Route(path = RouterConfig.ACTIVITY_MARKETING_SETTING_MENU)
public class MarketingSettingMenuActivity extends AppCompatActivity {
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_market_setting_menu);
        unbinder = ButterKnife.bind(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_product, R.id.ll_order, R.id.ll_coupon})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_product:
                ProductMarketingListActivity.start(TYPE_PRODUCT);
                break;
            case R.id.ll_order:
                ProductMarketingListActivity.start(TYPE_ORDER);
                break;
            case R.id.ll_coupon:
                RouterUtil.goToActivity(RouterConfig.ACTIVITY_MARKETING_COUPON_LIST);
                break;
            default:
                break;
        }
    }
}