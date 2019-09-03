package com.hll_sc_app.app.report.produce.input.maneffect;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.report.produce.ProduceBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.NumberUtils;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/31
 */
@Route(path = RouterConfig.REPORT_PRODUCE_INPUT_PEOPLE)
public class PeopleEffectInputActivity extends BaseLoadActivity implements IPeopleEffectInputContract.IPeopleEffectInputView {
    public static final int REQ_CODE = 0x826;

    public static void start(Activity activity, ProduceBean bean) {
        RouterUtil.goToActivity(RouterConfig.REPORT_PRODUCE_INPUT_PEOPLE, activity, REQ_CODE, bean);
    }

    @BindView(R.id.pei_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.pei_weigh)
    EditText mWeigh;
    @BindView(R.id.pei_package)
    EditText mPackage;
    @BindView(R.id.pei_weigh_effect)
    TextView mWeighEffect;
    @BindView(R.id.pei_package_effect)
    TextView mPackageEffect;
    @BindView(R.id.pei_amount_effect)
    TextView mAmountEffect;
    @Autowired(name = "parcelable")
    ProduceBean mBean;
    int mTotalNum;
    private IPeopleEffectInputContract.IPeopleEffectInputPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_report_people_effect_input);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        mTotalNum = mBean.getStandardSortNum() + mBean.getVegetablesPackNum() + mBean.getVegetablesSortNum();
        mPresenter = PeopleEffectInputPresenter.newInstance(mBean);
        mPresenter.register(this);
    }

    private void initView() {
        mTitleBar.setRightBtnClick(this::save);
        mWeigh.setText(mBean.getOrderQtyPackageWeight() == 0 ? "" : CommonUtils.formatNumber(mBean.getOrderQtyPackageWeight()));
        mPackage.setText(mBean.getDeliveryPackageQty() == 0 ? "" : String.valueOf(mBean.getDeliveryPackageQty()));
        mAmountEffect.setText(String.format("%s 元/人", NumberUtils.isNumeric(mBean.getAmountEfficiency()) ? mBean.getAmountEfficiency() : "0.00"));
        mWeighEffect.setText(String.format("%s 斤/人", NumberUtils.isNumeric(mBean.getWeightEfficiency()) ? mBean.getWeightEfficiency() : "0.00"));
        mPackageEffect.setText(String.format("%s 个/人", NumberUtils.isNumeric(mBean.getPackageEfficiency()) ? mBean.getPackageEfficiency() : "0.00"));
    }

    private void save(View view) {
        if (TextUtils.isEmpty(mPackage.getText())) {
            ToastUtils.showShort(this, mPackage.getHint().toString());
            return;
        }
        if (TextUtils.isEmpty(mWeigh.getText())) {
            ToastUtils.showShort(this, mWeigh.getHint().toString());
            return;
        }
        mPresenter.save(mPackage.getText().toString(), mWeigh.getText().toString());
    }

    @OnTextChanged(R.id.pei_package)
    public void onTextChanged(CharSequence s) {
        mPackageEffect.setText(String.format("%s 个/人", mTotalNum == 0 ? 0 :
                CommonUtils.divDouble(TextUtils.isEmpty(s) ? 0 : Double.parseDouble(s.toString()),
                        mTotalNum, 2)));
    }

    @OnTextChanged(value = R.id.pei_weigh, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChanged(Editable s) {
        Utils.processMoney(s, false);
        mWeighEffect.setText(String.format("%s 斤/人", mTotalNum == 0 ? 0 :
                CommonUtils.divDouble(TextUtils.isEmpty(s) ? 0 : Double.parseDouble(s.toString()),
                        mTotalNum, 2)));
    }

    @Override
    public void saveSuccess() {
        setResult(RESULT_OK);
        finish();
    }
}
