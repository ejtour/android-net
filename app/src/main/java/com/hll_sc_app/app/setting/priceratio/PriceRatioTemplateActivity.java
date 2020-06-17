package com.hll_sc_app.app.setting.priceratio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.setting.group.GroupSettingActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 价格比例设置
 *
 * @author zhuyingsong
 * @date 2019-07-22
 */
@Route(path = RouterConfig.SETTING_PRICE_RATIO, extras = Constant.LOGIN_EXTRA)
public class PriceRatioTemplateActivity extends BaseLoadActivity {

    public static final String TYPE_AGREEMENT_PRICE = "1";
    public static final String TYPE_PRICE_MANAGE = "2";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        setContentView(R.layout.activity_setting_price_ratio);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.img_close, R.id.txt_agreement_price, R.id.txt_price_manage,R.id.txt_price_conversion_rate,R.id.txt_send_info_change_price,R.id.txt_quotation_review})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_agreement_price:
                // 协议价比例模版设置
                RouterUtil.goToActivity(RouterConfig.SETTING_PRICE_RATIO_LIST, TYPE_AGREEMENT_PRICE);
                break;
            case R.id.txt_price_manage:
                // 售价比例模版设置
                RouterUtil.goToActivity(RouterConfig.SETTING_PRICE_RATIO_LIST, TYPE_PRICE_MANAGE);
                break;
            case R.id.txt_price_conversion_rate:
                GroupSettingActivity.start("价格根据转换率变价设置", null, 26);
                break;
            case R.id.txt_send_info_change_price:
                GroupSettingActivity.start("发货信息修改商品价格", null, 10);
                break;
            case R.id.txt_quotation_review:
                GroupSettingActivity.start("报价单审核设置", null, 30);
                break;
            default:
                break;
        }
    }
}
