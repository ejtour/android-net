package com.hll_sc_app.app.marketingsetting;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.router.RouterConfig;

/**
 * 营销中心入口
 */
@Route(path = RouterConfig.ACTIVITY_MARKETING_SETTING_MENU)
public class MarketingSettingMenuActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_market_setting_menu);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_product:
                break;
            case R.id.ll_order:
                break;
            case R.id.ll_coupon:
                break;
            default:
                break;
        }
    }
}