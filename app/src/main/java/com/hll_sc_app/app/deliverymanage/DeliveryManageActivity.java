package com.hll_sc_app.app.deliverymanage;

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
import com.hll_sc_app.base.utils.router.RouterUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 配送管理
 *
 * @author zhuyingsong
 * @date 2019-07-27
 */
@Route(path = RouterConfig.DELIVERY_MANAGE, extras = Constant.LOGIN_EXTRA)
public class DeliveryManageActivity extends BaseLoadActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        setContentView(R.layout.activity_delivery_manage);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.img_close, R.id.txt_DeliveryType, R.id.txt_nextDayDelivery, R.id.txt_ageing, R.id.txt_minimum,
        R.id.txt_range})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
            case R.id.txt_DeliveryType:
                RouterUtil.goToActivity(RouterConfig.DELIVERY_TYPE_SET);
                break;
            default:
                break;
        }
    }
}
