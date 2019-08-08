package com.hll_sc_app.app.paymanage;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.style.ReplacementSpan;
import android.view.View;
import android.widget.CompoundButton;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.delivery.DeliveryBean;
import com.hll_sc_app.bean.delivery.DeliveryCompanyBean;
import com.kyleduo.switchbutton.SwitchButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 支付管理
 *
 * @author zhuyingsong
 * @date 2019/8/8
 */
@Route(path = RouterConfig.PAY_MANAGE, extras = Constant.LOGIN_EXTRA)
public class PayManageActivity extends BaseLoadActivity implements PayManageContract.IDeliveryTypeSetView,
    CompoundButton.OnCheckedChangeListener {
    @BindView(R.id.switch_1)
    SwitchButton mSwitch1;
    @BindView(R.id.switch_2)
    SwitchButton mSwitch2;
    @BindView(R.id.switch_3)
    SwitchButton mSwitch3;
    private DeliveryBean mBean;
    private PayManagePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_manage);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = PayManagePresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mSwitch1.setOnCheckedChangeListener(this);
        mSwitch2.setOnCheckedChangeListener(this);
        mSwitch3.setOnCheckedChangeListener(this);
    }

    @Subscribe
    public void onEvent(ArrayList<DeliveryCompanyBean> list) {
        mBean.setDeliveryCompanyList(list);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mPresenter.start();
    }

    @OnClick({R.id.img_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void showDeliveryList(DeliveryBean bean) {
        this.mBean = bean;
        mSwitch1.setCheckedNoEvent(false);
        mSwitch2.setCheckedNoEvent(false);
        mSwitch3.setCheckedNoEvent(false);
        String deliveryWay = bean.getDeliveryWay();
        if (!TextUtils.isEmpty(deliveryWay)) {
            String[] strings = deliveryWay.split(",");
            for (String s : strings) {
                switch (s) {
                    case "1":
                        mSwitch1.setCheckedNoEvent(true);
                        break;
                    case "2":
                        mSwitch2.setCheckedNoEvent(true);
                        break;
                    case "3":
                        mSwitch3.setCheckedNoEvent(true);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void editSuccess() {
        showToast("配送方式修改成功");
        mPresenter.start();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!mSwitch1.isChecked() && !mSwitch2.isChecked() && !mSwitch3.isChecked()) {
            ((SwitchButton) buttonView).setCheckedNoEvent(!isChecked);
            showToast("至少保留一种配送方式噢");
            return;
        }
        String actionType = isChecked ? "insert" : "delete";
        String deliveryWay = null;
        if (buttonView == mSwitch1) {
            // 自有物流配送
            deliveryWay = "1";
        } else if (buttonView == mSwitch2) {
            // 上门自提
            deliveryWay = "2";
        } else if (buttonView == mSwitch3) {
            // 第三方物流公司
            deliveryWay = "3";
        }
        if (!TextUtils.isEmpty(actionType) && !TextUtils.isEmpty(deliveryWay)) {
            mPresenter.editDeliveryType(actionType, deliveryWay);
        }
    }

    private static class CustomReplacementSpan extends ReplacementSpan {
        private final int width;

        CustomReplacementSpan(int width) {
            this.width = width;
        }

        @Override
        public int getSize(@NonNull Paint paint, CharSequence text, int start, int end,
                           @Nullable Paint.FontMetricsInt fm) {
            return width;
        }

        @Override
        public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x,
                         int top, int y, int bottom, @NonNull Paint paint) {
            paint.setColor(Color.parseColor("#E8F5FE"));
            canvas.drawRect(x, top, x + width, top + width, paint);
            paint.setColor(Color.parseColor("#5695D2"));
            float textWidth = paint.measureText(String.valueOf(text.subSequence(start, end)));
            float baseLine = Math.abs(paint.ascent() + paint.descent()) / 2;
            canvas.drawText(text, start, end, (float) (x + width * 0.5 - textWidth / 2),
                (float) (((top + width) * 0.5) + baseLine), paint);
        }
    }
}
