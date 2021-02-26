package com.hll_sc_app.app.order.place.confirm.remark;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/23
 */

@Route(path = RouterConfig.ORDER_PLACE_CONFIRM_REMARK)
public class OrderConfirmRemarkActivity extends BaseActivity {
    public static final int REQ_CODE = 0x895;
    @BindView(R.id.pcr_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.pcr_edit_text)
    EditText mEditText;
    @BindView(R.id.pcr_count)
    TextView mCount;
    @Autowired(name = "object0")
    String mTitle;
    @Autowired(name = "object1")
    String mRemark;

    /**
     * @param shopName 供应商门店名称
     * @param remark   备注
     */
    public static void start(Activity activity, String shopName, String remark) {
        Object[] args = {shopName, remark};
        RouterUtil.goToActivity(RouterConfig.ORDER_PLACE_CONFIRM_REMARK, activity, REQ_CODE, args);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_place_confirm_remark);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        mTitleBar.setHeaderTitle(mTitle);
        mTitleBar.setRightBtnClick(this::save);
        if (!TextUtils.isEmpty(mRemark)) {
            mEditText.setText(mRemark);
            mEditText.setSelection(mRemark.length());
        }
    }

    @OnTextChanged(R.id.pcr_edit_text)
    public void onTextChanged(CharSequence s) {
        mCount.setText(String.format("剩余%s字", 100 - s.length()));
    }

    private void save(View v) {
        Intent intent = new Intent();
        intent.putExtra("remark", mEditText.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
