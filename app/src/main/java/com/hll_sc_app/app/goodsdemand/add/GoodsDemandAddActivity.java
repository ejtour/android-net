package com.hll_sc_app.app.goodsdemand.add;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goodsdemand.commit.GoodsDemandCommitActivity;
import com.hll_sc_app.app.goodsdemand.search.PurchaserSearchActivity;
import com.hll_sc_app.base.BaseActivity;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.window.NameValue;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/18
 */
@Route(path = RouterConfig.GOODS_DEMAND_ADD)
public class GoodsDemandAddActivity extends BaseActivity {
    @BindView(R.id.gda_supplier)
    TextView mSupplier;
    @BindView(R.id.gda_purchaser)
    TextView mPurchaser;
    @BindView(R.id.gda_name)
    EditText mName;
    @BindView(R.id.gda_desc)
    EditText mDesc;
    @BindView(R.id.gda_count)
    TextView mCount;
    @BindView(R.id.gda_contact)
    TextView mContact;
    @BindView(R.id.gda_next)
    Button mNext;
    private NameValue mPurchaserInfo;
    private int mDefaultSearchIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_goods_demand_add);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        UserBean user = GreenDaoUtils.getUser();
        mSupplier.setText(user.getGroupName());
        mContact.setText(user.getLoginPhone());
    }

    @OnClick({R.id.gda_purchaser, R.id.gda_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.gda_purchaser:
                PurchaserSearchActivity.start(this,
                        mPurchaserInfo != null ? mPurchaserInfo.getValue() : null, mDefaultSearchIndex);
                break;
            case R.id.gda_next:
                GoodsDemandCommitActivity.start(mPurchaserInfo.getValue(),
                        mPurchaserInfo.getName(),
                        mName.getText().toString().trim(),
                        mDesc.getText().toString().trim());
                break;
        }
    }

    @OnTextChanged(value = R.id.gda_desc)
    public void onTextChanged(CharSequence s) {
        mCount.setText(String.valueOf(200 - s.length()));
        afterTextChanged();
    }

    @OnTextChanged({R.id.gda_purchaser, R.id.gda_name})
    public void afterTextChanged() {
        mNext.setEnabled(!TextUtils.isEmpty(mPurchaser.getText())
                && !TextUtils.isEmpty(mName.getText().toString().trim())
                && !TextUtils.isEmpty(mDesc.getText().toString().trim()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PurchaserSearchActivity.REQ_CODE && RESULT_OK == resultCode && data != null) {
            mPurchaserInfo = data.getParcelableExtra("parcelable");
            mDefaultSearchIndex = data.getIntExtra("object", 0);
            mPurchaser.setText(mPurchaserInfo.getName());
        }
    }
}
