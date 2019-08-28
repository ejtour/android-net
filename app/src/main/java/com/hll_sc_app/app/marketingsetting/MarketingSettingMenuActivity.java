package com.hll_sc_app.app.marketingsetting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
                RouterUtil.goToActivity(RouterConfig.ACTIVITY_MARKETING_PRODUCT_LIST);
                break;
            case R.id.ll_order:
                break;
            case R.id.ll_coupon:
                RouterUtil.goToActivity(RouterConfig.ACTIVITY_MARKETING_COUPON_LIST);
                break;
            default:
                break;
        }
    }
}