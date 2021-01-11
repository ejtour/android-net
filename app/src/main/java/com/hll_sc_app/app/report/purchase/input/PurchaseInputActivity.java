package com.hll_sc_app.app.report.purchase.input;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.report.purchase.PurchaseBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.TitleBar;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/28
 */

@Route(path = RouterConfig.REPORT_PURCHASE_INPUT)
public class PurchaseInputActivity extends BaseLoadActivity implements IPurchaseInputContract.IPurchaseInputView {
    public static final int REQ_CODE = 0x711;

    /**
     * @param bean 采购记录
     */
    public static void start(Activity context, PurchaseBean bean) {
        RouterUtil.goToActivity(RouterConfig.REPORT_PURCHASE_INPUT, context, REQ_CODE, bean);
    }

    @BindView(R.id.rpi_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.rpi_date)
    TextView mDate;
    @BindView(R.id.rpi_num_label)
    TextView mNumLabel;
    @BindView(R.id.rpi_num)
    EditText mNum;
    @BindView(R.id.rpi_fee_label)
    TextView mFeeLabel;
    @BindView(R.id.rpi_fee)
    EditText mFee;
    @BindView(R.id.rpi_user)
    TextView mUser;
    @Autowired(name = "parcelable", required = true)
    PurchaseBean mBean;
    private IPurchaseInputContract.IPurchaseInputPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_puchase_input);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initView() {
        mDate.setText(CalendarUtils.format(new Date(), Constants.SLASH_YYYY_MM_DD));
        mUser.setText(GreenDaoUtils.getUser().getEmployeeName());
        if (mBean.getPurchaserNum() == -1) { // 物流
            mTitleBar.setHeaderTitle("录入采购物流");
            mNumLabel.setText("采购车辆数");
            mFeeLabel.setText("采购物流费用");
            mNum.setHint("请输入采购车辆数");
            mFee.setHint("请输入采购物流费用");
            if (!TextUtils.isEmpty(mBean.getId())) { // 编辑
                mTitleBar.setHeaderTitle("修改采购物流");
                mNum.setText(String.valueOf(mBean.getCarNums()));
                mFee.setText(CommonUtils.formatNumber(mBean.getLogisticsCost()));
            }
        }
        if (mBean.getCarNums() == -1) { // 金额
            mTitleBar.setHeaderTitle("录入采购金额");
            mNumLabel.setText("采购人数");
            mFeeLabel.setText("采购金额");
            mNum.setHint("请输入采购人数");
            mFee.setHint("请输入采购金额");
            if (!TextUtils.isEmpty(mBean.getId())) { // 编辑
                mTitleBar.setHeaderTitle("修改采购金额");
                mNum.setText(String.valueOf(mBean.getPurchaserNum()));
                mFee.setText(CommonUtils.formatNumber(mBean.getPurchaseAmount()));
            }
        }
        mTitleBar.setRightBtnClick(this::save);
    }

    private void save(View view) {
        if (TextUtils.isEmpty(mNum.getText())) {
            showToast("请输入" + mNumLabel.getText());
            return;
        }
        if (TextUtils.isEmpty(mFee.getText())) {
            showToast("请输入" + mFeeLabel.getText());
            return;
        }
        mPresenter.save(mBean.getCarNums() == -1 ? 0 : 1, mNum.getText().toString(), mFee.getText().toString());
    }

    private void initData() {
        mPresenter = PurchaseInputPresenter.newInstance(mBean.getId());
        mPresenter.register(this);
    }

    @OnTextChanged(value = R.id.rpi_fee, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChanged(Editable s) {
        Utils.processMoney(s, false);
    }

    @Override
    public void saveSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    @OnClick({R.id.rpi_date, R.id.rpi_user})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rpi_date:
                showToast("录入日期不可修改");
                break;
            case R.id.rpi_user:
                showToast("录入人员不可修改");
                break;
        }
    }
}
