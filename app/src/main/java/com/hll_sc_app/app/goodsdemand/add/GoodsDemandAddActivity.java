package com.hll_sc_app.app.goodsdemand.add;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goodsdemand.commit.GoodsDemandCommitActivity;
import com.hll_sc_app.app.goodsdemand.search.PurchaserSearchActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.goodsdemand.GoodsDemandBean;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/18
 */
@Route(path = RouterConfig.GOODS_DEMAND_ADD)
public class GoodsDemandAddActivity extends BaseLoadActivity implements IGoodsDemandAddContract.IGoodsDemandAddView {
    @BindView(R.id.gda_title_bar)
    TitleBar mTitleBar;
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
    @Autowired(name = "parcelable")
    NameValue mPurchaserInfo;
    @Autowired(name = "demand")
    GoodsDemandBean mBean;
    private int mDefaultSearchIndex;

    /**
     * @param name 第一条数据的采购商名称
     * @param id   第一条数据的采购商id
     */
    public static void start(String name, String id) {
        RouterUtil.goToActivity(RouterConfig.GOODS_DEMAND_ADD, new NameValue(name, id));
    }

    public static void start(GoodsDemandBean bean) {
        ARouter.getInstance().build(RouterConfig.GOODS_DEMAND_ADD)
                .withParcelable("demand", bean)
                .setProvider(new LoginInterceptor())
                .navigation();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_demand_add);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        if (mBean != null) {
            mPurchaser.setClickable(false);
            mPurchaser.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            mPurchaser.setText(mBean.getPurchaserName());
            mName.setText(mBean.getProductName());
            mDesc.setText(mBean.getProductBrief());
            return;
        }
        IGoodsDemandAddContract.IGoodsDemandAddPresenter presenter = GoodsDemandAddPresenter.newInstance();
        presenter.register(this);
        if (mPurchaserInfo == null) {
            presenter.start();
        } else {
            mPurchaser.setText(mPurchaserInfo.getName());
        }
    }

    private void initView() {
        if (mBean != null){
            mTitleBar.setHeaderTitle("编辑商品需求");
        }
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
                if (mBean != null) {
                    mBean.setProductName(mName.getText().toString().trim());
                    mBean.setProductBrief(mDesc.getText().toString().trim());
                    GoodsDemandCommitActivity.start(mBean);
                } else {
                    GoodsDemandCommitActivity.start(mPurchaserInfo.getValue(),
                            mPurchaserInfo.getName(),
                            mName.getText().toString().trim(),
                            mDesc.getText().toString().trim());
                }
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

    @Override
    public void setData(NameValue value) {
        mPurchaserInfo = value;
        mPurchaser.setText(mPurchaserInfo.getName());
    }
}
