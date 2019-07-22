package com.hll_sc_app.app.setting.priceratio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 价格比例设置
 *
 * @author zhuyingsong
 * @date 2019-07-22
 */
@Route(path = RouterConfig.SETTING_PRICE_RATIO, extras = Constant.LOGIN_EXTRA)
public class PriceRatioActivity extends BaseLoadActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        setContentView(R.layout.activity_setting_price_ratio);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.img_close, R.id.txt_agreement_price, R.id.txt_price_manage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_agreement_price:
                // 协议价比例模版设置
                break;
            case R.id.txt_price_manage:
                // 售价比例模版设置
                break;
            default:
                break;
        }
    }
}
