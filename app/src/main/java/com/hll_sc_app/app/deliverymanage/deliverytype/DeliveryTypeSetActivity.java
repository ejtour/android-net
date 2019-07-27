package com.hll_sc_app.app.deliverymanage.deliverytype;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ReplacementSpan;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.delivery.DeliveryBean;
import com.hll_sc_app.bean.delivery.DeliveryCompanyBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.kyleduo.switchbutton.SwitchButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 配送方式设置
 *
 * @author zhuyingsong
 * @date 2019/7/27
 */
@Route(path = RouterConfig.DELIVERY_TYPE_SET, extras = Constant.LOGIN_EXTRA)
public class DeliveryTypeSetActivity extends BaseLoadActivity implements DeliveryTypeSetContract.IDeliveryTypeSetView,
    CompoundButton.OnCheckedChangeListener {
    @BindView(R.id.switch_1)
    SwitchButton mSwitch1;
    @BindView(R.id.switch_2)
    SwitchButton mSwitch2;
    @BindView(R.id.switch_3)
    SwitchButton mSwitch3;
    @BindView(R.id.txt_delivery_name)
    TextView mTxtDeliveryName;
    @BindView(R.id.ll_delivery_name)
    LinearLayout mLlDeliveryName;
    private DeliveryTypeSetPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_type);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = DeliveryTypeSetPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mSwitch1.setOnCheckedChangeListener(this);
        mSwitch2.setOnCheckedChangeListener(this);
        mSwitch3.setOnCheckedChangeListener(this);
    }

    @OnClick({R.id.img_close, R.id.ll_delivery_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.ll_delivery_name:
                break;
            default:
                break;
        }
    }

    @Override
    public void showDeliveryList(DeliveryBean bean) {
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
        showDeliveryName(bean.getDeliveryCompanyList());
    }

    /**
     * 显示第三方物流配送公司名称
     *
     * @param deliveryCompanyBeans deliveryCompanyBeans
     */
    private void showDeliveryName(List<DeliveryCompanyBean> deliveryCompanyBeans) {
        if (mSwitch3.isChecked()) {
            mLlDeliveryName.setVisibility(View.VISIBLE);
            StringBuilder stringBuilder = new StringBuilder();
            if (!CommonUtils.isEmpty(deliveryCompanyBeans)) {
                int count = 0;
                for (int i = 0, all = deliveryCompanyBeans.size(); i < all; i++) {
                    DeliveryCompanyBean deliveryCompanyBean = deliveryCompanyBeans.get(i);
                    if (TextUtils.equals(deliveryCompanyBean.getStatus(), "1")) {
                        count++;
                    }
                }
                int size = count > 6 ? 6 : count;
                for (int i = 0; i < size; i++) {
                    DeliveryCompanyBean deliveryCompanyBean = deliveryCompanyBeans.get(i);
                    if (TextUtils.equals(deliveryCompanyBean.getStatus(), "1")) {
                        // 生效
                        stringBuilder.append(deliveryCompanyBean.getDeliveryCompanyName(), 0, 1).append(" ");
                    }
                }
                if (count > 6) {
                    stringBuilder.append("等").append(count).append("家");
                }
                SpannableString spannableString = new SpannableString(stringBuilder.toString());
                int width = UIUtils.dip2px(18);
                for (int i = 0; i < size; i++) {
                    spannableString.setSpan(new CustomReplacementSpan(width), 2 * i, 2 * i + 1,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                mTxtDeliveryName.setText(spannableString);
            }
        } else {
            mLlDeliveryName.setVisibility(View.GONE);
        }
    }

    @Override
    public void editSuccess() {
        showToast("配送方式修改成功");
        ARouter.getInstance().build(RouterConfig.COOPERATION_PURCHASER_DETAIL)
            .setProvider(new LoginInterceptor())
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .navigation(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == mSwitch1) {
            // 自有物流配送
        } else if (buttonView == mSwitch2) {
            // 上门自提
        } else if (buttonView == mSwitch3) {
            // 第三方物流公司
            mLlDeliveryName.setVisibility(mSwitch3.isChecked() ? View.VISIBLE : View.GONE);
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
