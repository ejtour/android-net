package com.hll_sc_app.app.order.place.confirm.modify;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.AreaSelectWindow;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 2021/6/1
 */
@Route(path = RouterConfig.ORDER_PLACE_MODIFY)
public class PlaceOrderModifyActivity extends BaseLoadActivity {
    public static final int REQ_CODE = 0x11;
    @BindView(R.id.opm_area)
    TextView mArea;
    @BindView(R.id.opm_address)
    EditText mAddress;
    @BindView(R.id.opm_save)
    TextView mSave;
    private AreaSelectWindow mAreaWindow;

    public static void start(Activity context) {
        RouterUtil.goToActivity(RouterConfig.ORDER_PLACE_MODIFY, context, REQ_CODE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_place_modify);
        ButterKnife.bind(this);
    }

    @OnTextChanged({R.id.opm_area, R.id.opm_address})
    void onTextChanged() {
        mSave.setEnabled(!TextUtils.isEmpty(mArea.getText().toString())
                && !TextUtils.isEmpty(mAddress.getText().toString().trim()));
    }

    @OnClick(R.id.opm_area)
    void showAreaWindow() {
        if (mAreaWindow == null) {
            mAreaWindow = new AreaSelectWindow(this);
            mAreaWindow.setResultSelectListener(bean -> {
                mArea.setText(String.format("%s%s%s",
                        bean.getShopProvince(), bean.getShopCity(), bean.getShopDistrict()));
                mArea.setTag(bean);
            });
        }
        mAreaWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    @OnClick(R.id.opm_save)
    void save() {
        Intent intent = new Intent();
        intent.putExtra("address", mArea.getText().toString() + mAddress.getText());
        setResult(RESULT_OK, intent);
        onBackPressed();
    }
}
